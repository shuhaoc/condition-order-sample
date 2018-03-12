package me.caosh.condition.application.order.impl;

import hbec.intellitrade.condorder.domain.ConditionOrder;
import hbec.intellitrade.condorder.domain.ConditionOrderRepository;
import me.caosh.condition.application.order.OrderCommandService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by caosh on 2017/8/9.
 */
@Service
public class OrderCommandServiceImpl implements OrderCommandService {
    private static final Logger logger = LoggerFactory.getLogger(OrderCommandServiceImpl.class);

    private final ConditionOrderRepository conditionOrderRepository;

    @Autowired
    public OrderCommandServiceImpl(ConditionOrderRepository conditionOrderRepository) {
        this.conditionOrderRepository = conditionOrderRepository;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void save(ConditionOrder conditionOrder) {
        logger.info("Creating order => {}", conditionOrder);
        conditionOrderRepository.save(conditionOrder);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void update(ConditionOrder conditionOrder) {
        logger.info("Updating order => {}", conditionOrder);
        conditionOrderRepository.update(conditionOrder);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void remove(Long orderId) {
        logger.info("Updating order => {}", orderId);
        conditionOrderRepository.remove(orderId);
    }
}
