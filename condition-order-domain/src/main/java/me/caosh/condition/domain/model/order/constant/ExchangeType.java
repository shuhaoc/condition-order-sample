package me.caosh.condition.domain.model.order.constant;

import me.caosh.condition.domain.model.share.ValuedEnum;

/**
 * Created by caosh on 2017/8/2.
 */
public enum ExchangeType implements ValuedEnum<Integer> {
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
