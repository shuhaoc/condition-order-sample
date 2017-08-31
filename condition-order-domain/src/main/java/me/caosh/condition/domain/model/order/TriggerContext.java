package me.caosh.condition.domain.model.order;

import com.google.common.base.MoreObjects;
import me.caosh.condition.domain.model.market.RealTimeMarket;
import me.caosh.condition.domain.model.signal.TradeSignal;

import java.util.Optional;

/**
 * Created by caosh on 2017/8/14.
 *
 * @author caoshuhao@touker.com
 */
public class TriggerContext {
    private final TradeSignal tradeSignal;
    private final ConditionOrder conditionOrder;
    private RealTimeMarket triggerRealTimeMarket;

    public TriggerContext(TradeSignal tradeSignal, ConditionOrder conditionOrder, RealTimeMarket triggerRealTimeMarket) {
        this.tradeSignal = tradeSignal;
        this.conditionOrder = conditionOrder;
        this.triggerRealTimeMarket = triggerRealTimeMarket;
    }

    public TradeSignal getTradeSignal() {
        return tradeSignal;
    }

    public ConditionOrder getConditionOrder() {
        return conditionOrder;
    }

    public Optional<RealTimeMarket> getTriggerRealTimeMarket() {
        return Optional.ofNullable(triggerRealTimeMarket);
    }

    public void setTriggerRealTimeMarket(RealTimeMarket triggerRealTimeMarket) {
        this.triggerRealTimeMarket = triggerRealTimeMarket;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("tradeSignal", tradeSignal)
                .add("conditionOrder", conditionOrder)
                .add("triggerRealTimeMarket", triggerRealTimeMarket)
                .toString();
    }
}
