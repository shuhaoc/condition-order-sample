package me.caosh.condition.application.order.impl;

import hbec.intellitrade.condorder.domain.ConditionOrder;
import hbec.intellitrade.condorder.domain.OrderState;
import me.caosh.condition.application.order.ConditionOrderCommandService;
import me.caosh.condition.infrastructure.rabbitmq.ConditionOrderProducer;
import me.caosh.condition.infrastructure.repository.ConditionOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by caosh on 2017/8/9.
 */
@Service
public class ConditionOrderCommandServiceImpl implements ConditionOrderCommandService {

    private final ConditionOrderRepository conditionOrderRepository;
    private final ConditionOrderProducer conditionOrderProducer;

    @Autowired
    public ConditionOrderCommandServiceImpl(ConditionOrderRepository conditionOrderRepository, ConditionOrderProducer conditionOrderProducer) {
        this.conditionOrderRepository = conditionOrderRepository;
        this.conditionOrderProducer = conditionOrderProducer;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void save(ConditionOrder conditionOrder) {
        conditionOrderRepository.save(conditionOrder);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void update(ConditionOrder conditionOrder) {
        conditionOrderRepository.save(conditionOrder);
//        if (conditionOrder.getOrderState() == OrderState.ACTIVE) {
//            conditionOrderProducer.update(conditionOrder);
//        } else {
//            conditionOrderProducer.remove(conditionOrder.getOrderId());
//        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateDynamicProperties(ConditionOrder conditionOrder) {
        conditionOrderRepository.save(conditionOrder);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void remove(Long orderId) {
        conditionOrderRepository.remove(orderId);
    }
}
