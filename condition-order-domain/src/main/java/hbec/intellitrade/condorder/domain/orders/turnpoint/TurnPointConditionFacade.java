package hbec.intellitrade.condorder.domain.orders.turnpoint;

import hbec.intellitrade.strategy.domain.condition.DynamicCondition;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/3/21
 */
public class TurnPointConditionFacade implements DynamicCondition {
    private final TurnPointCondition basicTurnPointCondition;
    private final CrossBaselineCondition crossBaselineCondition;

    public TurnPointConditionFacade(TurnPointCondition turnPointCondition,
                                    CrossBaselineCondition crossBaselineCondition) {
        this.basicTurnPointCondition = turnPointCondition;
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
