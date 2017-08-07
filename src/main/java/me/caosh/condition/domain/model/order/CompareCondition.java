package me.caosh.condition.domain.model.order;

import me.caosh.condition.domain.model.share.ValuedEnum;

/**
 * Created by caosh on 2017/8/2.
 */
public enum CompareCondition implements ValuedEnum<Integer> {
    GREATER_THAN_OR_EQUALS(1),
    LESS_THAN_OR_EQUALS(2);

    int value;

    CompareCondition(int value) {
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return value;
    }
}
