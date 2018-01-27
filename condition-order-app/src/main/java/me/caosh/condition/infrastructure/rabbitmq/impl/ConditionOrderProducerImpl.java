package me.caosh.condition.infrastructure.rabbitmq.impl;

import com.google.common.base.Preconditions;
import me.caosh.condition.domain.dto.order.ConditionOrderDTO;
import me.caosh.condition.domain.dto.order.ConditionOrderMonitorDTO;
import me.caosh.condition.domain.dto.order.assembler.ConditionOrderDTOAssembler;
import me.caosh.condition.domain.dto.order.converter.ConditionOrderGSONMessageConverter;
import me.caosh.condition.domain.model.constants.OrderCommandType;
import me.caosh.condition.domain.model.order.ConditionOrder;
import me.caosh.condition.domain.model.order.constant.StrategyState;
import me.caosh.condition.infrastructure.rabbitmq.ConditionOrderProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Created by caosh on 2017/8/11.
 *
 * @author caoshuhao@touker.com
 */
@ConfigurationProperties(prefix = "me.caosh.condition.conditionOrder")
@Service
public class ConditionOrderProducerImpl implements ConditionOrderProducer {
    private static final Logger logger = LoggerFactory.getLogger(ConditionOrderProducerImpl.class);

    private String exchangeName;
    private String routingKey;

    private final AmqpAdmin amqpAdmin;

    private final AmqpTemplate amqpTemplate;

    private final MessageConverter messageConverter = new ConditionOrderGSONMessageConverter<>(ConditionOrderMonitorDTO.class);

    public ConditionOrderProducerImpl(AmqpAdmin amqpAdmin, AmqpTemplate amqpTemplate) {
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

        logger.info("=== Condition order producer initialized ===");
    }

    @Override
    public void save(ConditionOrder conditionOrder) {
        Preconditions.checkArgument(conditionOrder.getStrategyState() == StrategyState.ACTIVE);
        send(exchangeName, routingKey, OrderCommandType.CREATE, conditionOrder);
    }

    @Override
    public void update(ConditionOrder conditionOrder) {
        Preconditions.checkArgument(conditionOrder.getStrategyState() == StrategyState.ACTIVE);
        send(exchangeName, routingKey, OrderCommandType.UPDATE, conditionOrder);
    }

    private void send(String exchangeName, String routingKey, OrderCommandType update, ConditionOrder conditionOrder) {
        ConditionOrderDTO conditionOrderDTO = ConditionOrderDTOAssembler.toDTO(conditionOrder);
        ConditionOrderMonitorDTO conditionOrderMonitorDTO = new ConditionOrderMonitorDTO(update.getValue(), conditionOrderDTO);
        Message message = messageConverter.toMessage(conditionOrderMonitorDTO, new MessageProperties());
        amqpTemplate.send(exchangeName, routingKey, message);
        logger.info("Send condition order ==> {}", conditionOrderDTO);
    }

    @Override
    public void remove(Long orderId) {
        ConditionOrderMonitorDTO conditionOrderMonitorDTO = new ConditionOrderMonitorDTO(OrderCommandType.DELETE.getValue(), orderId);
        Message message = messageConverter.toMessage(conditionOrderMonitorDTO, new MessageProperties());
        amqpTemplate.send(exchangeName, routingKey, message);
        logger.info("Send <DELETE> command ==> {}", orderId);
    }
}
