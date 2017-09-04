package me.caosh.condition.infrastructure.rabbitmq;

import me.caosh.condition.domain.model.order.TriggerMessage;

/**
 * Created by caosh on 2017/8/11.
 */
public interface TriggerMessageTriggerProducer {
    void send(TriggerMessage triggerMessage);
}
