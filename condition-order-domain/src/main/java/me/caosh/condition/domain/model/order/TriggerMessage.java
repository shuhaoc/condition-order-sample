package me.caosh.condition.domain.model.order;

import com.google.common.base.MoreObjects;
import me.caosh.condition.domain.model.market.RealTimeMarket;
import me.caosh.condition.domain.model.signal.TradeSignal;

import java.util.Optional;

/**
 * Created by caosh on 2017/8/30.
 */
public class TriggerMessage {
    private final TradeSignal tradeSignal;
    private final ConditionOrder conditionOrder;
    private final RealTimeMarket realTimeMarket;

    public TriggerMessage(TradeSignal tradeSignal, ConditionOrder conditionOrder, RealTimeMarket realTimeMarket) {
        this.tradeSignal = tradeSignal;
        this.conditionOrder = conditionOrder;
        this.realTimeMarket = realTimeMarket;
    }

    public TriggerMessage(TradeSignal tradeSignal, ConditionOrder conditionOrder) {
        this.tradeSignal = tradeSignal;
        this.conditionOrder = conditionOrder;
        this.realTimeMarket = null;
    }

    public TradeSignal getTradeSignal() {
        return tradeSignal;
    }

    public ConditionOrder getConditionOrder() {
        return conditionOrder;
    }

    public Optional<RealTimeMarket> getRealTimeMarket() {
        return Optional.ofNullable(realTimeMarket);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("tradeSignal", tradeSignal)
                .add("conditionOrder", conditionOrder)
                .add("realTimeMarket", realTimeMarket)
                .toString();
    }
}
