package me.caosh.condition.infrastructure.repository.impl;

import com.google.common.base.Function;
import com.google.common.collect.Maps;
import me.caosh.condition.domain.dto.order.ConditionOrderDTO;
import me.caosh.condition.domain.dto.order.assembler.ConditionOrderDTOAssembler;
import me.caosh.condition.domain.model.order.ConditionOrder;
import me.caosh.condition.domain.util.ConditionOrderGSONUtils;
import me.caosh.condition.infrastructure.repository.MonitorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * Created by caosh on 2017/8/11.
 */
@Configuration
@ConfigurationProperties(prefix = "me.caosh.condition.conditionOrder")
@Repository
public class MonitorRepositoryImpl implements MonitorRepository {

    private final StringRedisTemplate redisTemplate;

    private String monitorOrdersKey;

    @Autowired
    public MonitorRepositoryImpl(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void setMonitorOrdersKey(String monitorOrdersKey) {
        this.monitorOrdersKey = monitorOrdersKey;
    }

    @Override
    public Map<Long, ConditionOrder> getAll() {
        Map<Long, String> entries = redisTemplate.<Long, String>opsForHash().entries(monitorOrdersKey);
        return Maps.transformValues(entries, json -> {
            ConditionOrderDTO conditionOrderDTO = ConditionOrderGSONUtils.getGSON().fromJson(json, ConditionOrderDTO.class);
            return ConditionOrderDTOAssembler.fromDTO(conditionOrderDTO);
        });
    }
}
