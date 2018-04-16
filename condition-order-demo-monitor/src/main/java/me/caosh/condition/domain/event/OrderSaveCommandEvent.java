package me.caosh.condition.domain.event;

import hbec.intellitrade.conditionorder.domain.ConditionOrder;

/**
 * Created by caosh on 2017/8/11.
 *
 * @author caoshuhao@touker.com
 */
public class OrderSaveCommandEvent implements OrderCommandEvent {
    private final ConditionOrder conditionOrder;

    public OrderSaveCommandEvent(ConditionOrder conditionOrder) {
        this.conditionOrder = conditionOrder;
    }

    public ConditionOrder getConditionOrder() {
        return conditionOrder;
    }
}
