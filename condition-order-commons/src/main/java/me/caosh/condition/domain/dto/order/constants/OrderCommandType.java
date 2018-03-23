package me.caosh.condition.domain.dto.order.constants;

import me.caosh.autoasm.ConvertibleEnum;

/**
 * Created by caosh on 2017/8/13.
 */
public enum OrderCommandType implements ConvertibleEnum<Integer> {
    SAVE(1),
    REMOVE(2);

    int value;

    OrderCommandType(int value) {
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return value;
    }
}
