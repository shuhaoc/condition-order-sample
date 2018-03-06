package me.caosh.condition.domain.model.order;

import com.google.common.base.MoreObjects;
import com.google.common.base.Optional;
import hbec.intellitrade.common.security.SecurityInfo;
import me.caosh.condition.domain.model.order.constant.StrategyState;
import org.joda.time.LocalDateTime;

/**
 * Created by caosh on 2017/8/2.
 */
public abstract class AbstractConditionOrder implements ConditionOrder {
    private final Long orderId;
    private final TradeCustomerInfo tradeCustomerInfo;
    private final SecurityInfo securityInfo;
    private StrategyState strategyState;

    public AbstractConditionOrder(Long orderId, TradeCustomerInfo tradeCustomerInfo, SecurityInfo securityInfo,
                                  StrategyState strategyState) {
        this.orderId = orderId;
        this.tradeCustomerInfo = tradeCustomerInfo;
        this.securityInfo = securityInfo;
        this.strategyState = strategyState;
    }

    @Override
    public Long getOrderId() {
        return orderId;
    }

    @Override
    public long getStrategyId() {
        return getOrderId().intValue();
    }

    @Override
    public TradeCustomerInfo getCustomer() {
        return tradeCustomerInfo;
    }

    @Override
    public StrategyState getStrategyState() {
        return strategyState;
    }

    protected void setStrategyState(StrategyState strategyState) {
        this.strategyState = strategyState;
    }

    @Override
    public SecurityInfo getSecurityInfo() {
        return securityInfo;
    }

    @Override
    public Optional<LocalDateTime> getExpireTime() {
        return Optional.absent();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractConditionOrder that = (AbstractConditionOrder) o;

        if (!orderId.equals(that.orderId)) return false;
        if (!tradeCustomerInfo.equals(that.tradeCustomerInfo)) return false;
        if (!securityInfo.equals(that.securityInfo)) return false;
        return strategyState == that.strategyState;
    }

    @Override
    public int hashCode() {
        int result = orderId.hashCode();
        result = 31 * result + tradeCustomerInfo.hashCode();
        result = 31 * result + securityInfo.hashCode();
        result = 31 * result + strategyState.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(AbstractConditionOrder.class).omitNullValues()
                .add("orderId", orderId)
                .add("tradeCustomerInfo", tradeCustomerInfo)
                .add("securityInfo", securityInfo)
                .add("strategyState", strategyState)
                .toString();
    }
}
