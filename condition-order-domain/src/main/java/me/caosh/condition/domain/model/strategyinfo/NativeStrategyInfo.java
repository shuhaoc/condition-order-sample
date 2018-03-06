package me.caosh.condition.domain.model.strategyinfo;

import me.caosh.autoasm.ConvertibleEnum;
import me.caosh.condition.domain.model.share.ValuedEnum;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2017/8/1
 */
public enum NativeStrategyInfo implements StrategyInfo, ValuedEnum<Integer>, ConvertibleEnum<Integer> {
    PRICE(1),
    TIME(4),
    TURN_UP(7),
    GRID(9),
    NEW_STOCK(10);

    private final int strategyType;

    NativeStrategyInfo(int strategyType) {
        this.strategyType = strategyType;
    }

    @Override
    public StrategySystemType getSystemType() {
        return StrategySystemType.NATIVE;
    }

    @Override
    public int getStrategyType() {
        return strategyType;
    }

    @Override
    public Integer getValue() {
        return strategyType;
    }
}
