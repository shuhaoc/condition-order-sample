package me.caosh.condition.infrastructure.repository.impl;

import me.caosh.condition.domain.dto.order.serializer.ConditionOrderRedisSerializer;
import me.caosh.condition.domain.model.order.ConditionOrder;
import me.caosh.condition.infrastructure.repository.MonitorRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * Created by caosh on 2017/8/11.
 */
@Configuration
@ConfigurationProperties(prefix = "me.caosh.condition.conditionOrder")
@Repository("redis")
public class MonitorRepositoryRedisImpl implements MonitorRepository {

    private final RedisTemplate<String, ConditionOrder> redisTemplate;

    private String monitorOrdersKey;

    @Bean("monitorOrdersRedisTemplate")
    public static RedisTemplate createRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, ConditionOrder> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new ConditionOrderRedisSerializer());
        redisTemplate.setHashKeySerializer(new GenericToStringSerializer<>(Long.class));
        redisTemplate.setHashValueSerializer(new ConditionOrderRedisSerializer());
        return redisTemplate;
    }

    public MonitorRepositoryRedisImpl(@Qualifier("monitorOrdersRedisTemplate") RedisTemplate<String, ConditionOrder> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void setMonitorOrdersKey(String monitorOrdersKey) {
        this.monitorOrdersKey = monitorOrdersKey;
    }

    @Override
    public Iterable<ConditionOrder> getAllOrders() {
        return getAll().values();
    }

    @Override
    public Map<Long, ConditionOrder> getAll() {
        return redisTemplate.<Long, ConditionOrder>opsForHash().entries(monitorOrdersKey);
    }

    @Override
    public void save(ConditionOrder conditionOrder) {
        update(conditionOrder);
    }

    @Override
    public void update(ConditionOrder conditionOrder) {
        redisTemplate.<Long, ConditionOrder>opsForHash().put(monitorOrdersKey, conditionOrder.getOrderId(), conditionOrder);
    }

    @Override
    public void remove(Long orderId) {
        redisTemplate.<Long, ConditionOrder>opsForHash().delete(monitorOrdersKey, orderId);
    }
}
