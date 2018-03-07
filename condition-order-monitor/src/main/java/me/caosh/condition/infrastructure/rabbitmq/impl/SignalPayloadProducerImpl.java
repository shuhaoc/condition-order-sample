package me.caosh.condition.infrastructure.rabbitmq.impl;

import hbec.intellitrade.strategy.domain.signalpayload.SignalPayload;
import me.caosh.autoasm.AutoAssemblers;
import me.caosh.condition.domain.dto.order.SignalPayloadDTO;
import me.caosh.condition.domain.dto.order.converter.ConditionOrderGSONMessageConverter;
import me.caosh.condition.infrastructure.rabbitmq.SignalPayloadProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Created by caosh on 2017/8/11.
 */
@ConfigurationProperties(prefix = "me.caosh.condition.triggerMessage")
@Service
public class SignalPayloadProducerImpl implements SignalPayloadProducer {
    private static final Logger logger = LoggerFactory.getLogger(SignalPayloadProducerImpl.class);

    private String exchangeName;
    private String routingKey;

    private final AmqpAdmin amqpAdmin;
    private final AmqpTemplate amqpTemplate;

    private final MessageConverter messageConverter = new ConditionOrderGSONMessageConverter<>(SignalPayloadDTO.class);

    public SignalPayloadProducerImpl(AmqpAdmin amqpAdmin, AmqpTemplate amqpTemplate) {
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
    public void send(SignalPayload signalPayload) {
        SignalPayloadDTO signalPayloadDTO = AutoAssemblers.getDefault().assemble(signalPayload, SignalPayloadDTO.class);
        send(signalPayloadDTO);
    }

    private void send(SignalPayloadDTO signalPayloadDTO) {
        Message message = messageConverter.toMessage(signalPayloadDTO, new MessageProperties());
        amqpTemplate.send(exchangeName, routingKey, message);
        logger.info("Send signal payload ==> {}", signalPayloadDTO);
    }
}
