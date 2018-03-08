package me.caosh.condition.application.order.impl;

import hbec.intellitrade.condorder.domain.ConditionOrder;
import me.caosh.condition.application.order.OrderCommandService;
import hbec.intellitrade.condorder.domain.ConditionOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by caosh on 2017/8/9.
 */
@Service
public class OrderCommandServiceImpl implements OrderCommandService {

    private final ConditionOrderRepository conditionOrderRepository;

    @Autowired
    public OrderCommandServiceImpl(ConditionOrderRepository conditionOrderRepository) {
        this.conditionOrderRepository = conditionOrderRepository;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void save(ConditionOrder conditionOrder) {
        conditionOrderRepository.save(conditionOrder);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void update(ConditionOrder conditionOrder) {
        conditionOrderRepository.update(conditionOrder);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void remove(Long orderId) {
        conditionOrderRepository.remove(orderId);
    }
}
