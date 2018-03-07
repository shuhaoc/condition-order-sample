package me.caosh.condition.infrastructure.repository;

import hbec.intellitrade.condorder.domain.ConditionOrder;

import java.util.Map;

/**
 * Created by caosh on 2017/8/11.
 */
public interface MonitorRepository {
    Iterable<ConditionOrder> getAllOrders();
    
    Map<Long, ConditionOrder> getAll();

    void save(ConditionOrder conditionOrder);

    void update(ConditionOrder conditionOrder);

    void remove(Long orderId);
}
