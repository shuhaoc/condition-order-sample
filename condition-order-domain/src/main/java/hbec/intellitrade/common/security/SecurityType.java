package hbec.intellitrade.common.security;

import me.caosh.autoasm.ConvertibleEnum;

/**
 * Created by caosh on 2017/8/1.
 */
public enum SecurityType implements ConvertibleEnum<Integer> {
    STOCK(4),
    FUND(3);

    int value;

    SecurityType(int value) {
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return value;
    }
}
