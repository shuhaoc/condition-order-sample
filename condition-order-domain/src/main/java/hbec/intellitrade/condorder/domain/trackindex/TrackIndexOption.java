package hbec.intellitrade.condorder.domain.trackindex;

import me.caosh.autoasm.ConvertibleEnum;

/**
 * @author caoshuhao@touker.com
 * @date 2018/3/17
 */
public enum TrackIndexOption implements ConvertibleEnum<Integer> {
    /**
     * 禁用
     */
    DISABLED(0),

    /**
     * 启用
     */
    ENABLED(1);

    int value;

    TrackIndexOption(int value) {
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return value;
    }
}
