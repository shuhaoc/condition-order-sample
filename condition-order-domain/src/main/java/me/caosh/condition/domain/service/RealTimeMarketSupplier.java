package me.caosh.condition.domain.service;

import hbec.intellitrade.common.market.MarketID;
import hbec.intellitrade.common.market.RealTimeMarket;

/**
 * Created by caosh on 2017/8/2.
 */
public interface RealTimeMarketSupplier {
    RealTimeMarket getCurrentMarket(MarketID marketID);
}
