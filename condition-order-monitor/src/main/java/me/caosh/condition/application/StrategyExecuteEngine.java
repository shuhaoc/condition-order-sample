package me.caosh.condition.application;

import com.google.common.eventbus.Subscribe;
import hbec.intellitrade.condorder.domain.ConditionOrder;
import hbec.intellitrade.strategy.domain.signalpayload.SignalPayload;
import me.caosh.condition.domain.event.OrderDeleteCommandEvent;
import me.caosh.condition.domain.event.OrderUpdateCommandEvent;
import me.caosh.condition.domain.event.TimerEvent;
import me.caosh.condition.domain.model.market.event.RealTimeMarketPushEvent;
import me.caosh.condition.domain.container.StrategyContainer;
import me.caosh.condition.infrastructure.eventbus.MonitorEventBus;
import me.caosh.condition.infrastructure.rabbitmq.SignalPayloadProducer;
import me.caosh.condition.infrastructure.repository.MonitorRepository;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collection;

/**
 * Created by caosh on 2017/8/9.
 */
@Component
public class StrategyExecuteEngine {
    private static final Logger logger = LoggerFactory.getLogger(StrategyExecuteEngine.class);

    private final StrategyContainer strategyContainer = new StrategyContainer();
    private final MonitorRepository monitorRepository;
    private final SignalPayloadProducer signalPayloadProducer;

    public StrategyExecuteEngine(MonitorRepository monitorRepository, SignalPayloadProducer signalPayloadProducer) {
        this.monitorRepository = monitorRepository;
        this.signalPayloadProducer = signalPayloadProducer;
    }

    @PostConstruct
    public void init() {
        MonitorEventBus.EVENT_SERIALIZED.register(this);

        for (ConditionOrder conditionOrder : monitorRepository.getAllOrders()) {
            strategyContainer.add(conditionOrder);
        }
    }

    @Subscribe
    public void onRealTimeMarketPushEvent(RealTimeMarketPushEvent e) {
        logger.info("---------------- Start checking ----------------");

        Collection<SignalPayload> signalPayloads = strategyContainer.onMarketTicks(e.getMarketMap().values());
        for (SignalPayload signalPayload : signalPayloads) {
            signalPayloadProducer.send(signalPayload);
        }

        logger.info("---------------- Finish checking, count={} ----------------", strategyContainer.size());
    }

    @Subscribe
    public void onTimer(TimerEvent e) {
        Collection<SignalPayload> signalPayloads = strategyContainer.onTimeTick(LocalDateTime.now());
        for (SignalPayload signalPayload : signalPayloads) {
            signalPayloadProducer.send(signalPayload);
        }
    }

    @Subscribe
    public void onOrderUpdateCommandEvent(OrderUpdateCommandEvent e) {
        ConditionOrder conditionOrder = e.getConditionOrder();
        monitorRepository.update(conditionOrder);
        strategyContainer.add(conditionOrder);
        logger.info("Update condition order ==> {}", conditionOrder);
    }

    @Subscribe
    public void onOrderDeleteCommandEvent(OrderDeleteCommandEvent e) {
        long orderId = e.getOrderId();
        monitorRepository.remove(orderId);
        strategyContainer.remove(orderId);
        logger.info("Remove condition order ==> {}", orderId);
    }
}
