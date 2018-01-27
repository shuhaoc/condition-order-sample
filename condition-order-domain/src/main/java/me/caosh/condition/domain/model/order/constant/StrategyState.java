package me.caosh.condition.domain.model.order.constant;

import me.caosh.autoasm.ConvertibleEnum;
import me.caosh.condition.domain.model.share.ValuedEnum;

/**
 * Created by caosh on 2017/8/6.
 */
public enum StrategyState implements ValuedEnum<Integer>, ConvertibleEnum<Integer> {
    ACTIVE(1),
    PAUSED(2),
    TERMINATED(3);

    int value;

    StrategyState(int value) {
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return value;
    }
}
