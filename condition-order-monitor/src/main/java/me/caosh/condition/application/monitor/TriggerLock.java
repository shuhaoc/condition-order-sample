package me.caosh.condition.application.monitor;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Created by caosh on 2017/8/17.
 */
public class TriggerLock {
    private final LocalDateTime triggerLockTime;
    private final int autoReleaseSeconds;

    public TriggerLock(int autoReleaseSeconds) {
        this.triggerLockTime = LocalDateTime.now();
        this.autoReleaseSeconds = autoReleaseSeconds;
    }

    public Duration lockedDuration() {
        return Duration.between(triggerLockTime, LocalDateTime.now());
    }

    public boolean isLocked() {
        return lockedDuration().getSeconds() < autoReleaseSeconds;
    }
}
