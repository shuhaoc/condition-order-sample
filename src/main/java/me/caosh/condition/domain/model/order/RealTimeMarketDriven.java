package me.caosh.condition.domain.model.order;

import me.caosh.condition.domain.model.market.RealTimeMarket;
import me.caosh.condition.domain.model.signal.TradeSignal;

/**
 * Created by caosh on 2017/8/7.
 */
public interface RealTimeMarketDriven {
    TradeSignal onRealTimeMarketUpdate(RealTimeMarket realTimeMarket);

//    boolean onClockTick();
}
