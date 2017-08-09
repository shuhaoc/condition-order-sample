package me.caosh.condition.infrastructure.rabbitmq;

import me.caosh.condition.infrastructure.Order;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

/**
 * Created by caosh on 2017/7/23.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class RabbitMQTest {
    private static final Logger logger = LoggerFactory.getLogger(RabbitMQTest.class);

    private static final String CONDITION_ORDER_SAMPLE_EXCHANGE = "condition-order-sample-exchange";
    private static final String CONDITION_ORDER_SAMPLE_QUEUE = "condition-order-sample-queue";
    private static final String ROUTING_KEY = "";

    @Autowired
    private RabbitAdmin rabbitAdmin;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Before
    public void setUp() throws Exception {
        DirectExchange exchange = new DirectExchange(CONDITION_ORDER_SAMPLE_EXCHANGE);
        Queue queue = new Queue(CONDITION_ORDER_SAMPLE_QUEUE);
        Binding binding = new Binding(queue.getName(), Binding.DestinationType.QUEUE, exchange.getName(), ROUTING_KEY, Collections.<String, Object>emptyMap());

        rabbitAdmin.declareExchange(exchange);
        rabbitAdmin.declareQueue(queue);
        rabbitAdmin.declareBinding(binding);
    }

    @Test
    public void testSend() throws Exception {
        Order order = new Order(123, "huhu");
        rabbitTemplate.convertAndSend(CONDITION_ORDER_SAMPLE_EXCHANGE, ROUTING_KEY, order);
        logger.info("Send message OK ==> {}", order);
        Thread.sleep(100);
    }

    @RabbitListener(queues = CONDITION_ORDER_SAMPLE_QUEUE)
    public void testReceive(@Payload Order order) {
        logger.info("Receive message <== {}", order);
    }
}
