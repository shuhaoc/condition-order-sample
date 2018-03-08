package me.caosh.condition.domain.model.strategy.condition.delayconfirm;

import me.caosh.autoasm.ConvertibleEnum;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/3/8
 */
public enum DelayConfirmOption implements ConvertibleEnum<Integer> {
    DISABLED(0),

    ACCUMULATE(1),

    CONTINUOUS(2);

    private final int value;

    DelayConfirmOption(int value) {
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return value;
    }
}
