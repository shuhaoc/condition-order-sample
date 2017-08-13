package me.caosh.condition.infrastructure.repository;

import me.caosh.condition.domain.model.order.ConditionOrder;

import java.util.Map;

/**
 * Created by caosh on 2017/8/11.
 */
public interface MonitorRepository {
    Iterable<ConditionOrder> getAllOrders();
    
    Map<Long, ConditionOrder> getAll();

    void update(ConditionOrder conditionOrder);
}
