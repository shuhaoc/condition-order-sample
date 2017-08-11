package me.caosh.condition.infrastructure.rabbitmq.impl;

import me.caosh.condition.domain.model.order.ConditionOrder;
import me.caosh.condition.infrastructure.rabbitmq.ConditionOrderProducer;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;

/**
 * Created by caosh on 2017/8/11.
 *
 * @author caoshuhao@touker.com
 */
public class ConditionOrderProducerImpl implements ConditionOrderProducer {

    private final AmqpAdmin amqpAdmin;

    private final AmqpTemplate rabbitTemplate;

    public ConditionOrderProducerImpl(AmqpAdmin amqpAdmin, AmqpTemplate rabbitTemplate) {
        this.amqpAdmin = amqpAdmin;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void send(ConditionOrder conditionOrder) {

    }
}
