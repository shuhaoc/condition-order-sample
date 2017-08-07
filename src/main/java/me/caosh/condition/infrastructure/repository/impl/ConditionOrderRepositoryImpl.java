package me.caosh.condition.infrastructure.repository.impl;

import com.google.common.base.Optional;
import me.caosh.condition.domain.model.order.ConditionOrder;
import me.caosh.condition.infrastructure.repository.ConditionOrderRepository;
import me.caosh.condition.infrastructure.repository.model.ConditionOrderDO;
import me.caosh.condition.infrastructure.repository.model.ConditionOrderDOAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;

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
        conditionOrderDORepository.delete(orderId);
    }

    @Override
    public Optional<ConditionOrder> findOne(Long orderId) {
        ConditionOrderDO conditionOrderDO = conditionOrderDORepository.findOne(orderId);
        if (conditionOrderDO == null) {
            return Optional.absent();
        }
        ConditionOrder conditionOrder = ConditionOrderDOAssembler.fromDO(conditionOrderDO);
        return Optional.of(conditionOrder);
    }
}
