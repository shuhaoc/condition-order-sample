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
public class IndexMarketConsumer {
    private static final Logger logger = LoggerFactory.getLogger(IndexMarketConsumer.class);

    private String indexExchange;
    private String indexQueue;

    private final ConnectionFactory connectionFactory;
    private final AmqpAdmin amqpAdmin;
    private final MessageConverter messageConverter = new RealTimeMarketMessageConverter();

    public void setIndexExchange(String indexExchange) {
        this.indexExchange = indexExchange;
    }

    public void setIndexQueue(String indexQueue) {
        this.indexQueue = indexQueue;
    }

    @Autowired
    public IndexMarketConsumer(ConnectionFactory connectionFactory, AmqpAdmin amqpAdmin) {
        this.connectionFactory = connectionFactory;
        this.amqpAdmin = amqpAdmin;
    }

    @PostConstruct
    public void init() throws Exception {
        Queue indexMarketQueue = new Queue(indexQueue, false, false, true);
        amqpAdmin.declareQueue(indexMarketQueue);
        amqpAdmin.declareBinding(new Binding(indexMarketQueue.getName(),
                                             Binding.DestinationType.QUEUE,
                                             indexExchange,
                                             "",
                                             Collections.<String, Object>emptyMap()));
        logger.info("=== Index market consumer initialized ===");

        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        container.addQueues(indexMarketQueue);
        container.setMessageListener(new MessageListener() {
            @Override
            @SuppressWarnings("unchecked")
            public void onMessage(Message message) {
                HashMap<String, RealTimeMarketSimpleDTO> marketMap = (HashMap<String, RealTimeMarketSimpleDTO>) messageConverter
                        .fromMessage(message);
                logger.debug("Receive index market message <== size={}", marketMap.size());
                Map<String, RealTimeMarket> realTimeMarketMap = RealTimeMarketSimpleDTOAssembler.transformMap(
                        MarketType.INDEX,
                        marketMap);
                MonitorEventBus.EVENT_SERIALIZED.post(new RealTimeMarketPushEvent(realTimeMarketMap));
            }
        });
        container.start();
    }
}
