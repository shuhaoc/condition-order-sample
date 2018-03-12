package hbec.intellitrade.strategy.domain;

import hbec.intellitrade.strategy.domain.shared.DirtyFlag;

/**
 * 易变的策略，指含有动态属性的策略
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2018/3/12
 */
public interface MutableStrategy extends Strategy, DirtyFlag {
    /**
     * 是否存在有变化的需持久化的字段
     *
     * @return 存在有变化的需持久化的字段
     */
    boolean isPersistentPropertyDirty();
}
