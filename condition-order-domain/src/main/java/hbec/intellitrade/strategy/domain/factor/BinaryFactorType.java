package hbec.intellitrade.strategy.domain.factor;

import me.caosh.autoasm.ConvertibleEnum;

/**
 * 二元因子类别，包括百分比、增量（差价）
 *
 * @author caoshuhao@touker.com
 * @date 2018/3/24
 */
public enum BinaryFactorType implements ConvertibleEnum<Integer> {
    /**
     * 百分比
     */
    PERCENT(0),

    /**
     * 增量
     */
    INCREMENT(1);

    int value;

    BinaryFactorType(int value) {
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return value;
    }
}
