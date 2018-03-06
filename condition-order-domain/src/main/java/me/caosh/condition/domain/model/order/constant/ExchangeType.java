package me.caosh.condition.domain.model.order.constant;

import me.caosh.autoasm.ConvertibleEnum;
import hbec.intellitrade.common.ValuedEnum;

/**
 * Created by caosh on 2017/8/2.
 */
public enum ExchangeType implements ValuedEnum<Integer>, ConvertibleEnum<Integer> {
    BUY(1),
    SELL(2),
    QUOTA_PURCHASE(14);

    int value;

    ExchangeType(int value) {
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return value;
    }
}
