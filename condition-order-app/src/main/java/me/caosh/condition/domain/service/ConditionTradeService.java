package me.caosh.condition.domain.service;

import me.caosh.condition.domain.model.market.RealTimeMarket;
import me.caosh.condition.domain.model.order.ConditionOrder;
import me.caosh.condition.domain.model.signal.Signal;

/**
 * Created by caosh on 2017/8/13.
 *
 * @implNote 仅作示例，没有并发控制不可用于生产
 */
public interface ConditionTradeService {
    void handleTriggerContext(Signal signal, ConditionOrder conditionOrder, RealTimeMarket realTimeMarket);
}
