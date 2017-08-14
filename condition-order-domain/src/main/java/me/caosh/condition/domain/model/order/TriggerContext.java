package me.caosh.condition.domain.model.order;

import me.caosh.condition.domain.model.market.RealTimeMarket;
import me.caosh.condition.domain.model.signal.TradeSignal;

/**
 * Created by caosh on 2017/8/14.
 *
 * @author caoshuhao@touker.com
 */
public class TriggerContext {
    private final TradeSignal tradeSignal;
    private final ConditionOrder conditionOrder;
    private final RealTimeMarket triggerRealTimeMarket;

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

    public RealTimeMarket getTriggerRealTimeMarket() {
        return triggerRealTimeMarket;
    }
}
