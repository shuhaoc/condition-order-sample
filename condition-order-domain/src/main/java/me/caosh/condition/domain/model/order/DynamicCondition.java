package me.caosh.condition.domain.model.order;

/**
 * Created by caosh on 2017/8/19.
 */
public interface DynamicCondition extends Condition {
    boolean isDirty();

    void clearDirty();

    void swap(DynamicCondition another);
}
