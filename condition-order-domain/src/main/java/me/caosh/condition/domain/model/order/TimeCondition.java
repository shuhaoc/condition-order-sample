package me.caosh.condition.domain.model.order;

/**
 * Created by caosh on 2017/8/20.
 */
public interface TimeCondition extends Condition {
    boolean isSatisfiedNow();
}
