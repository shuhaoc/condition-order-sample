package me.caosh.condition.infrastructure.repository.model;

import me.caosh.condition.domain.model.order.ConditionOrder;

/**
 * Created by caosh on 2017/8/3.
 */
public class ConditionOrderDOAssembler {
    public static ConditionOrderDO toDO(ConditionOrder conditionOrder) {
        ConditionOrderDO conditionOrderDO = new ConditionOrderDO();
        conditionOrderDO.setOrderId(conditionOrder.getOrderId());
        conditionOrderDO.setSecurityType(conditionOrder.getSecurityInfo().getType().getValue());
        conditionOrderDO.setSecurityCode(conditionOrder.getSecurityInfo().getCode());
        conditionOrderDO.setSecurityExchange(conditionOrder.getSecurityInfo().getExchange().name());
        conditionOrderDO.setSecurityName(conditionOrder.getSecurityInfo().getName());


        return conditionOrderDO;
    }

    private ConditionOrderDOAssembler() {
    }
}
