package hbec.intellitrade.trade.domain;

import me.caosh.autoasm.ConvertibleEnum;

/**
 * Created by caosh on 2017/8/2.
 */
public enum ExchangeType implements ConvertibleEnum<Integer> {
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
