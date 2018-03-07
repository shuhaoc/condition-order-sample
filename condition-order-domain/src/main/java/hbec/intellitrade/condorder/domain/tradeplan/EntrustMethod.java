package hbec.intellitrade.condorder.domain.tradeplan;

import me.caosh.autoasm.ConvertibleEnum;
import hbec.intellitrade.common.ValuedEnum;

/**
 * Created by caosh on 2017/8/18.
 */
public enum EntrustMethod implements ValuedEnum<Integer>, ConvertibleEnum<Integer> {
    NUMBER(0),
    AMOUNT(1);

    int value;

    EntrustMethod(int value) {
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return value;
    }
}
