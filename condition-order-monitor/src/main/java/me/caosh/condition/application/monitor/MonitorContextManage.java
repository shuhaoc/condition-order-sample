package me.caosh.condition.application.monitor;

import me.caosh.condition.domain.model.order.MonitorContext;

/**
 * Created by caosh on 2017/8/14.
 *
 * @author caoshuhao@touker.com
 */
public interface MonitorContextManage {
    Iterable<MonitorContext> getAll();

    void save(MonitorContext monitorContext);

    void update(MonitorContext monitorContext);

    void remove(Long orderId);
}
