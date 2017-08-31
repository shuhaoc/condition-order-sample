package me.caosh.condition.domain.model.trade;

import me.caosh.condition.domain.model.market.RealTimeMarket;
import me.caosh.condition.domain.model.signal.TradeSignal;

import java.util.List;

/**
 * Created by caosh on 2017/8/29.
 */
public interface MultipleEntrustOnTrigger {
    List<EntrustCommand> createEntrustCommands(TradeSignal signal, RealTimeMarket realTimeMarket);
}
