package me.caosh.condition.infrastructure.repository.impl;

import com.google.common.base.Optional;
import hbec.intellitrade.condorder.domain.ConditionOrder;
import me.caosh.condition.infrastructure.rabbitmq.ConditionOrderProducer;
import hbec.intellitrade.condorder.domain.ConditionOrderRepository;
import me.caosh.condition.infrastructure.tunnel.ConditionOrderDbTunnel;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by caosh on 2017/8/3.
 */
@Repository
public class ConditionOrderRepositoryImpl implements ConditionOrderRepository {
    private final ConditionOrderProducer conditionOrderProducer;
    private final ConditionOrderDbTunnel conditionOrderDbTunnel;

    public ConditionOrderRepositoryImpl(ConditionOrderProducer conditionOrderProducer, ConditionOrderDbTunnel conditionOrderDbTunnel) {
        this.conditionOrderProducer = conditionOrderProducer;
        this.conditionOrderDbTunnel = conditionOrderDbTunnel;
    }

    @Override
    public void save(ConditionOrder conditionOrder) {
        conditionOrderDbTunnel.save(conditionOrder);

        conditionOrderProducer.save(conditionOrder);
    }

    @Override
    public void update(ConditionOrder conditionOrder) {
        conditionOrderDbTunnel.save(conditionOrder);

        if (conditionOrder.isMonitoringState()) {
            conditionOrderProducer.save(conditionOrder);
        } else {
            conditionOrderProducer.remove(conditionOrder.getOrderId());
        }
    }

    @Override
    public void remove(Long orderId) {
        conditionOrderDbTunnel.remove(orderId);

        conditionOrderProducer.remove(orderId);
    }

    @Override
    public Optional<ConditionOrder> findOne(Long orderId) {
        return conditionOrderDbTunnel.findOne(orderId);
    }

    @Override
    public List<ConditionOrder> findAllMonitoring() {
        return conditionOrderDbTunnel.findAllMonitoring();
    }
}
