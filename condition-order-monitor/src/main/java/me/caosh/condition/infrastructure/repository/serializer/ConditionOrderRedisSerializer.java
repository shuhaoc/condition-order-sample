package me.caosh.condition.infrastructure.repository.serializer;

import hbec.intellitrade.condorder.domain.ConditionOrder;
import me.caosh.condition.domain.dto.order.ConditionOrderDTO;
import me.caosh.condition.domain.dto.order.assembler.ConditionOrderDTOAssembler;
import me.caosh.condition.domain.dto.util.ConditionOrderDTOGSONUtils;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.nio.charset.Charset;

/**
 * Created by caosh on 2017/8/13.
 */
public class ConditionOrderRedisSerializer implements RedisSerializer<ConditionOrder> {
    @Override
    public byte[] serialize(ConditionOrder conditionOrder) throws SerializationException {
        ConditionOrderDTO conditionOrderDTO = ConditionOrderDTOAssembler.toDTO(conditionOrder);
        String json = ConditionOrderDTOGSONUtils.getConditionGSON().toJson(conditionOrderDTO);
        return json.getBytes(Charset.forName("utf-8"));
    }

    @Override
    public ConditionOrder deserialize(byte[] bytes) throws SerializationException {
        String json = new String(bytes, Charset.forName("utf-8"));
        ConditionOrderDTO conditionOrderDTO = ConditionOrderDTOGSONUtils.getConditionGSON().fromJson(json, ConditionOrderDTO.class);
        return ConditionOrderDTOAssembler.fromDTO(conditionOrderDTO);
    }
}
