package me.caosh.condition.domain.model.order;

import com.google.common.base.MoreObjects;
import me.caosh.condition.domain.model.market.SecurityInfo;

import java.time.LocalDateTime;

/**
 * Created by caosh on 2017/8/1.
 */
public class PriceOrder implements ConditionOrder {
    private final Long orderId;
    private OrderState orderState;
    private final SecurityInfo securityInfo;
    private final SimplePriceCondition simplePriceCondition;
    private final TradePlan tradePlan;
    private final LocalDateTime createTime;
    private LocalDateTime updateTime;

    public PriceOrder(Long orderId, OrderState orderState, SecurityInfo securityInfo, SimplePriceCondition simplePriceCondition,
                      TradePlan tradePlan, LocalDateTime createTime, LocalDateTime updateTime) {
        this.orderId = orderId;
        this.orderState = orderState;
        this.securityInfo = securityInfo;
        this.simplePriceCondition = simplePriceCondition;
        this.tradePlan = tradePlan;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    @Override
    public Long getOrderId() {
        return orderId;
    }

    @Override
    public OrderState getOrderState() {
        return orderState;
    }

    public void setOrderState(OrderState orderState) {
        this.orderState = orderState;
    }

    @Override
    public SecurityInfo getSecurityInfo() {
        return securityInfo;
    }

    @Override
    public Condition getCondition() {
        return getSimplePriceCondition();
    }

    public SimplePriceCondition getSimplePriceCondition() {
        return simplePriceCondition;
    }

    @Override
    public TradePlan getTradePlan() {
        return tradePlan;
    }

    @Override
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    @Override
    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("orderId", orderId)
                .add("orderState", orderState)
                .add("securityInfo", securityInfo)
                .add("simplePriceCondition", simplePriceCondition)
                .add("tradePlan", tradePlan)
                .add("createTime", createTime)
                .add("updateTime", updateTime)
                .toString();
    }
}
