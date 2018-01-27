package me.caosh.condition.domain.model.order;

import com.google.common.base.MoreObjects;
import com.google.common.base.Optional;
import me.caosh.condition.domain.model.market.SecurityInfo;
import me.caosh.condition.domain.model.order.constant.StrategyState;
import me.caosh.condition.domain.model.strategy.description.StrategyInfo;
import org.joda.time.LocalDateTime;

/**
 * Created by caosh on 2017/8/2.
 */
public abstract class AbstractConditionOrder implements ConditionOrder {
    private final Long orderId;
    private final TradeCustomer tradeCustomer;
    private final SecurityInfo securityInfo;
    private final StrategyInfo strategyInfo;
    private StrategyState strategyState;

    public AbstractConditionOrder(Long orderId, TradeCustomer tradeCustomer, SecurityInfo securityInfo,
                                  StrategyInfo strategyInfo, StrategyState strategyState) {
        this.orderId = orderId;
        this.tradeCustomer = tradeCustomer;
        this.securityInfo = securityInfo;
        this.strategyInfo = strategyInfo;
        this.strategyState = strategyState;
    }

    @Override
    public Long getOrderId() {
        return orderId;
    }

    @Override
    public int getStrategyId() {
        return getOrderId().intValue();
    }

    @Override
    public TradeCustomer getCustomer() {
        return tradeCustomer;
    }

    @Override
    public StrategyState getStrategyState() {
        return strategyState;
    }

    @Override
    public void setStrategyState(StrategyState strategyState) {
        this.strategyState = strategyState;
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
    public Optional<LocalDateTime> getExpireTime() {
        return Optional.absent();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractConditionOrder that = (AbstractConditionOrder) o;

        if (!orderId.equals(that.orderId)) return false;
        if (!tradeCustomer.equals(that.tradeCustomer)) return false;
        if (!securityInfo.equals(that.securityInfo)) return false;
        if (!strategyInfo.equals(that.strategyInfo)) return false;
        return strategyState == that.strategyState;
    }

    @Override
    public int hashCode() {
        int result = orderId.hashCode();
        result = 31 * result + tradeCustomer.hashCode();
        result = 31 * result + securityInfo.hashCode();
        result = 31 * result + strategyInfo.hashCode();
        result = 31 * result + strategyState.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(AbstractConditionOrder.class).omitNullValues()
                .addValue(AbstractConditionOrder.class.getSuperclass() != Object.class ? super.toString() : null)
                .add("orderId", orderId)
                .add("tradeCustomer", tradeCustomer)
                .add("securityInfo", securityInfo)
                .add("strategyInfo", strategyInfo)
                .add("strategyState", strategyState)
                .toString();
    }
}
