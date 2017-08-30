package me.caosh.condition.infrastructure.rabbitmq;

import me.caosh.condition.domain.dto.order.TriggerMessageDTO;
import me.caosh.condition.domain.dto.order.assembler.TriggerMessageAssembler;
import me.caosh.condition.domain.dto.order.converter.ConditionOrderGSONMessageConverter;
import me.caosh.condition.domain.model.order.TriggerContext;
import me.caosh.condition.domain.model.order.TriggerMessage;
import me.caosh.condition.domain.model.share.Retry;
import me.caosh.condition.domain.service.ConditionTradeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collections;

/**
 * Created by caosh on 2017/8/13.
 */
@Configuration
@ConfigurationProperties(prefix = "me.caosh.condition.triggerMessage")
@Component
public class TriggerMessageConsumer {
    private static final Logger logger = LoggerFactory.getLogger(TriggerMessageConsumer.class);

    private String exchangeName;
    private String queueName;
    private String routingKey;

    private final ConnectionFactory connectionFactory;
    private final AmqpAdmin amqpAdmin;
    private final ConditionTradeService conditionTradeService;
    // TODO: converter to domain model
    private final MessageConverter messageConverter = new ConditionOrderGSONMessageConverter<>(TriggerMessageDTO.class);

    public void setExchangeName(String exchangeName) {
        this.exchangeName = exchangeName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public void setRoutingKey(String routingKey) {
        this.routingKey = routingKey;
    }

    public TriggerMessageConsumer(ConnectionFactory connectionFactory, AmqpAdmin amqpAdmin, ConditionTradeService conditionTradeService) {
        this.connectionFactory = connectionFactory;
        this.amqpAdmin = amqpAdmin;
        this.conditionTradeService = conditionTradeService;
    }

    @PostConstruct
    public void init() {
        Queue queue = new Queue(queueName, false, false, false);
        Binding binding = new Binding(queue.getName(), Binding.DestinationType.QUEUE, exchangeName, routingKey, Collections.emptyMap());

        amqpAdmin.declareQueue(queue);
        amqpAdmin.declareBinding(binding);
        logger.info("=== Trigger message consumer initialized ===");

        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        container.addQueues(queue);
        container.setMessageListener((MessageListener) this::handleTriggerMessage);
        container.start();
    }

    private void handleTriggerMessage(Message message) {
        try {
            Retry.times(3).onException(RuntimeException.class).execute(new Retry.BaseRetryAction<Void>() {
                @Override
                public Void onTry() throws Exception {
                    tryHandleTriggerMessage(message);
                    return null;
                }

                @Override
                public void onFailedOnce(Exception e, int retriedTimes) {
                    logger.warn("Try handle trigger message failed, retriedTimes=" + retriedTimes, e);
                }
            });
        } catch (Exception e) {
            throw new AmqpRejectAndDontRequeueException(e);
        }
    }

    private void tryHandleTriggerMessage(Message message) {
        TriggerMessageDTO triggerMessageDTO = (TriggerMessageDTO) messageConverter.fromMessage(message);
        logger.debug("Receive trigger message <== {}", triggerMessageDTO);

        TriggerMessage triggerMessage = TriggerMessageAssembler.fromDTO(triggerMessageDTO);
        TriggerContext triggerContext = new TriggerContext(triggerMessage.getTradeSignal(), triggerMessage.getConditionOrder(),
                triggerMessage.getRealTimeMarket().orElse(null));
        conditionTradeService.handleTriggerContext(triggerContext);
    }
}
