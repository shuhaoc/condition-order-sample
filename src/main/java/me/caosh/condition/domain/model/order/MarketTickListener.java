package me.caosh.condition.domain.model.order;

import me.caosh.condition.domain.model.market.RealTimeMarket;
import me.caosh.condition.domain.model.signal.TradeSignal;

/**
 * Created by caosh on 2017/8/7.
 */
public interface MarketTickListener {
    TradeSignal onMarketTick(RealTimeMarket realTimeMarket);

//    boolean onClockTick();
}
