package me.caosh.condition.domain.model.signal;

import com.google.common.base.MoreObjects;
import hbec.intellitrade.strategy.domain.Strategy;
import org.joda.time.LocalTime;

/**
 * 信号负载，由策略引擎发出，由信号响应系统进行处理
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2018/2/1
 */
public class SignalPayload {
    private final int triggerId;
    private final Signal signal;
    private final Strategy strategy;

    public SignalPayload(Signal signal, Strategy strategy) {
        this.triggerId = SignalPayloads.triggerId((int) strategy.getStrategyId(), LocalTime.now());
        this.signal = signal;
        this.strategy = strategy;
    }

    public SignalPayload(int triggerId, Signal signal, Strategy strategy) {
        this.triggerId = triggerId;
        this.signal = signal;
        this.strategy = strategy;
    }

    public int getTriggerId() {
        return triggerId;
    }

    public Signal getSignal() {
        return signal;
    }

    public Strategy getStrategy() {
        return strategy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SignalPayload that = (SignalPayload) o;

        if (triggerId != that.triggerId) {
            return false;
        }
        if (!signal.equals(that.signal)) {
            return false;
        }
        return strategy.equals(that.strategy);
    }

    @Override
    public int hashCode() {
        int result = triggerId;
        result = 31 * result + signal.hashCode();
        result = 31 * result + strategy.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(SignalPayload.class).omitNullValues()
                .add("triggerId", triggerId)
                .add("signal", signal)
                .add("strategy", strategy)
                .toString();
    }
}
