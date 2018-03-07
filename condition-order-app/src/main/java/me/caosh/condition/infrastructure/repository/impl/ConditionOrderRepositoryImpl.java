package me.caosh.condition.infrastructure.repository.impl;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import hbec.intellitrade.condorder.domain.ConditionOrder;
import hbec.intellitrade.condorder.domain.ConditionOrderBuilder;
import me.caosh.autoasm.AutoAssemblers;
import me.caosh.condition.infrastructure.repository.ConditionOrderRepository;
import me.caosh.condition.infrastructure.repository.model.ConditionOrderDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by caosh on 2017/8/3.
 */
@Repository
public class ConditionOrderRepositoryImpl implements ConditionOrderRepository {
    @Autowired
    private ConditionOrderDORepository conditionOrderDORepository;

    @Override
    public void save(ConditionOrder conditionOrder) {
        ConditionOrderDO conditionOrderDO = AutoAssemblers.getDefault().assemble(conditionOrder, ConditionOrderDO.class);
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
            return Optional.absent();
        }
        ConditionOrder conditionOrder = AutoAssemblers.getDefault()
                .disassemble(conditionOrderDO, ConditionOrderBuilder.class)
                .build();
        return Optional.of(conditionOrder);
    }

    @Override
    public List<ConditionOrder> findAllActive() {
        List<ConditionOrderDO> conditionOrderDOs = conditionOrderDORepository.findAllActive();
        return Lists.transform(conditionOrderDOs, new Function<ConditionOrderDO, ConditionOrder>() {
            @Override
            public ConditionOrder apply(ConditionOrderDO conditionOrderDO) {
                return AutoAssemblers.getDefault().disassemble(conditionOrderDO, ConditionOrderBuilder.class).build();
            }
        });
    }

    @Override
    public List<ConditionOrder> findMonitoringOrders(String customerNo) {
        List<ConditionOrderDO> monitoring = conditionOrderDORepository.findMonitoring(customerNo);
        return Lists.transform(monitoring, new Function<ConditionOrderDO, ConditionOrder>() {
            @Override
            public ConditionOrder apply(ConditionOrderDO conditionOrderDO) {
                return AutoAssemblers.getDefault().disassemble(conditionOrderDO, ConditionOrderBuilder.class).build();
            }
        });
    }
}
