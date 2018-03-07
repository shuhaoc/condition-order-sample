package hbec.intellitrade.condorder.domain;

import hbec.intellitrade.common.security.SecurityInfo;
import hbec.intellitrade.condorder.domain.orders.PriceOrder;
import hbec.intellitrade.condorder.domain.strategyinfo.NativeStrategyInfo;
import hbec.intellitrade.condorder.domain.strategyinfo.StrategyInfo;
import hbec.intellitrade.condorder.domain.tradeplan.BasicTradePlan;
import hbec.intellitrade.condorder.domain.tradeplan.DoubleDirectionTradePlan;
import hbec.intellitrade.condorder.domain.tradeplan.TradePlan;
import hbec.intellitrade.strategy.domain.condition.Condition;
import hbec.intellitrade.strategy.domain.strategies.condition.PriceCondition;
import me.caosh.condition.domain.model.condition.TimeReachedCondition;
import me.caosh.condition.domain.model.condition.TurnUpCondition;
import me.caosh.condition.domain.model.order.grid.GridCondition;
import me.caosh.condition.domain.model.order.grid.GridTradeOrder;
import me.caosh.condition.domain.model.order.newstock.NewStockOrder;
import me.caosh.condition.domain.model.order.newstock.NewStockPurchaseCondition;
import me.caosh.condition.domain.model.order.time.TimeOrder;
import me.caosh.condition.domain.model.order.turnpoint.TurnUpBuyOrder;
import org.joda.time.LocalDateTime;

/**
 * Created by caosh on 2017/8/9.
 */
public class ConditionOrderFactory {
    private static final ConditionOrderFactory INSTANCE = new ConditionOrderFactory();

    public static ConditionOrderFactory getInstance() {
        return INSTANCE;
    }

    public ConditionOrder create(Long orderId, TradeCustomerInfo tradeCustomerInfo, StrategyState strategyState,
                                 SecurityInfo securityInfo, StrategyInfo strategyInfo, Condition condition,
                                 LocalDateTime expireTime, TradePlan tradePlan) {
        if (strategyInfo == NativeStrategyInfo.PRICE) {
            return new PriceOrder(orderId, tradeCustomerInfo, securityInfo, (PriceCondition) condition, expireTime,
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
