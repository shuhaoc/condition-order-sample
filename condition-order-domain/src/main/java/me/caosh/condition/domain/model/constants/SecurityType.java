package me.caosh.condition.domain.model.constants;

import me.caosh.autoasm.ConvertibleEnum;
import me.caosh.condition.domain.model.share.ValuedEnum;

/**
 * Created by caosh on 2017/8/1.
 */
public enum SecurityType implements ValuedEnum<Integer>, ConvertibleEnum<Integer> {
    STOCK(4),
    FUND(3),
    INDEX(5);

    int value;

    SecurityType(int value) {
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return value;
    }
}
