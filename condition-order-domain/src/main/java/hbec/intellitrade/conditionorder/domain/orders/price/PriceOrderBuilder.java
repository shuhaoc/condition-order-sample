package hbec.intellitrade.conditionorder.domain.orders.price;

import hbec.intellitrade.conditionorder.domain.orders.ConditionOrderBuilder;
import hbec.intellitrade.conditionorder.domain.strategyinfo.NativeStrategyInfo;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/2/4
 */
public class PriceOrderBuilder extends ConditionOrderBuilder {
    public PriceOrderBuilder() {
        getStrategyInfo().setStrategyType(NativeStrategyInfo.PRICE);
        setCondition(new DecoratedPriceConditionBuilder());
    }

    @Override
    public PriceOrder build() {
        return (PriceOrder) super.build();
    }
}