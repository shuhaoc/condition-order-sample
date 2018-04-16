package hbec.intellitrade.conditionorder.domain;

import me.caosh.autoasm.ConvertibleEnum;

/**
 * Created by caosh on 2017/8/6.
 */
public enum OrderState implements ConvertibleEnum<Integer> {
    ACTIVE(1),
    PAUSED(2),
    TERMINATED(3),
    EXPIRED(4);

    int value;

    OrderState(int value) {
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return value;
    }
}
