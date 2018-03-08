package me.caosh.condition.application.order;

import hbec.intellitrade.common.market.RealTimeMarket;
import hbec.intellitrade.condorder.domain.ConditionOrder;
import hbec.intellitrade.strategy.domain.signal.Signal;
import hbec.intellitrade.strategy.domain.signalpayload.SignalPayload;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2017/8/13
 */
public interface SignalHandlerService {
    void handleSignalPaylaod(SignalPayload signalPayload, Signal signal, ConditionOrder conditionOrder, RealTimeMarket realTimeMarket);
}
