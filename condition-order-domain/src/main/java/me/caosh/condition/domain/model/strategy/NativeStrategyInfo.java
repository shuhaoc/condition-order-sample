package me.caosh.condition.domain.model.strategy;

import me.caosh.condition.domain.model.share.ValuedEnum;

import static me.caosh.condition.domain.model.strategy.LifeCircle.ONCE;

/**
 * Created by caosh on 2017/8/1.
 */
public enum NativeStrategyInfo implements StrategyInfo, ValuedEnum<Integer> {
    PRICE(1, ONCE),
    TURN_UP(2, ONCE),
    TIME(4, ONCE);

    private final int strategyId;
    private final LifeCircle lifeCircle;

    NativeStrategyInfo(int strategyId, LifeCircle lifeCircle) {
        this.strategyId = strategyId;
        this.lifeCircle = lifeCircle;
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
    public LifeCircle getLifeCircle() {
        return lifeCircle;
    }

    @Override
    public Integer getValue() {
        return strategyId;
    }
}
