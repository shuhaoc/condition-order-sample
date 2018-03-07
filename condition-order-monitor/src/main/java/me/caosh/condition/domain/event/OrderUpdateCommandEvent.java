package me.caosh.condition.domain.event;

import hbec.intellitrade.condorder.domain.ConditionOrder;

/**
 * Created by caosh on 2017/8/11.
 *
 * @author caoshuhao@touker.com
 */
public class OrderUpdateCommandEvent implements OrderCommandEvent {
    private final ConditionOrder conditionOrder;

    public OrderUpdateCommandEvent(ConditionOrder conditionOrder) {
        this.conditionOrder = conditionOrder;
    }

    public ConditionOrder getConditionOrder() {
        return conditionOrder;
    }
}
