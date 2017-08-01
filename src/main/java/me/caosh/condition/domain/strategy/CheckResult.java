package me.caosh.condition.domain.strategy;

import com.google.common.base.MoreObjects;
import me.caosh.condition.domain.signal.TradeSignal;

/**
 * Created by caosh on 2017/8/1.
 */
public class CheckResult {
    private final TradeSignal tradeSignal;

    public CheckResult(TradeSignal tradeSignal) {
        this.tradeSignal = tradeSignal;
    }

    public TradeSignal getTradeSignal() {
        return tradeSignal;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("tradeSignal", tradeSignal)
                .toString();
    }
}
