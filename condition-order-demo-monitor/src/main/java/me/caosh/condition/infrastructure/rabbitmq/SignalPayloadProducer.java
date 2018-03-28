package me.caosh.condition.infrastructure.rabbitmq;

import hbec.intellitrade.strategy.domain.signalpayload.SignalPayload;

/**
 * Created by caosh on 2017/8/11.
 */
public interface SignalPayloadProducer {
    void send(SignalPayload signalPayload);
}
