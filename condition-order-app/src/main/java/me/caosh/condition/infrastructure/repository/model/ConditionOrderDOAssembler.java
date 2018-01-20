package me.caosh.condition.infrastructure.repository.model;

import me.caosh.condition.domain.model.constants.SecurityExchange;
import me.caosh.condition.domain.model.constants.SecurityType;
import me.caosh.condition.domain.model.market.SecurityInfo;
import me.caosh.condition.domain.model.order.Condition;
import me.caosh.condition.domain.model.order.ConditionOrder;
import me.caosh.condition.domain.model.order.ConditionOrderFactory;
import me.caosh.condition.domain.model.order.TradeCustomer;
import me.caosh.condition.domain.model.order.constant.OrderState;
import me.caosh.condition.domain.model.order.plan.AutoPurchaseTradePlan;
import me.caosh.condition.domain.model.order.plan.DoubleDirectionTradePlan;
import me.caosh.condition.domain.model.order.plan.SingleDirectionTradePlan;
import me.caosh.condition.domain.model.order.plan.TradeNumberByAmount;
import me.caosh.condition.domain.model.order.plan.TradeNumberDirect;
import me.caosh.condition.domain.model.order.plan.TradeNumberVisitor;
import me.caosh.condition.domain.model.order.plan.TradePlan;
import me.caosh.condition.domain.model.order.plan.TradePlanFactory;
import me.caosh.condition.domain.model.order.plan.TradePlanVisitor;
import me.caosh.condition.domain.model.share.ValuedEnumUtil;
import me.caosh.condition.domain.model.strategy.NativeStrategyInfo;
import me.caosh.condition.infrastructure.repository.impl.ConditionOrderDOGSONUtils;

import java.math.BigDecimal;

/**
 * Created by caosh on 2017/8/3.
 */
public class ConditionOrderDOAssembler {
    public static ConditionOrderDO toDO(ConditionOrder conditionOrder) {
        ConditionOrderDO conditionOrderDO = new ConditionOrderDO();
        conditionOrderDO.setOrderId(conditionOrder.getOrderId());
        conditionOrderDO.setUserId(conditionOrder.getCustomer().getUserId());
        conditionOrderDO.setCustomerNo(conditionOrder.getCustomer().getCustomerNo());
        conditionOrderDO.setDeleted(false);
        conditionOrderDO.setOrderState(conditionOrder.getOrderState().getValue());
        conditionOrderDO.setSecurityType(conditionOrder.getSecurityInfo().getType().getValue());
        conditionOrderDO.setSecurityCode(conditionOrder.getSecurityInfo().getCode());
        conditionOrderDO.setSecurityExchange(conditionOrder.getSecurityInfo().getExchange().name());
        conditionOrderDO.setSecurityName(conditionOrder.getSecurityInfo().getName());
        conditionOrderDO.setStrategyId(conditionOrder.getStrategyInfo().getStrategyId());
        ConditionDO conditionDO = new ConditionDOBuilder(conditionOrder.getCondition()).build();
        conditionOrderDO.setConditionProperties(ConditionOrderDOGSONUtils.getGSON().toJson(conditionDO));
        DynamicPropertiesDO dynamicPropertiesDO = new DynamicPropertiesDOBuilder(conditionOrder.getCondition()).build();
        String dynamicPropertiesJson = dynamicPropertiesDO != null
                ? ConditionOrderDOGSONUtils.getGSON().toJson(dynamicPropertiesDO)
                : null;
        conditionOrderDO.setDynamicProperties(dynamicPropertiesJson);

        conditionOrder.getTradePlan().accept(new TradePlanVisitor() {
            @Override
            public void visitSingleDirectionTradePlan(SingleDirectionTradePlan singleDirectionTradePlan) {
                conditionOrderDO.setExchangeType(singleDirectionTradePlan.getExchangeType().getValue());
                conditionOrderDO.setEntrustStrategy(singleDirectionTradePlan.getEntrustStrategy().getValue());
            }

            @Override
            public void visitDoubleDirectionTradePlan(DoubleDirectionTradePlan doubleDirectionTradePlan) {
                conditionOrderDO.setExchangeType(TradePlanFactory.DOUBLE_EXCHANGE_TYPE);
                conditionOrderDO.setEntrustStrategy(doubleDirectionTradePlan.getBuyPlan().getEntrustStrategy().getValue());
            }

            @Override
            public void visitAutoPurchaseTradePlan(AutoPurchaseTradePlan autoPurchaseTradePlan) {
                conditionOrderDO.setExchangeType(autoPurchaseTradePlan.getExchangeTypeValue());
                conditionOrderDO.setEntrustStrategy(autoPurchaseTradePlan.getEntrustStrategyValue());
            }
        });

        conditionOrderDO.setEntrustMethod(conditionOrder.getTradePlan().getTradeNumber().getEntrustMethod().getValue());
        conditionOrder.getTradePlan().getTradeNumber().accept(new TradeNumberVisitor() {
            @Override
            public void visitTradeNumberDirect(TradeNumberDirect tradeNumberDirect) {
                conditionOrderDO.setEntrustAmount(BigDecimal.valueOf(tradeNumberDirect.getNumber()));
            }

            @Override
            public void visitTradeNumberByAmount(TradeNumberByAmount tradeNumberByAmount) {
                conditionOrderDO.setEntrustAmount(tradeNumberByAmount.getAmount());
            }
        });
        return conditionOrderDO;
    }

    public static ConditionOrder fromDO(ConditionOrderDO conditionOrderDO) {
        TradeCustomer customerIdentity = new TradeCustomer(conditionOrderDO.getUserId(), conditionOrderDO.getCustomerNo());
        OrderState orderState = ValuedEnumUtil.valueOf(conditionOrderDO.getOrderState(), OrderState.class);
        SecurityType securityType = ValuedEnumUtil.valueOf(conditionOrderDO.getSecurityType(), SecurityType.class);
        SecurityExchange securityExchange = SecurityExchange.valueOf(conditionOrderDO.getSecurityExchange());
        SecurityInfo securityInfo = new SecurityInfo(securityType, conditionOrderDO.getSecurityCode(), securityExchange,
                conditionOrderDO.getSecurityName());
        NativeStrategyInfo nativeStrategyInfo = ValuedEnumUtil.valueOf(conditionOrderDO.getStrategyId(), NativeStrategyInfo.class);
        TradePlan tradePlan = TradePlanFactory.getInstance().create(conditionOrderDO.getExchangeType(), conditionOrderDO.getEntrustStrategy(),
                conditionOrderDO.getEntrustMethod(), conditionOrderDO.getEntrustAmount().intValue(), conditionOrderDO.getEntrustAmount());
        ;
        ConditionDO conditionDO = ConditionOrderDOGSONUtils.getGSON().fromJson(conditionOrderDO.getConditionProperties(), ConditionDO.class);
        DynamicPropertiesDO dynamicPropertiesDO = ConditionOrderDOGSONUtils.getGSON().fromJson(conditionOrderDO.getDynamicProperties(), DynamicPropertiesDO.class);
        Condition condition = new ConditionBuilder(conditionDO, dynamicPropertiesDO).build();
        return ConditionOrderFactory.getInstance().create(conditionOrderDO.getOrderId(), customerIdentity, conditionOrderDO.getDeleted(),
                orderState, securityInfo, nativeStrategyInfo, condition, tradePlan);
    }

    private ConditionOrderDOAssembler() {
    }
}
