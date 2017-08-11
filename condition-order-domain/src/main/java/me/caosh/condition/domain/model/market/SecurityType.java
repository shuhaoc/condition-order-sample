package me.caosh.condition.domain.model.market;

import me.caosh.condition.domain.model.share.ValuedEnum;

/**
 * Created by caosh on 2017/8/1.
 */
public enum SecurityType implements ValuedEnum<Integer> {
    STOCK(1),
    FUND(2);

    int value;

    SecurityType(int value) {
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return value;
    }
}
