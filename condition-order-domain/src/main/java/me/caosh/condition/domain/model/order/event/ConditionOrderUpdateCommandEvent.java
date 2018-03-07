package me.caosh.condition.domain.model.order.event;

import hbec.intellitrade.condorder.domain.ConditionOrder;

/**
 * Created by caosh on 2017/8/11.
 *
 * @author caoshuhao@touker.com
 */
public class ConditionOrderUpdateCommandEvent implements ConditionOrderCommandEvent {
    private final ConditionOrder conditionOrder;

    public ConditionOrderUpdateCommandEvent(ConditionOrder conditionOrder) {
        this.conditionOrder = conditionOrder;
    }

    public ConditionOrder getConditionOrder() {
        return conditionOrder;
    }
}
