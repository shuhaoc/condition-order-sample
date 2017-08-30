package me.caosh.condition.application.monitor;

import com.google.common.collect.Iterables;
import com.google.common.eventbus.Subscribe;
import me.caosh.condition.domain.model.market.RealTimeMarket;
import me.caosh.condition.domain.model.market.event.RealTimeMarketPushEvent;
import me.caosh.condition.domain.model.monitor.MonitorContext;
import me.caosh.condition.domain.model.order.ConditionOrder;
import me.caosh.condition.domain.model.order.event.ConditionOrderCreateCommandEvent;
import me.caosh.condition.domain.model.order.event.ConditionOrderDeleteCommandEvent;
import me.caosh.condition.domain.model.order.event.ConditionOrderUpdateCommandEvent;
import me.caosh.condition.domain.service.monitor.ConditionMonitorService;
import me.caosh.condition.domain.service.monitor.impl.ConditionMonitorServiceImpl;
import me.caosh.condition.infrastructure.cache.MonitorContextManage;
import me.caosh.condition.infrastructure.eventbus.MonitorEventBus;
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
public class ConditionOrderMonitor {
    private static final Logger logger = LoggerFactory.getLogger(ConditionOrderMonitor.class);

    private final MonitorContextManage monitorContextManage;
    private final ConditionMonitorService conditionMonitorService;

    public ConditionOrderMonitor(MonitorContextManage monitorContextManage,
                                 TriggerMessageTriggerProducer triggerMessageTriggerProducer) {
        this.monitorContextManage = monitorContextManage;
        conditionMonitorService = new ConditionMonitorServiceImpl(triggerMessageTriggerProducer);
    }

    @PostConstruct
    public void init() {
        MonitorEventBus.EVENT_SERIALIZED.register(this);
    }

    @Subscribe
    public void onRealTimeMarketPushEvent(RealTimeMarketPushEvent e) {
        Map<String, RealTimeMarket> realTimeMarketMap = e.getMarketMap();
        logger.info("---------------- Start checking ----------------");

        Iterable<MonitorContext> monitorContexts = monitorContextManage.getAll();
        Iterables.filter(monitorContexts, MonitorContext::isRealTimeMarketDriven)
                .forEach(monitorContext -> {
                    ConditionOrder conditionOrder = monitorContext.getConditionOrder();
                    RealTimeMarket realTimeMarket = realTimeMarketMap.get(conditionOrder.getSecurityInfo().getCode());
                    if (realTimeMarket != null) {
                        conditionMonitorService.checkWithRealTimeMarket(monitorContext, realTimeMarket);
                    }
                });
        logger.info("---------------- Finish checking, count={} ----------------", Iterables.size(monitorContexts));
    }

    @Subscribe
    public void onTimer(TimerEvent e) {
        logger.debug("tick...");
        Iterable<MonitorContext> monitorContexts = monitorContextManage.getAll();
        monitorContexts.forEach(conditionMonitorService::checkWithTime);
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
