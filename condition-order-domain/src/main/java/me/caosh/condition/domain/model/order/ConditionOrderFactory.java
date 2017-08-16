package me.caosh.condition.domain.model.order;

import me.caosh.condition.domain.model.market.SecurityInfo;
import me.caosh.condition.domain.model.order.price.PriceCondition;
import me.caosh.condition.domain.model.order.price.PriceOrder;
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

    public ConditionOrder create(Long orderId, TradeCustomerIdentity customerIdentity, boolean deleted, OrderState orderState,
                                 SecurityInfo securityInfo, StrategyInfo strategyInfo, Condition condition, TradePlan tradePlan) {
        if (strategyInfo == NativeStrategyInfo.PRICE) {
            return new PriceOrder(orderId, customerIdentity, deleted, orderState, securityInfo, NativeStrategyInfo.PRICE,
                    (PriceCondition) condition, tradePlan);
        }
        throw new IllegalArgumentException("strategyInfo=" + strategyInfo);
    }

    private ConditionOrderFactory() {
    }
}
