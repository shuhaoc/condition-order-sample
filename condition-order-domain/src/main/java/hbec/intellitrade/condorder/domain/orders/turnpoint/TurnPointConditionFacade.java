package hbec.intellitrade.condorder.domain.orders.turnpoint;

import hbec.intellitrade.strategy.domain.condition.DynamicCondition;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/3/21
 */
public class TurnPointConditionFacade implements DynamicCondition {
    private final BasicTurnPointCondition basicTurnPointCondition;
    private final CrossBaselineCondition crossBaselineCondition;

    public TurnPointConditionFacade(BasicTurnPointCondition basicTurnPointCondition,
                                    CrossBaselineCondition crossBaselineCondition) {
        this.basicTurnPointCondition = basicTurnPointCondition;
        this.crossBaselineCondition = crossBaselineCondition;
    }

    @Override
    public boolean isDirty() {
        return false;
    }

    @Override
    public void clearDirty() {

    }
}
