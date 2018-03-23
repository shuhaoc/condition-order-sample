package hbec.intellitrade.condorder.domain.orders.price;

import hbec.intellitrade.condorder.domain.orders.ConditionOrderBuilder;
import hbec.intellitrade.condorder.domain.strategyinfo.NativeStrategyInfo;

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