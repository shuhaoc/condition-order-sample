package hbec.intellitrade.condorder.domain;

import hbec.intellitrade.common.ValuedEnum;
import me.caosh.autoasm.ConvertibleEnum;

/**
 * Created by caosh on 2017/8/6.
 */
public enum OrderState implements ValuedEnum<Integer>, ConvertibleEnum<Integer> {
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
