package me.caosh.condition.infrastructure.rabbitmq;

import hbec.intellitrade.strategy.domain.signalpayload.SignalPayload;
import me.caosh.condition.domain.model.order.TriggerMessage;

/**
 * Created by caosh on 2017/8/11.
 */
public interface SignalPayloadProducer {
    void send(TriggerMessage triggerMessage);
    void send(SignalPayload signalPayload);
}
