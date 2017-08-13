package me.caosh.condition.application.order;

import me.caosh.condition.domain.model.market.RealTimeMarket;
import me.caosh.condition.domain.model.order.ConditionOrder;
import me.caosh.condition.domain.model.signal.TradeSignal;

/**
 * Created by caosh on 2017/8/13.
 */
public interface ConditionOrderTradeCenter {
    void handleTriggerMessage(TradeSignal signal, ConditionOrder conditionOrder, RealTimeMarket realTimeMarket);
}
