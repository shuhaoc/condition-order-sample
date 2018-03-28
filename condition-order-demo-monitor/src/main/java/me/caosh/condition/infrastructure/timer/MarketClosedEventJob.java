package me.caosh.condition.infrastructure.timer;

import me.caosh.condition.domain.event.MarketClosedEvent;
import me.caosh.condition.infrastructure.eventbus.MonitorEventBus;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/3/13
 */
public class MarketClosedEventJob implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        MonitorEventBus.EVENT_SERIALIZED.post(new MarketClosedEvent());
    }
}
