package me.caosh.condition.domain.model.order.constant;

import me.caosh.condition.domain.model.share.ValuedEnum;

/**
 * Created by caosh on 2017/8/6.
 */
public enum OrderState implements ValuedEnum<Integer> {
    ACTIVE(1),
    PAUSED(2),
    TERMINATED(3);

    int value;

    OrderState(int value) {
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return value;
    }
}
