package me.caosh.condition.infrastructure.repository.impl;

import me.caosh.condition.infrastructure.repository.model.ConditionOrderDO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by caosh on 2017/8/3.
 */
interface ConditionOrderDORepository extends CrudRepository<ConditionOrderDO, Long> {

    @Query("select c from ConditionOrderDO c where c.deleted = 0 and c.orderId = ?1")
    ConditionOrderDO findNotDeleted(Long orderId);

    @Query("select c from ConditionOrderDO c where c.deleted = 0 and c.orderState = 1")
    List<ConditionOrderDO> findAllActive();
}
