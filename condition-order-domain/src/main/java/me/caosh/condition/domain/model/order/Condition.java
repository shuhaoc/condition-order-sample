package me.caosh.condition.domain.model.order;

/**
 * Created by caosh on 2017/8/6.
 */
public interface Condition {
    void accept(ConditionVisitor visitor);
}
