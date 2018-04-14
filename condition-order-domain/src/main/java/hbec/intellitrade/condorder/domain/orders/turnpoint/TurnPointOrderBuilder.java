package hbec.intellitrade.condorder.domain.orders.turnpoint;

import hbec.intellitrade.condorder.domain.orders.ConditionOrderBuilder;
import hbec.intellitrade.condorder.domain.strategyinfo.NativeStrategyInfo;

/**
 * @author caoshuhao@touker.com
 * @date 2018/4/14
 */
public class TurnPointOrderBuilder extends ConditionOrderBuilder {
    public TurnPointOrderBuilder() {
        getStrategyInfo().setStrategyType(NativeStrategyInfo.TURN_POINT);
        setCondition(new DecoratedTurnPointConditionBuilder());
    }

    @Override
    public TurnPointOrder build() {
        return (TurnPointOrder) super.build();
    }
}
