package hbec.intellitrade.common.security;

import me.caosh.autoasm.ConvertibleEnum;

/**
 * Created by caosh on 2017/8/1.
 */
public enum SecurityType implements ConvertibleEnum<Integer> {
    /**
     * 股票
     */
    STOCK(4),

    /**
     * 场内基金
     */
    FUND(3),

    /**
     * 债券
     */
    BOND(7);

    int value;

    SecurityType(int value) {
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return value;
    }
}
