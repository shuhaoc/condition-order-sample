package me.caosh.condition.domain.model.order;

import com.google.common.base.MoreObjects;
import me.caosh.condition.domain.model.market.SecurityInfo;

import java.time.LocalDateTime;

/**
 * Created by caosh on 2017/8/2.
 */
public class ConditionOrder {
    private final Long orderId;
    private OrderState orderState;
    private final SecurityInfo securityInfo;
    private final Condition condition;
    private final TradePlan tradePlan;
    private final LocalDateTime createTime;
    private LocalDateTime updateTime;

    public ConditionOrder(Long orderId, OrderState orderState, SecurityInfo securityInfo, Condition condition,
                          TradePlan tradePlan, LocalDateTime createTime, LocalDateTime updateTime) {
        this.orderId = orderId;
        this.orderState = orderState;
        this.securityInfo = securityInfo;
        this.condition = condition;
        this.tradePlan = tradePlan;
        this.createTime = createTime;
        this.updateTime = updateTime;
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

    public Condition getCondition() {
        return condition;
    }

    public TradePlan getTradePlan() {
        return tradePlan;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("orderId", orderId)
                .add("orderState", orderState)
                .add("securityInfo", securityInfo)
                .add("condition", condition)
                .add("tradePlan", tradePlan)
                .add("createTime", createTime)
                .add("updateTime", updateTime)
                .toString();
    }
}
