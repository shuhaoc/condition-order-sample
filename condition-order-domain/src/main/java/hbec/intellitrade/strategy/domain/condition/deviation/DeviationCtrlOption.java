package hbec.intellitrade.strategy.domain.condition.deviation;

import me.caosh.autoasm.ConvertibleEnum;

/**
 * 偏差控制选项
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2018/3/16
 */
public enum DeviationCtrlOption implements ConvertibleEnum<Integer> {
    /**
     * 禁用
     */
    DISABLED(0),

    /**
     * 启用
     */
    ENABLED(1);

    int value;

    DeviationCtrlOption(int value) {
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return value;
    }
}
