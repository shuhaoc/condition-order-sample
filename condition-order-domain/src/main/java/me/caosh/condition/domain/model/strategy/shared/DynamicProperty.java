package me.caosh.condition.domain.model.strategy.shared;

/**
 * 动态属性代理
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2018/1/30
 */
public class DynamicProperty<T> implements DirtyFlag {
    private T value;
    private transient boolean dirty = false;

    /**
     * 构造值为null的动态属性，初始脏标志为false
     */
    public DynamicProperty() {
    }

    /**
     * 使用传入的初始值构造动态属性，初始脏标志为false
     *
     * @param value 初始值
     */
    public DynamicProperty(T value) {
        this.value = value;
    }

    /**
     * 获取属性值，是否可能为空视使用情况而定
     *
     * @return 属性值
     */
    public T get() {
        return value;
    }

    /**
     * 设置属性值，并设置脏标志为true
     *
     * @param value 属性值
     */
    public void set(T value) {
        this.value = value;
        this.dirty = true;
    }

    @Override
    public boolean isDirty() {
        return dirty;
    }

    @Override
    public void clearDirty() {
        this.dirty = false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DynamicProperty)) {
            return false;
        }

        DynamicProperty<?> that = (DynamicProperty<?>) o;

        return !(value != null ? !value.equals(that.value) : that.value != null);

    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "DynamicProperty(" + value + ")" + (dirty ? "[dirty]" : "");
    }
}
