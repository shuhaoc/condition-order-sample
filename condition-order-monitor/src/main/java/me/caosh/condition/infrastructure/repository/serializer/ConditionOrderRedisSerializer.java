package me.caosh.condition.infrastructure.repository.serializer;

import hbec.intellitrade.condorder.domain.ConditionOrder;
import hbec.commons.domain.intellitrade.condorder.ConditionOrderDTO;
import hbec.commons.domain.intellitrade.util.ConditionOrderAssemblers;
import hbec.commons.domain.intellitrade.util.ConditionOrderDTOGSONUtils;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.nio.charset.Charset;

/**
 * Created by caosh on 2017/8/13.
 */
public class ConditionOrderRedisSerializer implements RedisSerializer<ConditionOrder> {
    @Override
    public byte[] serialize(ConditionOrder conditionOrder) throws SerializationException {
        ConditionOrderDTO conditionOrderDTO = ConditionOrderAssemblers.dtoSupported()
                                                                      .assemble(conditionOrder,
                                                                                ConditionOrderDTO.class);
        String json = ConditionOrderDTOGSONUtils.getConditionGSON().toJson(conditionOrderDTO);
        return json.getBytes(Charset.forName("utf-8"));
    }

    @Override
    public ConditionOrder deserialize(byte[] bytes) throws SerializationException {
        String json = new String(bytes, Charset.forName("utf-8"));
        ConditionOrderDTO conditionOrderDTO = ConditionOrderDTOGSONUtils.getConditionGSON()
                                                                        .fromJson(json, ConditionOrderDTO.class);
        return ConditionOrderAssemblers.dtoSupported().disassemble(conditionOrderDTO, ConditionOrder.class);
    }
}
