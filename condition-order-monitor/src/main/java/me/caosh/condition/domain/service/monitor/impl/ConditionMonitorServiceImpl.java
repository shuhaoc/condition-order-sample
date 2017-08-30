package me.caosh.condition.domain.service.monitor.impl;

import com.google.common.base.Preconditions;
import me.caosh.condition.domain.model.market.RealTimeMarket;
import me.caosh.condition.domain.model.monitor.MonitorContext;
import me.caosh.condition.domain.model.order.*;
import me.caosh.condition.domain.model.signal.SignalFactory;
import me.caosh.condition.domain.model.signal.TradeSignal;
import me.caosh.condition.domain.service.monitor.ConditionMonitorService;
import me.caosh.condition.infrastructure.rabbitmq.TriggerMessageTriggerProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ConditionMonitorServiceImpl implements ConditionMonitorService {
    private static final Logger logger = LoggerFactory.getLogger(ConditionMonitorServiceImpl.class);

    private final TriggerMessageTriggerProducer triggerMessageTriggerProducer;

    public ConditionMonitorServiceImpl(TriggerMessageTriggerProducer triggerMessageTriggerProducer) {
        this.triggerMessageTriggerProducer = triggerMessageTriggerProducer;
    }

    @Override
    public void checkWithRealTimeMarket(MonitorContext monitorContext, RealTimeMarket realTimeMarket) {
        Preconditions.checkArgument(monitorContext.isRealTimeMarketDriven());

        if (monitorContext.isTriggerLocked()) {
            logger.warn("Trigger locked, orderId={}, lockedDuration={}", monitorContext.getOrderId(),
                    monitorContext.getTriggerLock().orElse(null).getCurrentDuration());
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
                checkDynamicProperties(monitorContext, condition);
            }
        }
    }

    private void checkDynamicProperties(MonitorContext monitorContext, Condition condition) {
        DynamicCondition dynamicCondition = (DynamicCondition) condition;
        if (dynamicCondition.isDirty()) {
            monitorContext.markDelaySync();
            dynamicCondition.clearDirty();
            logger.info("Mark delay sync, orderId={}, condition={}", monitorContext.getOrderId(), condition);
        }
    }

    @Override
    public void checkWithTime(MonitorContext monitorContext) {
        if (monitorContext.isTriggerLocked()) {
            logger.warn("Trigger locked, orderId={}, lockedDuration={}", monitorContext.getOrderId(),
                    monitorContext.getTriggerLock().orElse(null).getCurrentDuration());
            return;
        }

        ConditionOrder conditionOrder = monitorContext.getConditionOrder();
        if (monitorContext.isDelaySyncTimesUp()) {
            triggerCacheSync(monitorContext);
        }

        if (monitorContext.isTimeDriven()) {
            TradeSignal tradeSignal = ((TimeDriven) conditionOrder).onSecondTick();
            if (tradeSignal != SignalFactory.getInstance().none()) {
                triggerSignal(tradeSignal, conditionOrder);
                monitorContext.lockTriggering();
            }
        }
    }

    private void triggerCacheSync(MonitorContext monitorContext) {
        ConditionOrder conditionOrder = monitorContext.getConditionOrder();
        triggerSignal(SignalFactory.getInstance().cacheSync(), conditionOrder);
        monitorContext.clearDelaySyncMarker();
    }

    private void triggerSignal(TradeSignal tradeSignal, ConditionOrder conditionOrder, RealTimeMarket realTimeMarket) {
        TriggerMessage triggerMessage = new TriggerMessage(tradeSignal, conditionOrder, realTimeMarket);
        triggerMessageTriggerProducer.send(triggerMessage);
    }

    private void triggerSignal(TradeSignal tradeSignal, ConditionOrder conditionOrder) {
        TriggerMessage triggerMessage = new TriggerMessage(tradeSignal, conditionOrder);
        triggerMessageTriggerProducer.send(triggerMessage);
    }
}