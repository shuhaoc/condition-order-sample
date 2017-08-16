package me.caosh.condition.infrastructure.repository.model;

import me.caosh.condition.domain.model.market.SecurityExchange;
import me.caosh.condition.domain.model.market.SecurityInfo;
import me.caosh.condition.domain.model.market.SecurityType;
import me.caosh.condition.domain.model.order.*;
import me.caosh.condition.domain.model.share.ValuedEnumUtil;
import me.caosh.condition.domain.model.strategy.NativeStrategyInfo;
import me.caosh.condition.domain.util.ConditionOrderGSONUtils;
import me.caosh.condition.infrastructure.repository.impl.ConditionOrderDOGSONUtils;

import java.math.BigDecimal;

/**
 * Created by caosh on 2017/8/3.
 */
public class ConditionOrderDOAssembler {
    public static ConditionOrderDO toDO(ConditionOrder conditionOrder) {
        ConditionOrderDO conditionOrderDO = new ConditionOrderDO();
        conditionOrderDO.setOrderId(conditionOrder.getOrderId());
        conditionOrderDO.setUserId(conditionOrder.getCustomerIdentity().getUserId());
        conditionOrderDO.setCustomerNo(conditionOrder.getCustomerIdentity().getCustomerNo());
        conditionOrderDO.setDeleted(conditionOrder.isDeleted());
        conditionOrderDO.setOrderState(conditionOrder.getOrderState().getValue());
        conditionOrderDO.setSecurityType(conditionOrder.getSecurityInfo().getType().getValue());
        conditionOrderDO.setSecurityCode(conditionOrder.getSecurityInfo().getCode());
        conditionOrderDO.setSecurityExchange(conditionOrder.getSecurityInfo().getExchange().name());
        conditionOrderDO.setSecurityName(conditionOrder.getSecurityInfo().getName());
        conditionOrderDO.setStrategyId(conditionOrder.getStrategyInfo().getStrategyId());
        ConditionDO conditionDO = new ConditionDOBuilder(conditionOrder.getCondition()).build();
        conditionOrderDO.setConditionProperties(ConditionOrderDOGSONUtils.getGSON().toJson(conditionDO));
        conditionOrderDO.setExchangeType(conditionOrder.getTradePlan().getExchangeType().getValue());
        conditionOrderDO.setEntrustStrategy(conditionOrder.getTradePlan().getEntrustStrategy().getValue());
        conditionOrderDO.setEntrustAmount(BigDecimal.valueOf(conditionOrder.getTradePlan().getNumber()));
        return conditionOrderDO;
    }

    public static ConditionOrder fromDO(ConditionOrderDO conditionOrderDO) {
        TradeCustomerIdentity customerIdentity = new TradeCustomerIdentity(conditionOrderDO.getUserId(), conditionOrderDO.getCustomerNo());
        OrderState orderState = ValuedEnumUtil.valueOf(conditionOrderDO.getOrderState(), OrderState.class);
        SecurityType securityType = ValuedEnumUtil.valueOf(conditionOrderDO.getSecurityType(), SecurityType.class);
        SecurityExchange securityExchange = SecurityExchange.valueOf(conditionOrderDO.getSecurityExchange());
        SecurityInfo securityInfo = new SecurityInfo(securityType, conditionOrderDO.getSecurityCode(), securityExchange,
                conditionOrderDO.getSecurityName());
        NativeStrategyInfo nativeStrategyInfo = ValuedEnumUtil.valueOf(conditionOrderDO.getStrategyId(), NativeStrategyInfo.class);
        ExchangeType exchangeType = ValuedEnumUtil.valueOf(conditionOrderDO.getExchangeType(), ExchangeType.class);
        EntrustStrategy entrustStrategy = ValuedEnumUtil.valueOf(conditionOrderDO.getEntrustStrategy(), EntrustStrategy.class);
        TradePlan tradePlan = new TradePlan(exchangeType, entrustStrategy, conditionOrderDO.getEntrustAmount().intValue());
        ConditionDO conditionDO = ConditionOrderDOGSONUtils.getGSON().fromJson(conditionOrderDO.getConditionProperties(), ConditionDO.class);
        Condition condition = new ConditionBuilder(conditionDO).build();
        return ConditionOrderFactory.getInstance().create(conditionOrderDO.getOrderId(), customerIdentity, conditionOrderDO.getDeleted(),
                orderState, securityInfo, nativeStrategyInfo, condition, tradePlan);
    }

    private ConditionOrderDOAssembler() {
    }
}
