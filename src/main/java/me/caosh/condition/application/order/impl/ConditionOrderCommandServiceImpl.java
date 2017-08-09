package me.caosh.condition.application.order.impl;

import me.caosh.condition.application.order.ConditionOrderCommandService;
import me.caosh.condition.domain.model.order.ConditionOrder;
import me.caosh.condition.infrastructure.repository.ConditionOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by caosh on 2017/8/9.
 */
@Service
public class ConditionOrderCommandServiceImpl implements ConditionOrderCommandService {

    @Autowired
    private ConditionOrderRepository conditionOrderRepository;

    @Override
    public void save(ConditionOrder conditionOrder) {
        conditionOrderRepository.save(conditionOrder);
    }
}
