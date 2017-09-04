package me.caosh.condition.domain.model.order;

import com.google.common.base.MoreObjects;
import me.caosh.condition.domain.model.market.SecurityInfo;
import me.caosh.condition.domain.model.order.constant.OrderState;
import me.caosh.condition.domain.model.strategy.StrategyInfo;

/**
 * Created by caosh on 2017/8/2.
 */
public abstract class AbstractConditionOrder implements ConditionOrder {
    private final Long orderId;
    private final TradeCustomerIdentity customerIdentity;
    private final boolean deleted;
    private final SecurityInfo securityInfo;
    private final StrategyInfo strategyInfo;
    private OrderState orderState;

    public AbstractConditionOrder(Long orderId, TradeCustomerIdentity customerIdentity, boolean deleted, SecurityInfo securityInfo,
                                  StrategyInfo strategyInfo, OrderState orderState) {
        this.orderId = orderId;
        this.customerIdentity = customerIdentity;
        this.deleted = deleted;
        this.securityInfo = securityInfo;
        this.strategyInfo = strategyInfo;
        this.orderState = orderState;
    }

    @Override
    public Long getOrderId() {
        return orderId;
    }

    @Override
    public TradeCustomerIdentity getCustomerIdentity() {
        return customerIdentity;
    }

    @Override
    public boolean isDeleted() {
        return deleted;
    }

    @Override
    public OrderState getOrderState() {
        return orderState;
    }

    @Override
    public void setOrderState(OrderState orderState) {
        this.orderState = orderState;
    }

    @Override
    public SecurityInfo getSecurityInfo() {
        return securityInfo;
    }

    @Override
    public StrategyInfo getStrategyInfo() {
        return strategyInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractConditionOrder)) return false;

        AbstractConditionOrder that = (AbstractConditionOrder) o;

        return !(orderId != null ? !orderId.equals(that.orderId) : that.orderId != null);
    }

    @Override
    public int hashCode() {
        return orderId != null ? orderId.hashCode() : 0;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("orderId", orderId)
                .add("customerIdentity", customerIdentity)
                .add("deleted", deleted)
                .add("securityInfo", securityInfo)
                .add("strategyInfo", strategyInfo)
                .add("orderState", orderState)
                .toString();
    }
}
