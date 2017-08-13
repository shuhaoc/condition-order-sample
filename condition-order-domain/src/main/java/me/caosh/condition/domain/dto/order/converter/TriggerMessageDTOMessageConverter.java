package me.caosh.condition.domain.dto.order.converter;


import me.caosh.condition.domain.dto.order.ConditionOrderDTO;
import me.caosh.condition.domain.dto.order.TriggerMessageDTO;
import me.caosh.condition.domain.util.ConditionOrderGSONUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;

import java.nio.charset.Charset;

/**
 * Created by caosh on 2017/8/11.
 *
 * @author caoshuhao@touker.com
 */
public class TriggerMessageDTOMessageConverter implements MessageConverter {
    @Override
    public Message toMessage(Object o, MessageProperties messageProperties) throws MessageConversionException {
        String json = ConditionOrderGSONUtils.getGSON().toJson(o);
        return MessageBuilder.withBody(json.getBytes(Charset.forName("utf-8"))).build();
    }

    @Override
    public Object fromMessage(Message message) throws MessageConversionException {
        String json = new String(message.getBody(), Charset.forName("utf-8"));
        return ConditionOrderGSONUtils.getGSON().fromJson(json, TriggerMessageDTO.class);
    }
}
