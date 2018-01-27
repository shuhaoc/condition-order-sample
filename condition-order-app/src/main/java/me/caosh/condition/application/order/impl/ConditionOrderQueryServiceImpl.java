package me.caosh.condition.application.order.impl;

import com.google.common.base.Optional;
import me.caosh.condition.application.order.ConditionOrderQueryService;
import me.caosh.condition.domain.model.order.ConditionOrder;
import me.caosh.condition.domain.model.trade.EntrustOrder;
import me.caosh.condition.infrastructure.repository.ConditionOrderRepository;
import me.caosh.condition.infrastructure.repository.EntrustOrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by caosh on 2017/8/14.
 */
@Service
public class ConditionOrderQueryServiceImpl implements ConditionOrderQueryService {

    private final ConditionOrderRepository conditionOrderRepository;
    private final EntrustOrderRepository entrustOrderRepository;

    public ConditionOrderQueryServiceImpl(ConditionOrderRepository conditionOrderRepository,
                                          EntrustOrderRepository entrustOrderRepository) {
        this.conditionOrderRepository = conditionOrderRepository;
        this.entrustOrderRepository = entrustOrderRepository;
    }

    @Override
    public List<ConditionOrder> listMonitoringOrders(String customerNo) {
        return conditionOrderRepository.findMonitoringOrders(customerNo);
    }

    @Override
    public Optional<ConditionOrder> getConditionOrder(Long orderId) {
        return conditionOrderRepository.findOne(orderId);
    }

    @Override
    public Page<EntrustOrder> listEntrustOrders(String customerNo, Pageable pageable) {
        return entrustOrderRepository.findByCustomerNo(customerNo, pageable);
    }
}
