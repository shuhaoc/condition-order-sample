package me.caosh.condition.domain.model.trade;

import me.caosh.condition.domain.model.market.RealTimeMarket;
import me.caosh.condition.domain.model.signal.TradeSignal;

/**
 * Created by caosh on 2017/8/29.
 */
public interface SingleEntrustOnTrigger {
    EntrustCommand onTradeSignal(TradeSignal signal, RealTimeMarket realTimeMarket);
}
