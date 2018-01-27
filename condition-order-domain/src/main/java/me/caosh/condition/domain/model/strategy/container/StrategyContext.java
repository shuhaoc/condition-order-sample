package me.caosh.condition.domain.model.strategy.container;

import com.google.common.base.MoreObjects;
import me.caosh.condition.domain.model.order.constant.StrategyState;
import me.caosh.condition.domain.model.signal.Signal;
import me.caosh.condition.domain.model.signal.Signals;
import me.caosh.condition.domain.model.signal.TradeSignal;
import me.caosh.condition.domain.model.strategy.Strategy;
import me.caosh.condition.domain.model.strategy.TimeDrivenStrategy;
import me.caosh.condition.domain.model.strategy.shared.DelayMarker;
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
     * @return 信号
     */
    public Signal onSecondTick() {
        // 触发锁定期间不执行
        if (isTriggerLocked()) {
            logger.warn("Trigger locked, strategyId={}, lockedDuration={}", getStrategy().getStrategyId(),
                    getTriggerLockedDuration());
            return Signals.none();
        }

        if (getStrategy().getStrategyState() != StrategyState.ACTIVE) {
            logger.warn("Illegal state, strategyId={}, strategyState={}", getStrategy().getStrategyId(),
                    getStrategy().getStrategyState());
            return Signals.none();
        }

        // 过期判断
        if (isStrategyExpired()) {
            return Signals.expire();
        }

        // 延迟同步到期的触发同步信号（仅行情驱动策略需要延迟同步，不会影响时间驱动策略的触发）
        if (isDelaySyncTimesUp()) {
            clearDelaySyncMarker();
            return Signals.cacheSync();
        }

        // 计算时间驱动策略的条件
        if (getStrategy() instanceof TimeDrivenStrategy) {
            TimeDrivenStrategy timeDrivenStrategy = (TimeDrivenStrategy) getStrategy();
            TradeSignal tradeSignal = timeDrivenStrategy.getCondition().onSecondTick();
            if (tradeSignal.isValid()) {
                lockTriggering();
            }
            return tradeSignal;
        }
        return Signals.none();
    }

    private boolean isStrategyExpired() {
        boolean expireTimeConfigured = strategy.getExpireTime().isPresent();
        if (expireTimeConfigured) {
            return LocalDateTime.now().compareTo(strategy.getExpireTime().get()) >= 0;
        }
        return false;
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
        if (!strategy.equals(that.strategy)) {
            return false;
        }
        if (!strategyContextConfig.equals(that.strategyContextConfig)) {
            return false;
        }
        if (triggerLock != null ? !triggerLock.equals(that.triggerLock) : that.triggerLock != null) {
            return false;
        }
        return delaySyncMarker != null ? delaySyncMarker.equals(that.delaySyncMarker) : that.delaySyncMarker == null;
    }

    @Override
    public int hashCode() {
        int result = bucketKey.hashCode();
        result = 31 * result + strategy.hashCode();
        result = 31 * result + strategyContextConfig.hashCode();
        result = 31 * result + (triggerLock != null ? triggerLock.hashCode() : 0);
        result = 31 * result + (delaySyncMarker != null ? delaySyncMarker.hashCode() : 0);
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
