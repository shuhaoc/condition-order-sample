package me.caosh.condition.domain.model.order;

import hbec.intellitrade.common.security.SecurityInfo;
import me.caosh.condition.domain.model.condition.PriceCondition;
import me.caosh.condition.domain.model.condition.TimeReachedCondition;
import me.caosh.condition.domain.model.condition.TurnUpCondition;
import me.caosh.condition.domain.model.order.constant.StrategyState;
import me.caosh.condition.domain.model.order.grid.GridCondition;
import me.caosh.condition.domain.model.order.grid.GridTradeOrder;
import me.caosh.condition.domain.model.order.newstock.NewStockOrder;
import me.caosh.condition.domain.model.order.newstock.NewStockPurchaseCondition;
import me.caosh.condition.domain.model.order.plan.BasicTradePlan;
import me.caosh.condition.domain.model.order.plan.DoubleDirectionTradePlan;
import me.caosh.condition.domain.model.order.plan.TradePlan;
import me.caosh.condition.domain.model.order.price.PriceOrder;
import me.caosh.condition.domain.model.order.time.TimeOrder;
import me.caosh.condition.domain.model.order.turnpoint.TurnUpBuyOrder;
import me.caosh.condition.domain.model.strategy.condition.Condition;
import me.caosh.condition.domain.model.strategyinfo.NativeStrategyInfo;
import me.caosh.condition.domain.model.strategyinfo.StrategyInfo;

/**
 * Created by caosh on 2017/8/9.
 */
public class ConditionOrderFactory {
    private static final ConditionOrderFactory INSTANCE = new ConditionOrderFactory();

    public static ConditionOrderFactory getInstance() {
        return INSTANCE;
    }

    public ConditionOrder create(Long orderId, TradeCustomerInfo tradeCustomerInfo, StrategyState strategyState,
                                 SecurityInfo securityInfo, StrategyInfo strategyInfo, Condition condition, TradePlan tradePlan) {
        if (strategyInfo == NativeStrategyInfo.PRICE) {
            return new PriceOrder(orderId, tradeCustomerInfo, securityInfo, (PriceCondition) condition,
                    (BasicTradePlan) tradePlan, strategyState);
        } else if (strategyInfo == NativeStrategyInfo.TURN_UP) {
            return new TurnUpBuyOrder(orderId, tradeCustomerInfo, securityInfo, (TurnUpCondition) condition,
                    (BasicTradePlan) tradePlan, strategyState);
        } else if (strategyInfo == NativeStrategyInfo.TIME) {
            return new TimeOrder(orderId, tradeCustomerInfo, securityInfo, (TimeReachedCondition) condition,
                    (BasicTradePlan) tradePlan, strategyState);
        } else if (strategyInfo == NativeStrategyInfo.GRID) {
            return new GridTradeOrder(orderId, tradeCustomerInfo, securityInfo, (GridCondition) condition,
                    (DoubleDirectionTradePlan) tradePlan, strategyState);
        } else if (strategyInfo == NativeStrategyInfo.NEW_STOCK) {
            return new NewStockOrder(orderId, tradeCustomerInfo, (NewStockPurchaseCondition) condition, strategyState);
        }
        throw new IllegalArgumentException("strategyInfo=" + strategyInfo);
    }

    private ConditionOrderFactory() {
    }
}
