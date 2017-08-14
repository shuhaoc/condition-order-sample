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
import me.caosh.condition.domain.model.order.ConditionOrder;
import me.caosh.condition.domain.model.order.MonitorContext;
import me.caosh.condition.domain.model.order.RealTimeMarketDriven;
import me.caosh.condition.domain.model.order.event.ConditionOrderCreateCommandEvent;
import me.caosh.condition.domain.model.order.event.ConditionOrderDeleteCommandEvent;
import me.caosh.condition.domain.model.order.event.ConditionOrderUpdateCommandEvent;
import me.caosh.condition.domain.model.signal.SignalFactory;
import me.caosh.condition.domain.model.signal.TradeSignal;
import me.caosh.condition.domain.util.EventBuses;
import me.caosh.condition.infrastructure.rabbitmq.TriggerMessageTriggerProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * Created by caosh on 2017/8/9.
 *
 * @implNote 由于是demo，此模块没有解决并发问题，生产环境中请勿使用
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
        EventBuses.DEFAULT.register(this);
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
                    checkWithRealTimeMarket(conditionOrder, realTimeMarket);
                }
            }
        });

        logger.info("---------------- Finish checking, count={} ----------------", Iterables.size(monitorContexts));
    }

    private void checkWithRealTimeMarket(ConditionOrder conditionOrder, RealTimeMarket realTimeMarket) {
        TradeSignal tradeSignal = ((RealTimeMarketDriven) conditionOrder).onRealTimeMarketUpdate(realTimeMarket);
        if (tradeSignal != SignalFactory.getInstance().none()) {
            triggerSignal(tradeSignal, conditionOrder, realTimeMarket);
        }
    }

    private void triggerSignal(TradeSignal tradeSignal, ConditionOrder conditionOrder, RealTimeMarket realTimeMarket) {
        TradeSignalDTO tradeSignalDTO = new TradeSignalDTOBuilder(tradeSignal).build();
        ConditionOrderDTO conditionOrderDTO = ConditionOrderDTOAssembler.toDTO(conditionOrder);
        RealTimeMarketDTO realTimeMarketDTO = RealTimeMarketDTOAssembler.toDTO(realTimeMarket);
        TriggerMessageDTO triggerMessageDTO = new TriggerMessageDTO(tradeSignalDTO, conditionOrderDTO, realTimeMarketDTO);
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
