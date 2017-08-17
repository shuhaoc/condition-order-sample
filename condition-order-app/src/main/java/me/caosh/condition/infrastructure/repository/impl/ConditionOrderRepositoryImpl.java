package me.caosh.condition.infrastructure.repository.impl;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import me.caosh.condition.domain.model.order.ConditionOrder;
import me.caosh.condition.domain.model.trade.EntrustOrder;
import me.caosh.condition.infrastructure.repository.ConditionOrderRepository;
import me.caosh.condition.infrastructure.repository.model.ConditionOrderDO;
import me.caosh.condition.infrastructure.repository.model.ConditionOrderDOAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created by caosh on 2017/8/3.
 */
@Repository
public class ConditionOrderRepositoryImpl implements ConditionOrderRepository {
    @Autowired
    private ConditionOrderDORepository conditionOrderDORepository;

    @Override
    public void save(ConditionOrder conditionOrder) {
        ConditionOrderDO conditionOrderDO = ConditionOrderDOAssembler.toDO(conditionOrder);
        conditionOrderDORepository.save(conditionOrderDO);
    }

    @Override
    public void remove(Long orderId) {
        ConditionOrderDO conditionOrderDO = conditionOrderDORepository.findOne(orderId);
        if (conditionOrderDO != null) {
            conditionOrderDO.setDeleted(true);
            conditionOrderDORepository.save(conditionOrderDO);
        }
    }

    @Override
    public Optional<ConditionOrder> findOne(Long orderId) {
        ConditionOrderDO conditionOrderDO = conditionOrderDORepository.findNotDeleted(orderId);
        if (conditionOrderDO == null) {
            return Optional.empty();
        }
        ConditionOrder conditionOrder = ConditionOrderDOAssembler.fromDO(conditionOrderDO);
        return Optional.of(conditionOrder);
    }

    @Override
    public List<ConditionOrder> findAllActive() {
        List<ConditionOrderDO> conditionOrderDOs = conditionOrderDORepository.findAllActive();
        return Lists.transform(conditionOrderDOs, ConditionOrderDOAssembler::fromDO);
    }

    @Override
    public List<ConditionOrder> findMonitoringOrders(String customerNo) {
        List<ConditionOrderDO> monitoring = conditionOrderDORepository.findMonitoring(customerNo);
        return Lists.transform(monitoring, ConditionOrderDOAssembler::fromDO);
    }
}
