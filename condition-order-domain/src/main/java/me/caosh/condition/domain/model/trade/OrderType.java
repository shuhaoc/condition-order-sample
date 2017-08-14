package me.caosh.condition.domain.model.trade;

import me.caosh.condition.domain.model.share.ValuedEnum;

/**
 * Created by caosh on 2017/8/14.
 */
public enum  OrderType implements ValuedEnum<Integer> {
    LIMITED(0);

    int value;

    OrderType(int value) {
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return value;
    }
}
