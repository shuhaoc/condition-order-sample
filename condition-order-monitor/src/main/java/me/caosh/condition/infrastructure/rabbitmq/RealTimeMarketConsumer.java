package me.caosh.condition.infrastructure.rabbitmq;

import me.caosh.condition.domain.model.market.RealTimeMarket;
import me.caosh.condition.domain.model.market.RealTimeMarketPushEvent;
import me.caosh.condition.domain.model.market.SecurityType;
import me.caosh.condition.domain.util.EventBuses;
import me.caosh.condition.infrastructure.rabbitmq.model.RealTimeMarketSimpleDTO;
import me.caosh.condition.infrastructure.rabbitmq.model.RealTimeMarketSimpleDTOAssembler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by caosh on 2017/8/9.
 */
@Configuration
@ConfigurationProperties(prefix = "me.caosh.condition.realTimeMarket")
@Component
public class RealTimeMarketConsumer {
    private static final Logger logger = LoggerFactory.getLogger(RealTimeMarketConsumer.class);

    private String stockExchange;
    private String stockQueue;
    private String stockRoutingKey;

    private final ConnectionFactory connectionFactory;
    private final AmqpAdmin amqpAdmin;
    private final MessageConverter messageConverter;

    public void setStockExchange(String stockExchange) {
        this.stockExchange = stockExchange;
    }

    public void setStockQueue(String stockQueue) {
        this.stockQueue = stockQueue;
    }

    public void setStockRoutingKey(String stockRoutingKey) {
        this.stockRoutingKey = stockRoutingKey;
    }

    @Autowired
    public RealTimeMarketConsumer(ConnectionFactory connectionFactory, AmqpAdmin amqpAdmin, MessageConverter messageConverter) {
        this.connectionFactory = connectionFactory;
        this.amqpAdmin = amqpAdmin;
        this.messageConverter = messageConverter;
    }

    @PostConstruct
    public void init() throws Exception {
        Queue queue = new Queue(stockQueue, false, false, true);
        Binding binding = new Binding(queue.getName(), Binding.DestinationType.QUEUE, stockExchange, stockRoutingKey, Collections.<String, Object>emptyMap());

        amqpAdmin.declareQueue(queue);
        amqpAdmin.declareBinding(binding);
        logger.info("=== Real time market consumer initialized ===");

        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        container.addQueues(queue);
        container.setMessageListener((MessageListener) message -> {
            HashMap<String, RealTimeMarketSimpleDTO> marketMap = (HashMap<String, RealTimeMarketSimpleDTO>) messageConverter.fromMessage(message);
            logger.debug("Receive market message <== {}", marketMap);
            Map<String, RealTimeMarket> realTimeMarketMap = RealTimeMarketSimpleDTOAssembler.transformMap(SecurityType.STOCK, marketMap);
            EventBuses.DEFAULT.post(new RealTimeMarketPushEvent(realTimeMarketMap));
        });
        container.start();
    }
}
