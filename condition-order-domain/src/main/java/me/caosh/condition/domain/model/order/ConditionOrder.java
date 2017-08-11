package me.caosh.condition.domain.model.order;

import com.google.common.base.MoreObjects;
import me.caosh.condition.domain.model.market.SecurityInfo;
import me.caosh.condition.domain.model.strategy.StrategyInfo;

import java.time.LocalDateTime;

/**
 * Created by caosh on 2017/8/2.
 */
public abstract class ConditionOrder {
    private final Long orderId;
    private OrderState orderState;
    private final SecurityInfo securityInfo;
    private final StrategyInfo strategyInfo;
    private final Condition condition;
    private final TradePlan tradePlan;

    public ConditionOrder(Long orderId, OrderState orderState, SecurityInfo securityInfo, StrategyInfo strategyInfo,
                          Condition condition, TradePlan tradePlan) {
        this.orderId = orderId;
        this.orderState = orderState;
        this.securityInfo = securityInfo;
        this.strategyInfo = strategyInfo;
        this.condition = condition;
        this.tradePlan = tradePlan;
    }

    public Long getOrderId() {
        return orderId;
    }

    public OrderState getOrderState() {
        return orderState;
    }

    public void setOrderState(OrderState orderState) {
        this.orderState = orderState;
    }

    public SecurityInfo getSecurityInfo() {
        return securityInfo;
    }

    public StrategyInfo getStrategyInfo() {
        return strategyInfo;
    }

    public Condition getCondition() {
        return condition;
    }

    public TradePlan getTradePlan() {
        return tradePlan;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("orderId", orderId)
                .add("orderState", orderState)
                .add("securityInfo", securityInfo)
                .add("strategyInfo", strategyInfo)
                .add("condition", condition)
                .add("tradePlan", tradePlan)
                .toString();
    }
}
