package me.caosh.condition.application.monitor;

import com.google.common.collect.Iterables;
import com.google.common.eventbus.Subscribe;
import me.caosh.condition.domain.dto.market.RealTimeMarketDTO;
import me.caosh.condition.domain.dto.market.assembler.RealTimeMarketDTOAssembler;
import me.caosh.condition.domain.dto.order.ConditionOrderDTO;
import me.caosh.condition.domain.dto.order.TradeSignalDTO;
import me.caosh.condition.domain.dto.order.TriggerMessageDTO;
import me.caosh.condition.domain.dto.order.assembler.ConditionOrderDTOAssembler;
import me.caosh.condition.domain.dto.order.assembler.TradeSignalDTOBuilder;
import me.caosh.condition.domain.model.market.RealTimeMarket;
import me.caosh.condition.domain.model.market.event.RealTimeMarketPushEvent;
import me.caosh.condition.domain.model.order.Condition;
import me.caosh.condition.domain.model.order.ConditionOrder;
import me.caosh.condition.domain.model.order.DynamicCondition;
import me.caosh.condition.domain.model.order.RealTimeMarketDriven;
import me.caosh.condition.domain.model.order.event.ConditionOrderCreateCommandEvent;
import me.caosh.condition.domain.model.order.event.ConditionOrderDeleteCommandEvent;
import me.caosh.condition.domain.model.order.event.ConditionOrderUpdateCommandEvent;
import me.caosh.condition.domain.model.signal.SignalFactory;
import me.caosh.condition.domain.model.signal.TradeSignal;
import me.caosh.condition.infrastructure.cache.MonitorContextManage;
import me.caosh.condition.infrastructure.eventbus.MonitorEntrustBus;
import me.caosh.condition.infrastructure.rabbitmq.TriggerMessageTriggerProducer;
import me.caosh.condition.infrastructure.timer.event.TimerEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * Created by caosh on 2017/8/9.
 */
@Component
public class ConditionOrderMonitorCenter {
    private static final Logger logger = LoggerFactory.getLogger(ConditionOrderMonitorCenter.class);

    private final MonitorContextManage monitorContextManage;
    private final TriggerMessageTriggerProducer triggerMessageTriggerProducer;

    public ConditionOrderMonitorCenter(MonitorContextManage monitorContextManage,
                                       TriggerMessageTriggerProducer triggerMessageTriggerProducer) {
        this.monitorContextManage = monitorContextManage;
        this.triggerMessageTriggerProducer = triggerMessageTriggerProducer;
    }

    @PostConstruct
    public void init() {
        MonitorEntrustBus.EVENT_SERIALIZED.register(this);
    }

    @Subscribe
    public void onRealTimeMarketPushEvent(RealTimeMarketPushEvent e) {
        Map<String, RealTimeMarket> realTimeMarketMap = e.getMarketMap();
        logger.info("---------------- Start checking ----------------");

        Iterable<MonitorContext> monitorContexts = monitorContextManage.getAll();
        monitorContexts.forEach(monitorContext -> {
            ConditionOrder conditionOrder = monitorContext.getConditionOrder();
            if (conditionOrder instanceof RealTimeMarketDriven) {
                RealTimeMarket realTimeMarket = realTimeMarketMap.get(conditionOrder.getSecurityInfo().getCode());
                if (realTimeMarket != null) {
                    checkWithRealTimeMarket(monitorContext, realTimeMarket);
                }
            }
        });
        logger.info("---------------- Finish checking, count={} ----------------", Iterables.size(monitorContexts));
    }

    private void checkWithRealTimeMarket(MonitorContext monitorContext, RealTimeMarket realTimeMarket) {
        if (monitorContext.getTriggerLock().isPresent() && monitorContext.getTriggerLock().get().isLocked()) {
            logger.warn("Trigger locked, orderId={}, lockedDuration={}", monitorContext.getOrderId(),
                    monitorContext.getTriggerLock().get().getLockedDuration());
            return;
        }
        ConditionOrder conditionOrder = monitorContext.getConditionOrder();
        TradeSignal tradeSignal = ((RealTimeMarketDriven) conditionOrder).onRealTimeMarketUpdate(realTimeMarket);
        if (tradeSignal != SignalFactory.getInstance().none()) {
            triggerSignal(tradeSignal, conditionOrder, realTimeMarket);
            monitorContext.lockTriggering();
        } else {
            Condition condition = conditionOrder.getCondition();
            if (condition instanceof DynamicCondition) {
                DynamicCondition dynamicCondition = (DynamicCondition) condition;
                if (dynamicCondition.isDirty()) {
                    monitorContext.markDelaySync();
                    dynamicCondition.clearDirty();
                    logger.info("Mark delay sync, orderId={}, condition={}", conditionOrder.getOrderId(), condition);
                }
            }
        }
    }

    @Subscribe
    public void onTimer(TimerEvent e) {
        logger.debug("tick...");
        Iterable<MonitorContext> monitorContexts = monitorContextManage.getAll();
        monitorContexts.forEach(monitorContext -> {
            if (monitorContext.getDelaySyncMarker().isPresent() && monitorContext.getDelaySyncMarker().get().isTimesUp()) {
                ConditionOrder conditionOrder = monitorContext.getConditionOrder();
                triggerSignal(SignalFactory.getInstance().cacheSync(), conditionOrder);
                monitorContext.clearDelaySyncMarker();
            }
        });
    }

    private void triggerSignal(TradeSignal tradeSignal, ConditionOrder conditionOrder, RealTimeMarket realTimeMarket) {
        TradeSignalDTO tradeSignalDTO = new TradeSignalDTOBuilder(tradeSignal).build();
        ConditionOrderDTO conditionOrderDTO = ConditionOrderDTOAssembler.toDTO(conditionOrder);
        RealTimeMarketDTO realTimeMarketDTO = RealTimeMarketDTOAssembler.toDTO(realTimeMarket);
        TriggerMessageDTO triggerMessageDTO = new TriggerMessageDTO(tradeSignalDTO, conditionOrderDTO, realTimeMarketDTO);
        triggerMessageTriggerProducer.send(triggerMessageDTO);
    }

    private void triggerSignal(TradeSignal tradeSignal, ConditionOrder conditionOrder) {
        TradeSignalDTO tradeSignalDTO = new TradeSignalDTOBuilder(tradeSignal).build();
        ConditionOrderDTO conditionOrderDTO = ConditionOrderDTOAssembler.toDTO(conditionOrder);
        TriggerMessageDTO triggerMessageDTO = new TriggerMessageDTO(tradeSignalDTO, conditionOrderDTO, null);
        triggerMessageTriggerProducer.send(triggerMessageDTO);
    }

    @Subscribe
    public void onConditionOrderCreateCommand(ConditionOrderCreateCommandEvent e) {
        ConditionOrder conditionOrder = e.getConditionOrder();
        monitorContextManage.save(new MonitorContext(conditionOrder));
        logger.info("Create condition order ==> {}", conditionOrder);
    }

    @Subscribe
    public void onConditionOrderUpdateCommand(ConditionOrderUpdateCommandEvent e) {
        ConditionOrder conditionOrder = e.getConditionOrder();
        monitorContextManage.update(new MonitorContext(conditionOrder));
        logger.info("Update condition order ==> {}", conditionOrder);
    }

    @Subscribe
    public void onConditionOrderDeleteCommand(ConditionOrderDeleteCommandEvent e) {
        Long orderId = e.getOrderId();
        monitorContextManage.remove(orderId);
        logger.info("Remove condition order ==> {}", orderId);
    }
}
