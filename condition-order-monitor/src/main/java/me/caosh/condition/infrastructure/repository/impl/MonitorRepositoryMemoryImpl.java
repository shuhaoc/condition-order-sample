package me.caosh.condition.infrastructure.repository.impl;

import me.caosh.condition.domain.model.order.ConditionOrder;
import me.caosh.condition.infrastructure.repository.MonitorRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by caosh on 2017/8/12.
 */
@Deprecated
//@Repository("memory")
public class MonitorRepositoryMemoryImpl implements MonitorRepository {

    private final MonitorRepository monitorRepository;
    private Map<Long, ConditionOrder> monitorOrders = Collections.emptyMap();

    public MonitorRepositoryMemoryImpl(@Qualifier("redis") MonitorRepository monitorRepository) {
        this.monitorRepository = monitorRepository;
    }

    @PostConstruct
    public void init() {
        monitorOrders = new ConcurrentHashMap<>(monitorRepository.getAll());
    }

    @Override
    public Iterable<ConditionOrder> getAllOrders() {
        return Collections.unmodifiableCollection(monitorOrders.values());
    }

    @Override
    public Map<Long, ConditionOrder> getAll() {
        return Collections.unmodifiableMap(monitorOrders);
    }

    @Override
    public void save(ConditionOrder conditionOrder) {
        monitorRepository.save(conditionOrder);
        monitorOrders.put(conditionOrder.getOrderId(), conditionOrder);
    }

    @Override
    public void update(ConditionOrder conditionOrder) {
        monitorRepository.update(conditionOrder);
    }

    @Override
    public void remove(Long orderId) {
        monitorRepository.remove(orderId);
        monitorOrders.remove(orderId);
    }
}
