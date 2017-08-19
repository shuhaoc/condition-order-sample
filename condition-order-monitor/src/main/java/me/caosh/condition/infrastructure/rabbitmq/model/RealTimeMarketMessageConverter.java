package me.caosh.condition.infrastructure.rabbitmq.model;

import com.google.gson.reflect.TypeToken;
import me.caosh.condition.domain.util.ConditionOrderDTOGSONUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;

import java.nio.charset.Charset;
import java.util.HashMap;

/**
 * Created by caosh on 2017/8/9.
 */
public class RealTimeMarketMessageConverter implements MessageConverter {
    @Override
    public Message toMessage(Object o, MessageProperties messageProperties) throws MessageConversionException {
        String json = ConditionOrderDTOGSONUtils.getMarketGSON().toJson(o);
        return MessageBuilder.withBody(json.getBytes(Charset.forName("utf-8"))).build();
    }

    @Override
    public Object fromMessage(Message message) throws MessageConversionException {
        String json = new String(message.getBody(), Charset.forName("utf-8"));
        return ConditionOrderDTOGSONUtils.getMarketGSON().fromJson(json, new TypeToken<HashMap<String, RealTimeMarketSimpleDTO>>(){
        }.getType());
    }
}
