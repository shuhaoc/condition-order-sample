package me.caosh.condition.infrastructure.rabbitmq;

import me.caosh.condition.application.order.ConditionOrderTradeCenter;
import me.caosh.condition.domain.dto.order.TriggerMessageDTO;
import me.caosh.condition.domain.dto.order.assembler.ConditionOrderDTOAssembler;
import me.caosh.condition.domain.dto.order.converter.TriggerMessageDTOMessageConverter;
import me.caosh.condition.domain.model.order.ConditionOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.core.Queue;
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
    private final ConditionOrderTradeCenter conditionOrderTradeCenter;
    private final MessageConverter messageConverter = new TriggerMessageDTOMessageConverter();

    public void setExchangeName(String exchangeName) {
        this.exchangeName = exchangeName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public void setRoutingKey(String routingKey) {
        this.routingKey = routingKey;
    }

    public TriggerMessageConsumer(ConnectionFactory connectionFactory, AmqpAdmin amqpAdmin, ConditionOrderTradeCenter conditionOrderTradeCenter) {
        this.connectionFactory = connectionFactory;
        this.amqpAdmin = amqpAdmin;
        this.conditionOrderTradeCenter = conditionOrderTradeCenter;
    }

    @PostConstruct
    public void init() {
        // TODO: 样板代码
        Queue queue = new Queue(queueName, false, false, false);
        Binding binding = new Binding(queue.getName(), Binding.DestinationType.QUEUE, exchangeName, routingKey, Collections.emptyMap());

        amqpAdmin.declareQueue(queue);
        amqpAdmin.declareBinding(binding);
        logger.info("=== Trigger message consumer initialized ===");

        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        container.addQueues(queue);
        container.setMessageListener((MessageListener) message -> {
            TriggerMessageDTO triggerMessageDTO = (TriggerMessageDTO) messageConverter.fromMessage(message);
            logger.debug("Receive trigger message <== {}", triggerMessageDTO);
            ConditionOrder conditionOrder = ConditionOrderDTOAssembler.fromDTO(triggerMessageDTO.getConditionOrderDTO());
            conditionOrderTradeCenter.handleTriggerMessage(triggerMessageDTO.getTradeSignal(),
                    conditionOrder,
                    triggerMessageDTO.getRealTimeMarket());
        });
        container.start();
    }
}
