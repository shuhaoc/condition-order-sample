package me.caosh.condition.infrastructure.rabbitmq;

import me.caosh.condition.domain.dto.order.TriggerMessageDTO;

/**
 * Created by caosh on 2017/8/11.
 */
public interface ConditionOrderTriggerProducer {
    void send(TriggerMessageDTO triggerMessageDTO);
}
