package hbec.intellitrade.trade.domain;

import me.caosh.autoasm.ConvertibleEnum;

/**
 * Created by caosh on 2017/8/14.
 */
public enum OrderType implements ConvertibleEnum<Integer> {
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
