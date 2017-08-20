package me.caosh.condition.domain.model.order;

import me.caosh.condition.domain.model.order.shared.DelayTimer;

import java.util.Optional;

/**
 * Created by caosh on 2017/8/14.
 *
 * @author caoshuhao@touker.com
 */
public class MonitorContext {
    private final ConditionOrder conditionOrder;
    private DelayTimer triggerLock;
    private DelayTimer delaySyncMarker;

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
        triggerLock = new DelayTimer(10);
    }

    public Optional<DelayTimer> getTriggerLock() {
        return Optional.ofNullable(triggerLock);
    }

    public boolean isTriggerLocked() {
        return triggerLock != null && !triggerLock.isTimesUp();
    }

    public void markDelaySync() {
        delaySyncMarker = new DelayTimer(4);
    }

    public void clearDelaySyncMarker() {
        delaySyncMarker = null;
    }

    public boolean isDelaySyncTimesUp() {
        return delaySyncMarker != null && delaySyncMarker.isTimesUp();
    }
}
