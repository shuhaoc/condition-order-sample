package me.caosh.condition.infrastructure.repository;

import me.caosh.condition.domain.model.order.ConditionOrder;

import java.util.List;
import java.util.Optional;

/**
 * Created by caosh on 2017/8/3.
 */
public interface ConditionOrderRepository {
    void save(ConditionOrder conditionOrder);

    void remove(Long orderId);

    Optional<ConditionOrder> findOne(Long orderId);

    List<ConditionOrder> findAllActive();
}
