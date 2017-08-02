package me.caosh.condition.domain.model.strategy;

/**
 * Created by caosh on 2017/8/1.
 */
public interface TradeStrategy {
    SystemType getSystemType();

    Integer getStrategyId();

    String getName();
}
