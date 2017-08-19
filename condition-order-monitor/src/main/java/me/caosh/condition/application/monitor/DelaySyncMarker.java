package me.caosh.condition.application.monitor;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * TODO: 抽象复用
 *
 * Created by caosh on 2017/8/19.
 */
public class DelaySyncMarker {
    private final LocalDateTime lastDirtyTime;
    private final int delaySeconds;

    public DelaySyncMarker(int delaySeconds) {
        this.lastDirtyTime = LocalDateTime.now();
        this.delaySeconds = delaySeconds;
    }

    public Duration getDirtyDuration() {
        return Duration.between(lastDirtyTime, LocalDateTime.now());
    }

    public boolean isTimesUp() {
        return getDirtyDuration().getSeconds() >= delaySeconds;
    }
}
