package me.caosh.condition.infrastructure.timer;

import me.caosh.condition.infrastructure.timer.event.TimerEvent;
import me.caosh.condition.infrastructure.eventbus.MonitorEntrustBus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by caosh on 2017/8/16.
 *
 * @author caoshuhao@touker.com
 */
@Component
public class MonitorTimer {
    @Scheduled(cron = "0/1 * 9-15 * *  1-5")
    public void onTimer() {
        MonitorEntrustBus.EVENT_SERIALIZED.post(new TimerEvent());
    }
}
