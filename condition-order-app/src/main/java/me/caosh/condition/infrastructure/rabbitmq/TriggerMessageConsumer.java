package me.caosh.condition.infrastructure.rabbitmq;

import me.caosh.condition.application.order.ConditionOrderTradeCenter;
import me.caosh.condition.domain.dto.market.assembler.RealTimeMarketDTOAssembler;
import me.caosh.condition.domain.dto.order.TriggerMessageDTO;
import me.caosh.condition.domain.dto.order.assembler.ConditionOrderDTOAssembler;
import me.caosh.condition.domain.dto.order.assembler.TradeSignalBuilder;
import me.caosh.condition.domain.dto.order.converter.ConditionOrderGSONMessageConverter;
import me.caosh.condition.domain.model.market.RealTimeMarket;
import me.caosh.condition.domain.model.order.ConditionOrder;
import me.caosh.condition.domain.model.order.TriggerContext;
import me.caosh.condition.domain.model.share.Retry;
import me.caosh.condition.domain.model.signal.TradeSignal;
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
    private final ConditionOrderTradeCenter conditionOrderTradeCenter;
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

    public TriggerMessageConsumer(ConnectionFactory connectionFactory, AmqpAdmin amqpAdmin, ConditionOrderTradeCenter conditionOrderTradeCenter) {
        this.connectionFactory = connectionFactory;
        this.amqpAdmin = amqpAdmin;
        this.conditionOrderTradeCenter = conditionOrderTradeCenter;
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
        TradeSignal tradeSignal = new TradeSignalBuilder(triggerMessageDTO.getTradeSignalDTO()).build();
        ConditionOrder conditionOrder = ConditionOrderDTOAssembler.fromDTO(triggerMessageDTO.getConditionOrderDTO());
        RealTimeMarket realTimeMarket = null;
        if (triggerMessageDTO.getRealTimeMarketDTO() != null) {
            realTimeMarket = RealTimeMarketDTOAssembler.fromDTO(triggerMessageDTO.getRealTimeMarketDTO());
        }
        TriggerContext triggerContext = new TriggerContext(tradeSignal, conditionOrder, realTimeMarket);
        conditionOrderTradeCenter.handleTriggerContext(triggerContext);
    }
}
