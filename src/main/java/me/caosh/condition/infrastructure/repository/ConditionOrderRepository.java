package me.caosh.condition.infrastructure.repository;

import com.google.common.base.Optional;
import me.caosh.condition.domain.model.order.ConditionOrder;

/**
 * Created by caosh on 2017/8/3.
 */
public interface ConditionOrderRepository {
    void save(ConditionOrder conditionOrder);

    void remove(Long orderId);

    Optional<ConditionOrder> findOne(Long orderId);


}
