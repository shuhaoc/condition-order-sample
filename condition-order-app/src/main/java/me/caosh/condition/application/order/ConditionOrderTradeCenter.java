package me.caosh.condition.application.order;

import me.caosh.condition.domain.model.order.TriggerContext;

/**
 * Created by caosh on 2017/8/13.
 */
public interface ConditionOrderTradeCenter {
    void handleTriggerContext(TriggerContext triggerContext);
}
