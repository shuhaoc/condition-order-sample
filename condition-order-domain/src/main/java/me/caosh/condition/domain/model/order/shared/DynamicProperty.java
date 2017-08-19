package me.caosh.condition.domain.model.order.shared;

import java.util.Optional;

/**
 * Created by caosh on 2017/8/19.
 */
public class DynamicProperty<T> {
    private T value;
    private boolean dirty = false;

    public DynamicProperty() {
    }

    public DynamicProperty(T value) {
        this.value = value;
    }

    public T get() {
        return value;
    }

    public void set(T value) {
        this.value = value;
        this.dirty = true;
    }

    public boolean isDirty() {
        return dirty;
    }

    public void clearDirty() {
        this.dirty = false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DynamicProperty)) return false;

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
