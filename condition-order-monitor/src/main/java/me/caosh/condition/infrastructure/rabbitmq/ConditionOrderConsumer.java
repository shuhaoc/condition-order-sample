package me.caosh.condition.infrastructure.rabbitmq;

import hbec.intellitrade.common.ValuedEnumUtil;
import hbec.intellitrade.condorder.domain.ConditionOrder;
import hbec.intellitrade.condorder.domain.ConditionOrderBuilder;
import me.caosh.autoasm.AutoAssemblers;
import me.caosh.condition.domain.dto.order.ConditionOrderMonitorDTO;
import me.caosh.condition.domain.dto.order.converter.ConditionOrderGSONMessageConverter;
import me.caosh.condition.domain.event.OrderUpdateCommandEvent;
import me.caosh.condition.domain.model.constants.OrderCommandType;
import me.caosh.condition.domain.event.OrderCommandEvent;
import me.caosh.condition.domain.event.OrderDeleteCommandEvent;
import me.caosh.condition.infrastructure.eventbus.MonitorEventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collections;

/**
 * Created by caosh on 2017/8/9.
 */
@ConfigurationProperties(prefix = "me.caosh.condition.conditionOrder")
@Component
public class ConditionOrderConsumer {
    private static final Logger logger = LoggerFactory.getLogger(ConditionOrderConsumer.class);

    private String exchangeName;
    private String queueName;
    private String routingKey;

    private final ConnectionFactory connectionFactory;
    private final AmqpAdmin amqpAdmin;
    private final MessageConverter messageConverter = new ConditionOrderGSONMessageConverter<>(ConditionOrderMonitorDTO.class);

    public void setExchangeName(String exchangeName) {
        this.exchangeName = exchangeName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public void setRoutingKey(String routingKey) {
        this.routingKey = routingKey;
    }

    public ConditionOrderConsumer(ConnectionFactory connectionFactory, AmqpAdmin amqpAdmin) {
        this.connectionFactory = connectionFactory;
        this.amqpAdmin = amqpAdmin;
    }

    @PostConstruct
    public void init() throws Exception {
        Queue queue = new Queue(queueName, false, false, false);
        Binding binding = new Binding(queue.getName(), Binding.DestinationType.QUEUE, exchangeName, routingKey,
                Collections.<String, Object>emptyMap());

        amqpAdmin.declareQueue(queue);
        amqpAdmin.declareBinding(binding);
        logger.info("=== Condition order consumer initialized ===");

        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        container.addQueues(queue);
        container.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                ConditionOrderMonitorDTO conditionOrderMonitorDTO = (ConditionOrderMonitorDTO) messageConverter.fromMessage(message);
                logger.debug("Receive condition order message <== {}", conditionOrderMonitorDTO);
                OrderCommandEvent event = create(conditionOrderMonitorDTO);
                MonitorEventBus.EVENT_SERIALIZED.post(event);
            }
        });
        container.start();
    }

    private OrderCommandEvent create(ConditionOrderMonitorDTO dto) {
        OrderCommandType orderCommandType = ValuedEnumUtil.valueOf(dto.getOrderCommandType(), OrderCommandType.class);
        switch (orderCommandType) {
            case UPDATE:
                ConditionOrder conditionOrder = AutoAssemblers.getDefault().disassemble(dto.getConditionOrderDTO(),
                        ConditionOrderBuilder.class).build();
                return new OrderUpdateCommandEvent(conditionOrder);
            case DELETE:
                return new OrderDeleteCommandEvent(dto.getOrderId());
            default:
                throw new IllegalArgumentException("orderCommandType" + dto.getOrderCommandType());
        }
    }
}
