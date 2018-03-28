package me.caosh.condition.infrastructure.rabbitmq;

import hbec.intellitrade.common.market.MarketType;
import hbec.intellitrade.common.market.RealTimeMarket;
import me.caosh.condition.domain.event.RealTimeMarketPushEvent;
import me.caosh.condition.infrastructure.eventbus.MonitorEventBus;
import me.caosh.condition.infrastructure.rabbitmq.model.RealTimeMarketMessageConverter;
import me.caosh.condition.infrastructure.rabbitmq.model.RealTimeMarketSimpleDTO;
import me.caosh.condition.infrastructure.rabbitmq.model.RealTimeMarketSimpleDTOAssembler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by caosh on 2017/8/9.
 */
@ConfigurationProperties(prefix = "me.caosh.condition.realTimeMarket")
@Component
public class StockMarketConsumer {
    private static final Logger logger = LoggerFactory.getLogger(StockMarketConsumer.class);

    private String stockExchange;
    private String stockQueue;

    private final ConnectionFactory connectionFactory;
    private final AmqpAdmin amqpAdmin;
    private final MessageConverter messageConverter = new RealTimeMarketMessageConverter();

    public void setStockExchange(String stockExchange) {
        this.stockExchange = stockExchange;
    }

    public void setStockQueue(String stockQueue) {
        this.stockQueue = stockQueue;
    }

    @Autowired
    public StockMarketConsumer(ConnectionFactory connectionFactory, AmqpAdmin amqpAdmin) {
        this.connectionFactory = connectionFactory;
        this.amqpAdmin = amqpAdmin;
    }

    @PostConstruct
    public void init() throws Exception {
        Queue stockMarketQueue = new Queue(stockQueue, false, false, true);
        amqpAdmin.declareQueue(stockMarketQueue);
        amqpAdmin.declareBinding(new Binding(stockMarketQueue.getName(),
                                             Binding.DestinationType.QUEUE,
                                             stockExchange,
                                             "",
                                             Collections.<String, Object>emptyMap()));
        logger.info("=== Stock market consumer initialized ===");

        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        container.addQueues(stockMarketQueue);
        container.setMessageListener(new MessageListener() {
            @Override
            @SuppressWarnings("unchecked")
            public void onMessage(Message message) {
                HashMap<String, RealTimeMarketSimpleDTO> marketMap = (HashMap<String, RealTimeMarketSimpleDTO>) messageConverter
                        .fromMessage(message);
                logger.debug("Receive stock market message <== size={}", marketMap.size());
                Map<String, RealTimeMarket> realTimeMarketMap = RealTimeMarketSimpleDTOAssembler.transformMap(
                        MarketType.STOCK,
                        marketMap);
                MonitorEventBus.EVENT_SERIALIZED.post(new RealTimeMarketPushEvent(realTimeMarketMap));
            }
        });
        container.start();
    }
}
