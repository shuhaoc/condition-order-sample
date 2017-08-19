package me.caosh.condition.domain.dto.order.converter;

import me.caosh.condition.domain.dto.util.ConditionOrderDTOGSONUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;

import java.nio.charset.Charset;

/**
 * Created by caosh on 2017/8/14.
 *
 * @author caoshuhao@touker.com
 */
public class ConditionOrderGSONMessageConverter<T> implements MessageConverter {

    private final Class<T> clazz;
    private final Charset charset = Charset.forName("utf-8");;

    public ConditionOrderGSONMessageConverter(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public Message toMessage(Object o, MessageProperties messageProperties) throws MessageConversionException {
        String json = ConditionOrderDTOGSONUtils.getConditionGSON().toJson(o);
        return MessageBuilder.withBody(json.getBytes(charset)).build();
    }

    @Override
    public Object fromMessage(Message message) throws MessageConversionException {
        String json = new String(message.getBody(), Charset.forName("utf-8"));
        return ConditionOrderDTOGSONUtils.getConditionGSON().fromJson(json, clazz);
    }
}
