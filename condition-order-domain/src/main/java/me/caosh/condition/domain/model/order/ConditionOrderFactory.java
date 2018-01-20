package me.caosh.condition.domain.model.order;

import me.caosh.condition.domain.model.market.SecurityInfo;
import me.caosh.condition.domain.model.order.constant.OrderState;
import me.caosh.condition.domain.model.order.grid.GridCondition;
import me.caosh.condition.domain.model.order.grid.GridTradeOrder;
import me.caosh.condition.domain.model.order.newstock.NewStockOrder;
import me.caosh.condition.domain.model.order.newstock.NewStockPurchaseCondition;
import me.caosh.condition.domain.model.order.plan.DoubleDirectionTradePlan;
import me.caosh.condition.domain.model.order.plan.SingleDirectionTradePlan;
import me.caosh.condition.domain.model.order.plan.TradePlan;
import me.caosh.condition.domain.model.order.price.PriceCondition;
import me.caosh.condition.domain.model.order.price.PriceOrder;
import me.caosh.condition.domain.model.order.time.SimpleTimeCondition;
import me.caosh.condition.domain.model.order.time.TimeOrder;
import me.caosh.condition.domain.model.order.turnpoint.TurnUpBuyOrder;
import me.caosh.condition.domain.model.order.turnpoint.TurnUpCondition;
import me.caosh.condition.domain.model.strategy.NativeStrategyInfo;
import me.caosh.condition.domain.model.strategy.StrategyInfo;

/**
 * Created by caosh on 2017/8/9.
 */
public class ConditionOrderFactory {
    private static final ConditionOrderFactory INSTANCE = new ConditionOrderFactory();

    public static ConditionOrderFactory getInstance() {
        return INSTANCE;
    }

    public ConditionOrder create(Long orderId, TradeCustomer customerIdentity, boolean deleted, OrderState orderState,
                                 SecurityInfo securityInfo, StrategyInfo strategyInfo, Condition condition, TradePlan tradePlan) {
        if (strategyInfo == NativeStrategyInfo.PRICE) {
            return new PriceOrder(orderId, customerIdentity, securityInfo, (PriceCondition) condition,
                    (SingleDirectionTradePlan) tradePlan, orderState);
        } else if (strategyInfo == NativeStrategyInfo.TURN_UP) {
            return new TurnUpBuyOrder(orderId, customerIdentity, securityInfo, (TurnUpCondition) condition,
                    (SingleDirectionTradePlan) tradePlan, orderState);
        } else if (strategyInfo == NativeStrategyInfo.TIME) {
            return new TimeOrder(orderId, customerIdentity, securityInfo, (SimpleTimeCondition) condition,
                    (SingleDirectionTradePlan) tradePlan, orderState);
        } else if (strategyInfo == NativeStrategyInfo.GRID) {
            return new GridTradeOrder(orderId, customerIdentity, securityInfo, (GridCondition) condition,
                    (DoubleDirectionTradePlan) tradePlan, orderState);
        } else if (strategyInfo == NativeStrategyInfo.NEW_STOCK) {
            return new NewStockOrder(orderId, customerIdentity, (NewStockPurchaseCondition) condition, orderState);
        }
        throw new IllegalArgumentException("strategyInfo=" + strategyInfo);
    }

    private ConditionOrderFactory() {
    }
}
