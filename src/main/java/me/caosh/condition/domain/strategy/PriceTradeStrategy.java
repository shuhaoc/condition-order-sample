package me.caosh.condition.domain.strategy;

/**
 * Created by caosh on 2017/8/1.
 */
public class PriceTradeStrategy extends AbstractNativeTradeStrategy {
    @Override
    public Integer getStrategyId() {
        return NativeTradeStrategyIDs.PRICE;
    }

    @Override
    public String getName() {
        return "价格条件";
    }

//    public CheckResult onCheck() {
//
//    }
}
