package me.caosh.condition.domain.model.constants;

import me.caosh.condition.domain.model.share.ValuedEnum;

/**
 * Created by caosh on 2017/8/18.
 */
public enum EntrustMethod implements ValuedEnum<Integer> {
    NUMBER(0),
    AMOUNT(1);

    int value;

    EntrustMethod(int value) {
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return value;
    }
}
