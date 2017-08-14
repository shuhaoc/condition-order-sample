package me.caosh.condition.domain.model.strategy;

/**
 * Created by caosh on 2017/8/1.
 */
public interface StrategyInfo {
    SystemType getSystemType();

    int getStrategyId();

    LifeCircle getLifeCircle();
}
