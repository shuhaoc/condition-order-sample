package me.caosh.condition.infrastructure.rabbitmq;

import me.caosh.condition.infrastructure.rabbitmq.model.RealTimeMarketDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collections;

/**
 * Created by caosh on 2017/8/9.
 */
@Component
public class RealTimeMarketConsumer {
    private static final Logger logger = LoggerFactory.getLogger(RealTimeMarketConsumer.class);

    private static final String STOCK_EXCHANGE = "stock-exchange";
    private static final String COS_STOCK_QUEUE = "cos-stock-queue";
    private static final String ROUTING_KEY = "";

    @Autowired
    private RabbitAdmin rabbitAdmin;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void init() throws Exception {
        Queue queue = new Queue(COS_STOCK_QUEUE);
        Binding binding = new Binding(queue.getName(), Binding.DestinationType.QUEUE, STOCK_EXCHANGE, ROUTING_KEY, Collections.<String, Object>emptyMap());

        rabbitAdmin.declareQueue(queue);
        rabbitAdmin.declareBinding(binding);
        logger.info("=== Real time market consumer initialized ===");
    }

    @RabbitListener(queues = COS_STOCK_QUEUE)
    public void onMarketMessageComes(@Payload RealTimeMarketDTO realTimeMarketDTO) {
        logger.debug("Receive market message <== {}", realTimeMarketDTO);


    }
}
