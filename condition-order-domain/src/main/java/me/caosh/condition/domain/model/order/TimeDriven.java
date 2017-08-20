package me.caosh.condition.domain.model.order;

import me.caosh.condition.domain.model.signal.TradeSignal;

/**
 * Created by caosh on 2017/8/20.
 */
public interface TimeDriven {
    TradeSignal onSecondTick();
}
