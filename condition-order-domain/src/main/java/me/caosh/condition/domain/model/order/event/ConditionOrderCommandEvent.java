package me.caosh.condition.domain.model.order.event;

import me.caosh.condition.domain.model.order.ConditionOrder;

/**
 * Created by caosh on 2017/8/11.
 *
 * @author caoshuhao@touker.com
 */
public class ConditionOrderCommandEvent {
    private final ConditionOrder conditionOrder;

    public ConditionOrderCommandEvent(ConditionOrder conditionOrder) {
        this.conditionOrder = conditionOrder;
    }

    public ConditionOrder getConditionOrder() {
        return conditionOrder;
    }
}
