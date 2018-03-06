package me.caosh.condition.domain.model.order.time;

import com.google.common.base.MoreObjects;
import hbec.intellitrade.common.security.SecurityInfo;
import me.caosh.condition.domain.model.condition.TimeReachedCondition;
import me.caosh.condition.domain.model.order.AbstractGeneralConditionOrder;
import me.caosh.condition.domain.model.order.TradeCustomer;
import me.caosh.condition.domain.model.order.TradeCustomerInfo;
import me.caosh.condition.domain.model.order.TradingMarketSupplier;
import me.caosh.condition.domain.model.order.constant.StrategyState;
import me.caosh.condition.domain.model.order.plan.BasicTradePlan;
import me.caosh.condition.domain.model.order.plan.TradePlan;
import me.caosh.condition.domain.model.signal.TradeSignal;
import me.caosh.condition.domain.model.strategy.condition.Condition;
import me.caosh.condition.domain.model.strategyinfo.NativeStrategyInfo;
import me.caosh.condition.domain.model.strategyinfo.StrategyInfo;

/**
 * Created by caosh on 2017/8/20.
 */
public class TimeOrder extends AbstractGeneralConditionOrder {

    private final TimeReachedCondition timeReachedCondition;
    private final BasicTradePlan tradePlan;

    public TimeOrder(Long orderId, TradeCustomerInfo tradeCustomerInfo, SecurityInfo securityInfo,
                     TimeReachedCondition timeCondition, BasicTradePlan tradePlan, StrategyState strategyState) {
        super(orderId, tradeCustomerInfo, securityInfo, strategyState);
        this.timeReachedCondition = timeCondition;
        this.tradePlan = tradePlan;
    }

    @Override
    public Condition getCondition() {
        return timeReachedCondition;
    }

    @Override
    public Condition getRawCondition() {
        return timeReachedCondition;
    }

    @Override
    public StrategyInfo getStrategyInfo() {
        return NativeStrategyInfo.TURN_UP;
    }

    @Override
    public TradePlan getTradePlan() {
        return tradePlan;
    }

    @Override
    public void onTradeSignal(TradeSignal tradeSignal, TradeCustomer tradeCustomer, TradingMarketSupplier tradingMarketSupplier) {
        super.onTradeSignal(tradeSignal, tradeCustomer, tradingMarketSupplier);
        setStrategyState(StrategyState.TERMINATED);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .addValue(super.toString())
                .add("timeReachedCondition", timeReachedCondition)
                .add("tradePlan", tradePlan)
                .toString();
    }
}
