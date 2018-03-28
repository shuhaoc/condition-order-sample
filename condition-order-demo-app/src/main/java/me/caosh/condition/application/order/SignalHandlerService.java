package me.caosh.condition.application.order;

import hbec.intellitrade.strategy.domain.signalpayload.SignalPayload;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2017/8/13
 */
public interface SignalHandlerService {
    void handleSignalPayload(SignalPayload signalPayload);
}
