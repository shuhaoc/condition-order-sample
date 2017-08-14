package me.caosh.condition.domain.model.constants;

import me.caosh.condition.domain.model.share.ValuedEnum;

/**
 * Created by caosh on 2017/8/13.
 */
public enum OrderCommandType implements ValuedEnum<Integer> {
    CREATE(1),
    UPDATE(2),
    DELETE(3);

    int value;

    OrderCommandType(int value) {
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return value;
    }
}
