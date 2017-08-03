package me.caosh.condition.infrastructure.repository;

import me.caosh.condition.domain.model.order.ConditionOrder;

/**
 * Created by caosh on 2017/8/3.
 */
public interface ConditionOrderRepository {
    void save(ConditionOrder conditionOrder);

    void remove(Integer orderId);


}
