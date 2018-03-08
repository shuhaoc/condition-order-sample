package me.caosh.condition.domain.model.strategy.container;

import com.google.common.base.MoreObjects;
import hbec.intellitrade.strategy.domain.Strategy;
import hbec.intellitrade.strategy.domain.shared.DelayMarker;
import hbec.intellitrade.strategy.domain.signal.Signal;
import hbec.intellitrade.strategy.domain.signal.Signals;
import org.joda.time.Duration;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 策略上下文
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2018/2/1
 */
public class StrategyContext {
    private static final Logger logger = LoggerFactory.getLogger(StrategyContext.class);

    private final BucketKey bucketKey;
    private final Strategy strategy;
    private final StrategyContextConfig strategyContextConfig;
    private DelayMarker triggerLock;
    private DelayMarker delaySyncMarker;

    public StrategyContext(BucketKey bucketKey, Strategy strategy, StrategyContextConfig strategyContextConfig) {
        this.bucketKey = bucketKey;
        this.strategy = strategy;
        this.strategyContextConfig = strategyContextConfig;
    }

    public BucketKey getBucketKey() {
        return bucketKey;
    }

    public Strategy getStrategy() {
        return strategy;
    }

    /**
     * 锁定触发，锁定期间不执行策略的任何条件
     */
    protected void lockTriggering() {
        triggerLock = new DelayMarker(strategyContextConfig.getLockTriggerSeconds());
    }

    /**
     * 获取是否处于触发锁定状态
     *
     * @return 是否处于触发锁定状态
     */
    protected boolean isTriggerLocked() {
        return triggerLock != null && !triggerLock.isTimesUp();
    }

    /**
     * 获取触发锁定已锁定时间
     *
     * @return 触发锁定已锁定时间
     */
    protected Duration getTriggerLockedDuration() {
        return triggerLock.getCurrentDuration();
    }

    /**
     * 标志延迟同步
     */
    protected void markDelaySync() {
        delaySyncMarker = new DelayMarker(strategyContextConfig.getDelaySyncSeconds());
    }

    /**
     * 清除延迟同步标志
     */
    private void clearDelaySyncMarker() {
        delaySyncMarker = null;
    }

    /**
     * 延迟同步是否到期
     *
     * @return 延迟同步是否到期
     */
    private boolean isDelaySyncTimesUp() {
        return delaySyncMarker != null && delaySyncMarker.isTimesUp();
    }

    /**
     * 是否需要同步
     *
     * @return 是否需要同步
     */
    public boolean isNeedSync() {
        return delaySyncMarker != null;
    }

    /**
     * 接受秒级时间通知，对所有策略适用
     *
     * @param localDateTime 时间点
     * @return 信号
     */
    public Signal onTimeTick(LocalDateTime localDateTime) {
        // 触发锁定期间不执行
        if (isTriggerLocked()) {
            logger.warn("Trigger locked, strategyId={}, lockedDuration={}", getStrategy().getStrategyId(),
                    getTriggerLockedDuration());
            return Signals.none();
        }

        Signal signal = strategy.onTimeTick(localDateTime);
        if (signal.isValid()) {
            lockTriggering();
            return signal;
        }

        // 延迟同步到期的触发同步信号（仅行情驱动策略需要延迟同步）
        if (isDelaySyncTimesUp()) {
            clearDelaySyncMarker();
            return Signals.cacheSync();
        }
        return Signals.none();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        StrategyContext that = (StrategyContext) o;

        if (!bucketKey.equals(that.bucketKey)) {
            return false;
        }
        return strategy.equals(that.strategy);
    }

    @Override
    public int hashCode() {
        int result = bucketKey.hashCode();
        result = 31 * result + strategy.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(StrategyContext.class).omitNullValues()
                .add("bucketKey", bucketKey)
                .add("strategy", strategy)
                .add("strategyContextConfig", strategyContextConfig)
                .add("triggerLock", triggerLock)
                .add("delaySyncMarker", delaySyncMarker)
                .toString();
    }
}
