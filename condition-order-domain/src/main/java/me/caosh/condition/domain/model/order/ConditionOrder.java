package me.caosh.condition.domain.model.order;

import hbec.intellitrade.strategy.domain.Strategy;
import me.caosh.condition.domain.model.market.SecurityInfo;
import me.caosh.condition.domain.model.order.constant.StrategyState;
import me.caosh.condition.domain.model.order.plan.TradePlan;
import me.caosh.condition.domain.model.signal.TradeSignal;
import me.caosh.condition.domain.model.strategy.condition.Condition;
import me.caosh.condition.domain.model.strategyinfo.StrategyInfo;

/**
 * 条件单定义
 * Created by caosh on 2017/8/29.
 */
public interface ConditionOrder extends Strategy {
    Long getOrderId();

    TradeCustomerInfo getCustomer();

    SecurityInfo getSecurityInfo();

    StrategyInfo getStrategyInfo();

    Condition getRawCondition();

    TradePlan getTradePlan();

    @Override
    StrategyState getStrategyState();

    void onTradeSignal(TradeSignal tradeSignal, TradeCustomer tradeCustomer, TradingMarketSupplier tradingMarketSupplier);
}
