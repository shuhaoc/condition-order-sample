package me.caosh.condition.infrastructure.rabbitmq;

import com.google.gson.Gson;
import me.caosh.condition.infrastructure.Order;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.stereotype.Component;

/**
 * Created by caosh on 2017/7/23.
 */
@Component
public class OrderMessageConverter implements MessageConverter {
    private static final Gson GSON = new Gson();
    @Override
    public Message toMessage(Object o, MessageProperties messageProperties) throws MessageConversionException {
        Order order = (Order) o;
        return MessageBuilder.withBody(GSON.toJson(order).getBytes()).build();
    }

    @Override
    public Object fromMessage(Message message) throws MessageConversionException {
        String json = new String(message.getBody());
        return GSON.fromJson(json, Order.class);
    }
}
