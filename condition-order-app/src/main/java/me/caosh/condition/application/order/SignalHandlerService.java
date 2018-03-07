package me.caosh.condition.application.order;

import hbec.intellitrade.common.market.RealTimeMarket;
import hbec.intellitrade.condorder.domain.ConditionOrder;
import hbec.intellitrade.strategy.domain.signal.Signal;

/**
 * Created by caosh on 2017/8/13.
 *
 * @implNote 仅作示例，没有并发控制不可用于生产
 */
public interface SignalHandlerService {
    void handleTriggerContext(Signal signal, ConditionOrder conditionOrder, RealTimeMarket realTimeMarket);
}
