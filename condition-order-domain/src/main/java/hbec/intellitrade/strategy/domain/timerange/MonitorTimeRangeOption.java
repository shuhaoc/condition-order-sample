package hbec.intellitrade.strategy.domain.timerange;

import me.caosh.autoasm.ConvertibleEnum;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/3/15
 */
public enum MonitorTimeRangeOption implements ConvertibleEnum<Integer> {
    /**
     * 禁用
     */
    DISABLED(0),

    /**
     * 启用
     */
    ENABLED(1);

    int value;

    MonitorTimeRangeOption(int value) {
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return value;
    }
}
