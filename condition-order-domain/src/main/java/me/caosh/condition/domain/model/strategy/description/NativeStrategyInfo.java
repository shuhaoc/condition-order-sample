package me.caosh.condition.domain.model.strategy.description;

import me.caosh.autoasm.ConvertibleEnum;
import me.caosh.condition.domain.model.share.ValuedEnum;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2017/8/1
 */
public enum NativeStrategyInfo implements StrategyInfo, ValuedEnum<Integer>, ConvertibleEnum<Integer> {
    PRICE(1),
    TURN_UP(2),
    TIME(4),
    GRID(5),
    NEW_STOCK(6);

    private final int strategyId;

    NativeStrategyInfo(int strategyId) {
        this.strategyId = strategyId;
    }

    @Override
    public StrategySystemType getSystemType() {
        return StrategySystemType.NATIVE;
    }

    @Override
    public int getStrategyTemplateId() {
        return strategyId;
    }

    @Override
    public Integer getValue() {
        return strategyId;
    }
}
