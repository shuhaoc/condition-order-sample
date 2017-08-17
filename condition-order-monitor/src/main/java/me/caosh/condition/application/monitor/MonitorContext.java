package me.caosh.condition.application.monitor;

import me.caosh.condition.domain.model.order.ConditionOrder;

import java.util.Optional;

/**
 * Created by caosh on 2017/8/14.
 *
 * @author caoshuhao@touker.com
 */
public class MonitorContext {
    private final ConditionOrder conditionOrder;
    private TriggerLock triggerLock;

    public MonitorContext(ConditionOrder conditionOrder) {
        this.conditionOrder = conditionOrder;
    }

    public Long getOrderId() {
        return conditionOrder.getOrderId();
    }

    public ConditionOrder getConditionOrder() {
        return conditionOrder;
    }

    public void lockTriggering() {
        triggerLock = new TriggerLock(10);
    }

    public Optional<TriggerLock> getTriggerLock() {
        return Optional.ofNullable(triggerLock);
    }
}
