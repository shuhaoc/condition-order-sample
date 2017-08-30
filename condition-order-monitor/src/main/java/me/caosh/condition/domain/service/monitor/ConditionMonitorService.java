package me.caosh.condition.domain.service.monitor;

import me.caosh.condition.domain.model.market.RealTimeMarket;
import me.caosh.condition.domain.model.monitor.MonitorContext;

/**
 * Created by caosh on 2017/8/30.
 */
public interface ConditionMonitorService {
    void checkWithRealTimeMarket(MonitorContext monitorContext, RealTimeMarket realTimeMarket);

    void checkWithTime(MonitorContext monitorContext);
}
