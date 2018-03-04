package me.caosh.condition.domain.model.order;

import me.caosh.condition.domain.model.market.MarketID;
import me.caosh.condition.domain.model.market.SecurityInfo;
import me.caosh.condition.domain.model.order.constant.StrategyState;
import me.caosh.condition.domain.model.strategy.MarketDrivenStrategy;

/**
 * Created by caosh on 2017/8/20.
 */
public abstract class AbstractMarketConditionOrder extends AbstractGeneralConditionOrder implements MarketDrivenStrategy {
    public AbstractMarketConditionOrder(Long orderId, TradeCustomerInfo tradeCustomerInfo, SecurityInfo securityInfo,
                                        StrategyState strategyState) {
        super(orderId, tradeCustomerInfo, securityInfo, strategyState);
    }

    @Override
    public MarketID getTrackMarketID() {
        return getSecurityInfo().getMarketID();
    }
}
