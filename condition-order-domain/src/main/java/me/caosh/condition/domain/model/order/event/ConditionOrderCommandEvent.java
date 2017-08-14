package me.caosh.condition.domain.model.order.event;

import com.google.common.base.Preconditions;
import me.caosh.condition.domain.model.constants.OrderCommandType;
import me.caosh.condition.domain.model.order.ConditionOrder;

/**
 * Created by caosh on 2017/8/11.
 *
 * @author caoshuhao@touker.com
 */
public class ConditionOrderCommandEvent {
    private final OrderCommandType orderCommandType;
    private final ConditionOrder conditionOrder;

    public ConditionOrderCommandEvent(OrderCommandType orderCommandType, ConditionOrder conditionOrder) {
        Preconditions.checkArgument(orderCommandType != OrderCommandType.DELETE);
        this.orderCommandType = orderCommandType;
        this.conditionOrder = conditionOrder;
    }

    public OrderCommandType getOrderCommandType() {
        return orderCommandType;
    }

    public ConditionOrder getConditionOrder() {
        return conditionOrder;
    }
}
