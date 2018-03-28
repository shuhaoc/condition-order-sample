package hbec.intellitrade.strategy.container;

import com.google.common.base.MoreObjects;

/**
 * 策略上下文配置
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2018/2/7
 */
public class StrategyContextConfig {
    private final int lockTriggerSeconds;
    private final int delaySyncSeconds;

    public static final StrategyContextConfig DEFAULT = new StrategyContextConfig(30, 60);

    public StrategyContextConfig(int lockTriggerSeconds, int delaySyncSeconds) {
        this.lockTriggerSeconds = lockTriggerSeconds;
        this.delaySyncSeconds = delaySyncSeconds;
    }

    public int getLockTriggerSeconds() {
        return lockTriggerSeconds;
    }

    public int getDelaySyncSeconds() {
        return delaySyncSeconds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        StrategyContextConfig that = (StrategyContextConfig) o;

        if (lockTriggerSeconds != that.lockTriggerSeconds) {
            return false;
        }
        return delaySyncSeconds == that.delaySyncSeconds;
    }

    @Override
    public int hashCode() {
        int result = lockTriggerSeconds;
        result = 31 * result + delaySyncSeconds;
        return result;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(StrategyContextConfig.class).omitNullValues()
                .add("lockTriggerSeconds", lockTriggerSeconds)
                .add("delaySyncSeconds", delaySyncSeconds)
                .toString();
    }
}
