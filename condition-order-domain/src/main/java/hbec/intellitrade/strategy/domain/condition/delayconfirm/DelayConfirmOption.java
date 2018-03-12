package hbec.intellitrade.strategy.domain.condition.delayconfirm;

import me.caosh.autoasm.ConvertibleEnum;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/3/8
 */
public enum DelayConfirmOption implements ConvertibleEnum<Integer> {
    /**
     * 禁用
     */
    DISABLED(0),

    /**
     * 累计确认
     */
    ACCUMULATE(1),

    /**
     * 连续确认
     */
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
