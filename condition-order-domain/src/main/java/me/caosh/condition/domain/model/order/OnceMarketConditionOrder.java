package me.caosh.condition.domain.model.order;

import me.caosh.condition.domain.model.market.RealTimeMarket;
import me.caosh.condition.domain.model.market.SecurityInfo;
import me.caosh.condition.domain.model.order.constant.OrderState;
import me.caosh.condition.domain.model.order.plan.TradePlan;
import me.caosh.condition.domain.model.signal.TradeSignal;
import me.caosh.condition.domain.model.strategy.StrategyInfo;
import me.caosh.condition.domain.model.trade.EntrustCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by caosh on 2017/8/19.
 */
public abstract class OnceMarketConditionOrder extends MarketConditionOrder {
    private static final Logger logger = LoggerFactory.getLogger(OnceMarketConditionOrder.class);

    public OnceMarketConditionOrder(Long orderId, TradeCustomerIdentity customerIdentity, boolean deleted, SecurityInfo securityInfo,
                                    StrategyInfo strategyInfo, Condition condition, TradePlan tradePlan, OrderState orderState) {
        super(orderId, customerIdentity, deleted, securityInfo, strategyInfo, condition, tradePlan, orderState);
    }

    @Override
    public EntrustCommand onTradeSignal(TradeSignal signal, RealTimeMarket realTimeMarket) {
        return OnceOrders.createEntrustCommand(this, signal, realTimeMarket);
    }
}
