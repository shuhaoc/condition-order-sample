package me.caosh.condition.domain.model.strategy;

import com.google.common.base.MoreObjects;
import me.caosh.condition.domain.model.signal.Signal;

/**
 * Created by caosh on 2017/8/1.
 */
@Deprecated
public class CheckResult {
    private final Signal signal;

    public CheckResult(Signal signal) {
        this.signal = signal;
    }

    public Signal getSignal() {
        return signal;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("signal", signal)
                .toString();
    }
}
