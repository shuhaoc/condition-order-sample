package me.caosh.condition.domain.container;

import hbec.intellitrade.strategy.domain.container.BucketKey;

/**
 * 时间驱动策略的bucket key，用于纯时间驱动的策略
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2018/2/1
 */
public enum TimeDrivenBucketKey implements BucketKey {
    /**
     * 单例
     */
    INSTANCE;
}
