package hbec.intellitrade.condorder.domain.orders.turnpoint;

import hbec.intellitrade.strategy.domain.condition.DynamicCondition;
import hbec.intellitrade.strategy.domain.condition.delayconfirm.DelayConfirm;
import hbec.intellitrade.strategy.domain.condition.delayconfirm.DelayConfirmConditionFactory;
import hbec.intellitrade.strategy.domain.condition.deviation.DeviationCtrl;
import hbec.intellitrade.strategy.domain.condition.deviation.DeviationCtrlConditionFactory;
import hbec.intellitrade.strategy.domain.condition.market.MarketCondition;
import hbec.intellitrade.strategy.domain.condition.market.PredictableMarketCondition;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/3/21
 */
public class TurnPointConditionFacade implements DynamicCondition {
    private final TurnPointCondition turnPointCondition;
    private final CrossBaselineCondition crossBaselineCondition;
    private final DelayConfirm delayConfirm;
    private final DeviationCtrl deviationCtrl;
    private final MarketCondition decoratedCondition;

    public TurnPointConditionFacade(TurnPointCondition turnPointCondition,
                                    CrossBaselineCondition crossBaselineCondition,
                                    DelayConfirm delayConfirm,
                                    DeviationCtrl deviationCtrl,
                                    int basicDelayConfirmedCount,
                                    int crossDelayConfirmedCount) {
        this.turnPointCondition = turnPointCondition;
        this.crossBaselineCondition = crossBaselineCondition;
        this.delayConfirm = delayConfirm;
        this.deviationCtrl = deviationCtrl;

        // 基本拐点条件支持偏差控制
        PredictableMarketCondition deviationCtrlWrappedBasicCondition = DeviationCtrlConditionFactory.INSTANCE.wrapWith(
                turnPointCondition,
                deviationCtrl);

        // 基本拐点条件支持延迟确认
        MarketCondition delayConfirmWrappedBasicCondition = DelayConfirmConditionFactory.INSTANCE.wrapWith(
                deviationCtrlWrappedBasicCondition,
                delayConfirm,
                basicDelayConfirmedCount);

        // 穿越底线条件仅支持延迟确认
        MarketCondition delayConfirmWrappedCrossCondition = DelayConfirmConditionFactory.INSTANCE.wrapWith(
                crossBaselineCondition,
                delayConfirm,
                crossDelayConfirmedCount);

        this.decoratedCondition = delayConfirmWrappedCrossCondition;
    }

    @Override
    public boolean isDirty() {
        return false;
    }

    @Override
    public void clearDirty() {

    }
}
