package me.caosh.condition.infrastructure.rabbitmq;

import hbec.intellitrade.condorder.domain.ConditionOrder;
import hbec.intellitrade.strategy.domain.signalpayload.MarketSignalPayload;
import hbec.intellitrade.strategy.domain.signalpayload.SignalPayload;
import me.caosh.condition.domain.model.strategy.signalpayload.SignalPayloadBuilder;
import me.caosh.autoasm.AutoAssemblers;
import me.caosh.condition.application.order.SignalHandlerService;
import me.caosh.condition.domain.dto.order.SignalPayloadDTO;
import me.caosh.condition.domain.dto.order.converter.ConditionOrderGSONMessageConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collections;

/**
 * Created by caosh on 2017/8/13.
 */
@ConfigurationProperties(prefix = "me.caosh.condition.triggerMessage")
@Component
public class SignalPayloadConsumer {
    private static final Logger logger = LoggerFactory.getLogger(SignalPayloadConsumer.class);

    private String exchangeName;
    private String queueName;
    private String routingKey;

    private final ConnectionFactory connectionFactory;
    private final AmqpAdmin amqpAdmin;
    private final SignalHandlerService signalHandlerService;
    // TODO: converter to domain model
    private final MessageConverter messageConverter = new ConditionOrderGSONMessageConverter<>(SignalPayloadDTO.class);

    public void setExchangeName(String exchangeName) {
        this.exchangeName = exchangeName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public void setRoutingKey(String routingKey) {
        this.routingKey = routingKey;
    }

    public SignalPayloadConsumer(ConnectionFactory connectionFactory, AmqpAdmin amqpAdmin, SignalHandlerService signalHandlerService) {
        this.connectionFactory = connectionFactory;
        this.amqpAdmin = amqpAdmin;
        this.signalHandlerService = signalHandlerService;
    }

    @PostConstruct
    public void init() {
        Queue queue = new Queue(queueName, false, false, false);
        Binding binding = new Binding(queue.getName(), Binding.DestinationType.QUEUE, exchangeName, routingKey,
                Collections.<String, Object>emptyMap());

        amqpAdmin.declareQueue(queue);
        amqpAdmin.declareBinding(binding);
        logger.info("=== Trigger message consumer initialized ===");

        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        container.addQueues(queue);
        container.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                handleTriggerMessage(message);
            }
        });
        container.start();
    }

    private void handleTriggerMessage(final Message message) {
        SignalPayloadDTO signalPayloadDTO = (SignalPayloadDTO) messageConverter.fromMessage(message);
        logger.debug("Receive trigger message <== {}", signalPayloadDTO);

        SignalPayload signalPayload = AutoAssemblers.getDefault().disassemble(signalPayloadDTO, SignalPayloadBuilder.class).build();
        signalHandlerService.handleSignalPaylaod(signalPayload, signalPayload.getSignal(), (ConditionOrder) signalPayload.getStrategy(),
                ((MarketSignalPayload) signalPayload).getRealTimeMarket());
    }

}
