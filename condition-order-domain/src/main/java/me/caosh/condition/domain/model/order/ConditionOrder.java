package me.caosh.condition.domain.model.order;

import me.caosh.condition.domain.model.market.SecurityInfo;
import me.caosh.condition.domain.model.order.constant.OrderState;
import me.caosh.condition.domain.model.order.plan.TradePlan;
import me.caosh.condition.domain.model.strategy.StrategyInfo;

/**
 * 条件单定义
 * Created by caosh on 2017/8/29.
 */
public interface ConditionOrder {
    Long getOrderId();

    TradeCustomerIdentity getCustomerIdentity();

    SecurityInfo getSecurityInfo();

    StrategyInfo getStrategyInfo();

    Condition getCondition();

    TradePlan getTradePlan();

    OrderState getOrderState();

    void setOrderState(OrderState orderState);
}
