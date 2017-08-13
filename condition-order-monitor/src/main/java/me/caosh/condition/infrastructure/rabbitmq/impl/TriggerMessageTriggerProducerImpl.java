package me.caosh.condition.infrastructure.rabbitmq.impl;

import me.caosh.condition.domain.dto.order.TriggerMessageDTO;
import me.caosh.condition.domain.dto.order.converter.TriggerMessageDTOMessageConverter;
import me.caosh.condition.infrastructure.rabbitmq.TriggerMessageTriggerProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Created by caosh on 2017/8/11.
 */
@Configuration
@ConfigurationProperties(prefix = "me.caosh.condition.triggerMessage")
@Service
public class TriggerMessageTriggerProducerImpl implements TriggerMessageTriggerProducer {
    private static final Logger logger = LoggerFactory.getLogger(TriggerMessageTriggerProducerImpl.class);

    private String exchangeName;
    private String routingKey;

    private final AmqpAdmin amqpAdmin;
    private final AmqpTemplate amqpTemplate;

    private final MessageConverter messageConverter = new TriggerMessageDTOMessageConverter();

    public TriggerMessageTriggerProducerImpl(AmqpAdmin amqpAdmin, AmqpTemplate amqpTemplate) {
        this.amqpAdmin = amqpAdmin;
        this.amqpTemplate = amqpTemplate;
    }

    public void setExchangeName(String exchangeName) {
        this.exchangeName = exchangeName;
    }

    public void setRoutingKey(String routingKey) {
        this.routingKey = routingKey;
    }

    @PostConstruct
    public void init() {
        DirectExchange exchange = new DirectExchange(exchangeName, false, false);
        amqpAdmin.declareExchange(exchange);

        logger.info("=== Trigger message producer initialized ===");
    }

    @Override
    public void send(TriggerMessageDTO triggerMessageDTO) {
        Message message = messageConverter.toMessage(triggerMessageDTO, new MessageProperties());
        amqpTemplate.send(exchangeName, routingKey, message);
        logger.info("Send trigger message ==> {}", triggerMessageDTO);
    }
}
