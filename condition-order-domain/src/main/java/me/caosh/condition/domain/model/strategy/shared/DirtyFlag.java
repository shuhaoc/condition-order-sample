package me.caosh.condition.domain.model.strategy.shared;

/**
 * 脏标志接口
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2018/1/30
 */
public interface DirtyFlag {
    /**
     * 脏标志，即是否存在有变化的属性
     *
     * @return 是否存在有变化的属性
     */
    boolean isDirty();

    /**
     * 清除脏标志，一般用于动态属性已经持久化之后调用
     */
    void clearDirty();
}
