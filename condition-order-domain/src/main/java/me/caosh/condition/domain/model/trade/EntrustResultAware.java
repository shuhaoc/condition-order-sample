package me.caosh.condition.domain.model.trade;

import me.caosh.condition.domain.model.order.TriggerContext;

/**
 * Created by caosh on 2017/8/26.
 */
public interface EntrustResultAware {
    void afterEntrustReturned(TriggerContext triggerContext, EntrustResult entrustResult);
}
