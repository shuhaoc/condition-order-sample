package me.caosh.condition.domain.model.order.constant;

import me.caosh.condition.domain.model.share.ValuedEnum;

/**
 * Created by caosh on 2017/8/2.
 */
public enum EntrustStrategy implements ValuedEnum<Integer> {
    CURRENT_PRICE(1),
    SELL5(2),
    SELL4(3),
    SELL3(4),
    SELL2(5),
    SELL1(6),
    BUY1(7),
    BUY2(8),
    BUY3(9),
    BUY4(10),
    BUY5(11);

    int value;

    EntrustStrategy(int value) {
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return value;
    }
}
