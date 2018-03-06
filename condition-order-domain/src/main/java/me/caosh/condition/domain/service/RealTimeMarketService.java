package me.caosh.condition.domain.service;

import hbec.intellitrade.common.market.MarketID;
import hbec.intellitrade.common.market.RealTimeMarket;

/**
 * Created by caosh on 2017/8/2.
 */
public interface RealTimeMarketService {
    RealTimeMarket getCurrentMarket(MarketID marketID);
}
