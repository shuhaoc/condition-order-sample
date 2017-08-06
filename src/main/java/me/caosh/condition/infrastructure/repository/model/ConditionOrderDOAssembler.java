package me.caosh.condition.infrastructure.repository.model;

import me.caosh.condition.domain.model.order.ConditionOrder;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by caosh on 2017/8/3.
 */
public class ConditionOrderDOAssembler {
    public static ConditionOrderDO toDO(ConditionOrder conditionOrder) {
        ConditionOrderDO conditionOrderDO = new ConditionOrderDO();
        conditionOrderDO.setOrderId(conditionOrder.getOrderId());
        conditionOrderDO.setOrderState(conditionOrder.getOrderState().getValue());
        conditionOrderDO.setSecurityType(conditionOrder.getSecurityInfo().getType().getValue());
        conditionOrderDO.setSecurityCode(conditionOrder.getSecurityInfo().getCode());
        conditionOrderDO.setSecurityExchange(conditionOrder.getSecurityInfo().getExchange().name());
        conditionOrderDO.setSecurityName(conditionOrder.getSecurityInfo().getName());
        conditionOrderDO.setInputArguments(ConditionOrderGsonUtils.getGSON().toJson(conditionOrder.getCondition()));
        conditionOrderDO.setExchangeType(conditionOrder.getTradePlan().getExchangeType().getValue());
        conditionOrderDO.setEntrustStrategy(conditionOrder.getTradePlan().getEntrustStrategy().getValue());
        conditionOrderDO.setEntrustAmount(BigDecimal.valueOf(conditionOrder.getTradePlan().getNumber()));
        conditionOrderDO.setCreateTime(Timestamp.valueOf(conditionOrder.getCreateTime()));
        conditionOrderDO.setUpdateTime(Timestamp.valueOf(conditionOrder.getUpdateTime()));
        return conditionOrderDO;
    }

    private ConditionOrderDOAssembler() {
    }
}
