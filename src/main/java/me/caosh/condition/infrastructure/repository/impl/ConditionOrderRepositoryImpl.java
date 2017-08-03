package me.caosh.condition.infrastructure.repository.impl;

import me.caosh.condition.domain.model.order.ConditionOrder;
import me.caosh.condition.infrastructure.repository.ConditionOrderRepository;
import me.caosh.condition.infrastructure.repository.model.ConditionOrderDORepository;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by caosh on 2017/8/3.
 */
public class ConditionOrderRepositoryImpl implements ConditionOrderRepository {
    @Autowired
    private ConditionOrderDORepository conditionOrderDORepository;

    @Override
    public void save(ConditionOrder conditionOrder) {

    }

    @Override
    public void remove(Integer orderId) {

    }
}
