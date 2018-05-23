package hbec.intellitrade.conditionorder.domain.orders.grid;

import hbec.intellitrade.conditionorder.domain.orders.ConditionOrderBuilder;
import hbec.intellitrade.conditionorder.domain.strategyinfo.NativeStrategyInfo;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/5/2
 */
public class GridTradeOrderBuilder extends ConditionOrderBuilder {
    public GridTradeOrderBuilder() {
        getStrategyInfo().setStrategyType(NativeStrategyInfo.GRID);
        setCondition(new DecoratedGridConditionBuilder());
    }

    @Override
    public GridTradeOrder build() {
        return (GridTradeOrder) super.build();
    }
}
