package me.caosh.condition.domain.model.strategy;

import me.caosh.condition.domain.model.share.ValuedEnum;

/**
 * Created by caosh on 2017/8/1.
 */
public enum NativeStrategyInfo implements StrategyInfo, ValuedEnum<Integer> {
    PRICE(1),
    TURN_UP(2);

    private final int strategyId;

    NativeStrategyInfo(int strategyId) {
        this.strategyId = strategyId;
    }

    @Override
    public SystemType getSystemType() {
        return SystemType.NATIVE;
    }

    @Override
    public int getStrategyId() {
        return strategyId;
    }

    @Override
    public Integer getValue() {
        return strategyId;
    }
}
