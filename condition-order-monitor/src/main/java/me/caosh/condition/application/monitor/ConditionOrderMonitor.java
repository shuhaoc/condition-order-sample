package me.caosh.condition.application.monitor;

import com.google.common.collect.Iterables;
import com.google.common.eventbus.Subscribe;
import me.caosh.condition.domain.dto.order.ConditionOrderDTO;
import me.caosh.condition.domain.dto.order.TriggerMessageDTO;
import me.caosh.condition.domain.dto.order.assembler.ConditionOrderDTOAssembler;
import me.caosh.condition.domain.model.market.RealTimeMarket;
import me.caosh.condition.domain.model.market.event.RealTimeMarketPushEvent;
import me.caosh.condition.domain.model.order.ConditionOrder;
import me.caosh.condition.domain.model.order.RealTimeMarketDriven;
import me.caosh.condition.domain.model.order.event.ConditionOrderCommandEvent;
import me.caosh.condition.domain.model.signal.SignalFactory;
import me.caosh.condition.domain.model.signal.TradeSignal;
import me.caosh.condition.domain.util.EventBuses;
import me.caosh.condition.infrastructure.rabbitmq.TriggerMessageTriggerProducer;
import me.caosh.condition.infrastructure.repository.MonitorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * Created by caosh on 2017/8/9.
 */
@Component
public class ConditionOrderMonitor {
    private static final Logger logger = LoggerFactory.getLogger(ConditionOrderMonitor.class);

    private final MonitorRepository monitorRepository;
    private final TriggerMessageTriggerProducer triggerMessageTriggerProducer;

    public ConditionOrderMonitor(@Qualifier("memory") MonitorRepository monitorRepository,
                                 TriggerMessageTriggerProducer triggerMessageTriggerProducer) {
        this.monitorRepository = monitorRepository;
        this.triggerMessageTriggerProducer = triggerMessageTriggerProducer;
    }

    @PostConstruct
    public void init() {
        EventBuses.DEFAULT.register(this);
    }

    @Subscribe
    public void onRealTimeMarketPushEvent(RealTimeMarketPushEvent e) {
        Map<String, RealTimeMarket> realTimeMarketMap = e.getMarketMap();
        logger.info("Start checking =============>>");

        Iterable<ConditionOrder> conditionOrders = monitorRepository.getAllOrders();
        conditionOrders.forEach(conditionOrder -> {
            if (conditionOrder instanceof RealTimeMarketDriven) {
                RealTimeMarket realTimeMarket = realTimeMarketMap.get(conditionOrder.getSecurityInfo().getCode());
                if (realTimeMarket != null) {
                    checkWithRealTimeMarket(conditionOrder, realTimeMarket);
                }
            }
        });

        logger.info("Finish checking <<============= count={}", Iterables.size(conditionOrders));
    }

    private void checkWithRealTimeMarket(ConditionOrder conditionOrder, RealTimeMarket realTimeMarket) {
        TradeSignal tradeSignal = ((RealTimeMarketDriven) conditionOrder).onRealTimeMarketUpdate(realTimeMarket);
        if (tradeSignal == SignalFactory.getInstance().general()) {
            ConditionOrderDTO conditionOrderDTO = ConditionOrderDTOAssembler.toDTO(conditionOrder);
            TriggerMessageDTO triggerMessageDTO = new TriggerMessageDTO(tradeSignal, conditionOrderDTO, realTimeMarket);
            triggerMessageTriggerProducer.send(triggerMessageDTO);
        }
    }

    @Subscribe
    public void onConditionOrderCommand(ConditionOrderCommandEvent e) {
        ConditionOrder conditionOrder = e.getConditionOrder();
        monitorRepository.update(conditionOrder);
        logger.info("Update condition order ==> {}", conditionOrder);
    }
}
