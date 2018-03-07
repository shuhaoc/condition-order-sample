package me.caosh.condition.infrastructure.repository.impl;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import hbec.intellitrade.condorder.domain.ConditionOrder;
import hbec.intellitrade.condorder.domain.ConditionOrderBuilder;
import me.caosh.autoasm.AutoAssemblers;
import me.caosh.condition.infrastructure.rabbitmq.ConditionOrderProducer;
import hbec.intellitrade.condorder.domain.ConditionOrderRepository;
import me.caosh.condition.infrastructure.tunnel.model.ConditionOrderDO;
import me.caosh.condition.infrastructure.tunnel.ConditionOrderTunnel;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by caosh on 2017/8/3.
 */
@Repository
public class ConditionOrderRepositoryImpl implements ConditionOrderRepository {
    private final ConditionOrderTunnel conditionOrderTunnel;
    private final ConditionOrderProducer conditionOrderProducer;

    public ConditionOrderRepositoryImpl(ConditionOrderTunnel conditionOrderTunnel, ConditionOrderProducer conditionOrderProducer) {
        this.conditionOrderTunnel = conditionOrderTunnel;
        this.conditionOrderProducer = conditionOrderProducer;
    }

    @Override
    public void save(ConditionOrder conditionOrder) {
        ConditionOrderDO conditionOrderDO = AutoAssemblers.getDefault().assemble(conditionOrder, ConditionOrderDO.class);
        conditionOrderTunnel.save(conditionOrderDO);

        conditionOrderProducer.save(conditionOrder);
    }

    @Override
    public void update(ConditionOrder conditionOrder) {
        ConditionOrderDO conditionOrderDO = AutoAssemblers.getDefault().assemble(conditionOrder, ConditionOrderDO.class);
        conditionOrderTunnel.save(conditionOrderDO);

        if (conditionOrder.isMonitoringState()) {
            conditionOrderProducer.update(conditionOrder);
        } else {
            conditionOrderProducer.remove(conditionOrder.getOrderId());
        }
    }

    @Override
    public void remove(Long orderId) {
        ConditionOrderDO conditionOrderDO = conditionOrderTunnel.findOne(orderId);
        if (conditionOrderDO != null) {
            conditionOrderDO.setDeleted(true);
            conditionOrderTunnel.save(conditionOrderDO);
        }

        conditionOrderProducer.remove(orderId);
    }

    @Override
    public Optional<ConditionOrder> findOne(Long orderId) {
        ConditionOrderDO conditionOrderDO = conditionOrderTunnel.findNotDeleted(orderId);
        if (conditionOrderDO == null) {
            return Optional.absent();
        }
        ConditionOrder conditionOrder = AutoAssemblers.getDefault()
                .disassemble(conditionOrderDO, ConditionOrderBuilder.class)
                .build();
        return Optional.of(conditionOrder);
    }

    @Override
    public List<ConditionOrder> findAllMonitoring() {
        List<ConditionOrderDO> conditionOrderDOs = conditionOrderTunnel.findAllMonitoring();
        return Lists.transform(conditionOrderDOs, new Function<ConditionOrderDO, ConditionOrder>() {
            @Override
            public ConditionOrder apply(ConditionOrderDO conditionOrderDO) {
                return AutoAssemblers.getDefault().disassemble(conditionOrderDO, ConditionOrderBuilder.class).build();
            }
        });
    }

}
