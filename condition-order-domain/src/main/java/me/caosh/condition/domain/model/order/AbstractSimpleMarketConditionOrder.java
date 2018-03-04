package me.caosh.condition.domain.model.order;

import com.google.common.base.MoreObjects;
import me.caosh.condition.domain.model.market.RealTimeMarket;
import me.caosh.condition.domain.model.market.SecurityInfo;
import me.caosh.condition.domain.model.order.constant.StrategyState;
import me.caosh.condition.domain.model.order.plan.BasicTradePlan;
import me.caosh.condition.domain.model.order.plan.TradePlan;
import me.caosh.condition.domain.model.signal.TradeSignal;

/**
 * Created by caosh on 2017/8/20.
 */
public abstract class AbstractSimpleMarketConditionOrder extends AbstractMarketConditionOrder {
    private final BasicTradePlan tradePlan;

    public AbstractSimpleMarketConditionOrder(Long orderId, TradeCustomerInfo tradeCustomerInfo, SecurityInfo securityInfo,
                                              BasicTradePlan tradePlan, StrategyState strategyState) {
        super(orderId, tradeCustomerInfo, securityInfo, strategyState);
        this.tradePlan = tradePlan;
    }

    @Override
    public TradePlan getTradePlan() {
        return tradePlan;
    }

    @Override
    public void onTradeSignal(TradeSignal tradeSignal, TradeCustomer tradeCustomer, TradingMarketSupplier tradingMarketSupplier, RealTimeMarket realTimeMarket) {
        super.onTradeSignal(tradeSignal, tradeCustomer, tradingMarketSupplier, realTimeMarket);
        setStrategyState(StrategyState.TERMINATED);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        AbstractSimpleMarketConditionOrder that = (AbstractSimpleMarketConditionOrder) o;

        return tradePlan.equals(that.tradePlan);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + tradePlan.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .addValue(super.toString())
                .add("tradePlan", tradePlan)
                .toString();
    }
}
