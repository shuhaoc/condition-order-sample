package me.caosh.condition.infrastructure.repository.model;

import me.caosh.condition.domain.model.market.SecurityExchange;
import me.caosh.condition.domain.model.market.SecurityInfo;
import me.caosh.condition.domain.model.market.SecurityType;
import me.caosh.condition.domain.model.order.*;
import me.caosh.condition.domain.model.share.ValuedEnumUtil;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

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

    public static ConditionOrder fromDO(ConditionOrderDO conditionOrderDO) {
        OrderState orderState = ValuedEnumUtil.valueOf(conditionOrderDO.getOrderState(), OrderState.class);
        SecurityType securityType = ValuedEnumUtil.valueOf(conditionOrderDO.getSecurityType(), SecurityType.class);
        SecurityExchange securityExchange = SecurityExchange.valueOf(conditionOrderDO.getSecurityExchange());
        SecurityInfo securityInfo = new SecurityInfo(securityType, conditionOrderDO.getSecurityCode(), securityExchange,
                conditionOrderDO.getSecurityName());
        ExchangeType exchangeType = ValuedEnumUtil.valueOf(conditionOrderDO.getExchangeType(), ExchangeType.class);
        EntrustStrategy entrustStrategy = ValuedEnumUtil.valueOf(conditionOrderDO.getEntrustStrategy(), EntrustStrategy.class);
        TradePlan tradePlan = new TradePlan(exchangeType, entrustStrategy, conditionOrderDO.getEntrustAmount().intValue());
        Condition condition = ConditionOrderGsonUtils.getGSON().fromJson(conditionOrderDO.getInputArguments(), Condition.class);
        LocalDateTime createTime = conditionOrderDO.getCreateTime().toLocalDateTime();
        LocalDateTime updateTime = conditionOrderDO.getUpdateTime().toLocalDateTime();
        return new ConditionOrder(conditionOrderDO.getOrderId(), orderState, securityInfo, condition, tradePlan,
                createTime, updateTime);
    }

    private ConditionOrderDOAssembler() {
    }
}
