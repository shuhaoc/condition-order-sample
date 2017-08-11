package me.caosh.condition.infrastructure.rabbitmq;

import me.caosh.condition.infrastructure.rabbitmq.model.RealTimeMarketDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.HashMap;

/**
 * Created by caosh on 2017/8/9.
 */
@Component
public class RealTimeMarketConsumer {
    private static final Logger logger = LoggerFactory.getLogger(RealTimeMarketConsumer.class);

    private static final String STOCK_EXCHANGE = "stock_exchange";
    private static final String COS_STOCK_QUEUE = "cos_stock_queue";
    private static final String ROUTING_KEY = "";

    private final AmqpAdmin amqpAdmin;

    private final AmqpTemplate rabbitTemplate;

    private final MessageConverter messageConverter;

    @Autowired
    public RealTimeMarketConsumer(AmqpAdmin amqpAdmin, AmqpTemplate amqpTemplate, MessageConverter messageConverter) {
        this.amqpAdmin = amqpAdmin;
        this.rabbitTemplate = amqpTemplate;
        this.messageConverter = messageConverter;
    }

    @PostConstruct
    public void init() throws Exception {
        Queue queue = new Queue(COS_STOCK_QUEUE);
        Binding binding = new Binding(queue.getName(), Binding.DestinationType.QUEUE, STOCK_EXCHANGE, ROUTING_KEY, Collections.<String, Object>emptyMap());

        amqpAdmin.declareQueue(queue);
        amqpAdmin.declareBinding(binding);
        logger.info("=== Real time market consumer initialized ===");
    }

    @RabbitListener(queues = COS_STOCK_QUEUE)
    public void onMarketMessageComes(Message message) {
        HashMap<String, RealTimeMarketDTO> marketMap = (HashMap<String, RealTimeMarketDTO>) messageConverter.fromMessage(message);
        logger.debug("Receive market message <== {}", marketMap);
    }
}
