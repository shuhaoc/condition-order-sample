package me.caosh.condition.domain.strategy;

/**
 * Created by caosh on 2017/8/1.
 */
public abstract class AbstractNativeTradeStrategy implements TradeStrategy {
    @Override
    public SystemType getSystemType() {
        return SystemType.NATIVE;
    }

}
