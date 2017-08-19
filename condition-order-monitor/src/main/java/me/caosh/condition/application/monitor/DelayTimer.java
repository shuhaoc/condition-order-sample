package me.caosh.condition.application.monitor;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Created by caosh on 2017/8/19.
 */
public class DelayTimer {
    private final LocalDateTime occurTime;
    private final int delaySeconds;

    public DelayTimer(int delaySeconds) {
        this.occurTime = LocalDateTime.now();
        this.delaySeconds = delaySeconds;
    }

    public Duration getCurrentDuration() {
        return Duration.between(occurTime, LocalDateTime.now());
    }

    public boolean isTimesUp() {
        return getCurrentDuration().getSeconds() >= delaySeconds;
    }
}
