package me.caosh.condition.domain.service;

import me.caosh.condition.domain.model.market.MarketID;
import me.caosh.condition.domain.model.market.RealTimeMarket;

/**
 * Created by caosh on 2017/8/2.
 */
public interface RealTimeMarketService {
    RealTimeMarket getCurrentMarket(MarketID marketID);
}
