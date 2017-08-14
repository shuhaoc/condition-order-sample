package me.caosh.condition.application.monitor.impl;

import com.google.common.collect.Maps;
import me.caosh.condition.application.monitor.MonitorContextManage;
import me.caosh.condition.domain.model.order.ConditionOrder;
import me.caosh.condition.domain.model.order.MonitorContext;
import me.caosh.condition.infrastructure.repository.MonitorRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by caosh on 2017/8/14.
 *
 * @author caoshuhao@touker.com
 */
@Service
public class MonitorContextManageImpl implements MonitorContextManage {

    private final MonitorRepository monitorRepository;
    private Map<Long, MonitorContext> monitorContexts;

    public MonitorContextManageImpl(@Qualifier("redis") MonitorRepository monitorRepository) {
        this.monitorRepository = monitorRepository;
    }

    @PostConstruct
    public void init() {
        Map<Long, ConditionOrder> conditionOrders = monitorRepository.getAll();
        this.monitorContexts = new ConcurrentHashMap<>(Maps.transformValues(conditionOrders, MonitorContext::new));
    }

    @Override
    public Iterable<MonitorContext> getAll() {
        return Collections.unmodifiableCollection(monitorContexts.values());
    }

    @Override
    public void save(MonitorContext monitorContext) {
        monitorRepository.save(monitorContext.getConditionOrder());
        monitorContexts.put(monitorContext.getOrderId(), monitorContext);

    }

    @Override
    public void update(MonitorContext monitorContext) {
        monitorRepository.update(monitorContext.getConditionOrder());
    }

    @Override
    public void remove(Long orderId) {
        monitorRepository.remove(orderId);
        monitorContexts.remove(orderId);
    }
}
