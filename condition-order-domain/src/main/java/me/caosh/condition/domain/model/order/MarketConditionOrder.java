package me.caosh.condition.domain.model.order;

import me.caosh.condition.domain.model.market.SecurityInfo;
import me.caosh.condition.domain.model.order.constant.OrderState;
import me.caosh.condition.domain.model.order.plan.TradePlan;
import me.caosh.condition.domain.model.strategy.StrategyInfo;
import me.caosh.condition.domain.model.trade.SingleEntrustOnTrigger;

/**
 * Created by caosh on 2017/8/20.
 */
public abstract class MarketConditionOrder extends AbstractConditionOrder implements RealTimeMarketDriven, SingleEntrustOnTrigger {
    public MarketConditionOrder(Long orderId, TradeCustomerIdentity customerIdentity, boolean deleted, SecurityInfo securityInfo,
                                StrategyInfo strategyInfo, Condition condition, TradePlan tradePlan, OrderState orderState) {
        super(orderId, customerIdentity, deleted, securityInfo, strategyInfo, condition, tradePlan, orderState);
    }
}
