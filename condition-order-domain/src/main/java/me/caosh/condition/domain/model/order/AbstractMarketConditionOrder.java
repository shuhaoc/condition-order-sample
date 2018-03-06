package me.caosh.condition.domain.model.order;

import hbec.intellitrade.common.market.MarketID;
import hbec.intellitrade.common.security.SecurityInfo;
import hbec.intellitrade.strategy.domain.MarketDrivenStrategy;
import me.caosh.condition.domain.model.order.constant.StrategyState;

/**
 * Created by caosh on 2017/8/20.
 */
public abstract class AbstractMarketConditionOrder extends AbstractConditionOrder implements MarketDrivenStrategy {
    public AbstractMarketConditionOrder(Long orderId, TradeCustomerInfo tradeCustomerInfo, SecurityInfo securityInfo,
                                        StrategyState strategyState) {
        super(orderId, tradeCustomerInfo, securityInfo, strategyState);
    }

    @Override
    public MarketID getTrackMarketID() {
        return getSecurityInfo().getMarketID();
    }
}
