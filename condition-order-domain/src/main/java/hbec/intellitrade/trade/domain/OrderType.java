package hbec.intellitrade.trade.domain;

import hbec.intellitrade.common.ValuedEnum;

/**
 * Created by caosh on 2017/8/14.
 */
public enum OrderType implements ValuedEnum<Integer> {
    LIMITED(0);

    int value;

    OrderType(int value) {
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return value;
    }
}
