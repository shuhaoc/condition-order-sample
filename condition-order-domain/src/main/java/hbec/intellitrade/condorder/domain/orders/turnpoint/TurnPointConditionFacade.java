package hbec.intellitrade.condorder.domain.orders.turnpoint;

import com.google.common.base.MoreObjects;
import hbec.intellitrade.common.market.RealTimeMarket;
import hbec.intellitrade.strategy.domain.condition.DynamicCondition;
import hbec.intellitrade.strategy.domain.condition.delayconfirm.DelayConfirm;
import hbec.intellitrade.strategy.domain.condition.delayconfirm.DelayConfirmCondition;
import hbec.intellitrade.strategy.domain.condition.delayconfirm.DelayConfirmConditionFactory;
import hbec.intellitrade.strategy.domain.condition.deviation.DeviationCtrl;
import hbec.intellitrade.strategy.domain.condition.deviation.DeviationCtrlConditionFactory;
import hbec.intellitrade.strategy.domain.condition.market.MarketCondition;
import hbec.intellitrade.strategy.domain.condition.market.PredictableMarketCondition;
import hbec.intellitrade.strategy.domain.factor.CompareOperator;
import hbec.intellitrade.strategy.domain.factor.CompareOperators;
import hbec.intellitrade.strategy.domain.signal.TradeSignal;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/3/21
 */
public class TurnPointConditionFacade implements MarketCondition, DelayConfirmCondition, DynamicCondition {
    private final TurnPointCondition turnPointCondition;
    private final CrossBaselineCondition crossBaselineCondition;
    private final DelayConfirm delayConfirm;
    private final DeviationCtrl deviationCtrl;
    private final MarketCondition decoratedBasicCondition;
    private final MarketCondition decoratedCrossCondition;

    public TurnPointConditionFacade(TurnPointCondition turnPointCondition,
                                    BigDecimal baselinePrice,
                                    DelayConfirm delayConfirm,
                                    DeviationCtrl deviationCtrl,
                                    int basicDelayConfirmedCount,
                                    int crossDelayConfirmedCount) {
        this.turnPointCondition = turnPointCondition;

        // 踩线条件是可选的
        if (baselinePrice != null) {
            // 穿越底线条件的价格方向，与拐点条件的突破条件方向相同，但是价格等于底线价时不满足条件
            CompareOperator compareOperator = turnPointCondition.getTargetPriceFactor().getCompareOperator();
            this.crossBaselineCondition = new CrossBaselineCondition(CompareOperators.nonEquals(compareOperator),
                                                                     baselinePrice);
        } else {
            crossBaselineCondition = null;
        }

        this.delayConfirm = delayConfirm;
        this.deviationCtrl = deviationCtrl;

        // 基本拐点条件支持偏差控制
        PredictableMarketCondition deviationCtrlWrappedBasicCondition = DeviationCtrlConditionFactory.INSTANCE.wrapWith(
                turnPointCondition,
                deviationCtrl);

        // 基本拐点条件支持延迟确认
        decoratedBasicCondition = DelayConfirmConditionFactory.INSTANCE.wrapWith(
                deviationCtrlWrappedBasicCondition,
                delayConfirm,
                basicDelayConfirmedCount);

        if (crossBaselineCondition != null) {
            // 穿越底线条件仅支持延迟确认
            decoratedCrossCondition = DelayConfirmConditionFactory.INSTANCE.wrapWith(
                    crossBaselineCondition,
                    delayConfirm,
                    crossDelayConfirmedCount);
        } else {
            decoratedCrossCondition = null;
        }
    }

    @Override
    public TradeSignal onMarketTick(RealTimeMarket realTimeMarket) {
        if (decoratedCrossCondition != null) {
            TradeSignal crossSignal = decoratedCrossCondition.onMarketTick(realTimeMarket);
            if (crossSignal.isValid()) {
                return crossSignal;
            }
        }

        return decoratedBasicCondition.onMarketTick(realTimeMarket);
    }

    @Override
    public boolean isDirty() {
        if (turnPointCondition.isDirty()) {
            return turnPointCondition.isDirty();
        }

        if (decoratedBasicCondition instanceof DelayConfirmCondition) {
            return ((DelayConfirmCondition) decoratedBasicCondition).isDirty();
        }

        if (decoratedCrossCondition instanceof DelayConfirmCondition) {
            return ((DelayConfirmCondition) decoratedCrossCondition).isDirty();
        }

        return false;
    }

    @Override
    public void clearDirty() {
        turnPointCondition.clearDirty();

        if (decoratedBasicCondition instanceof DelayConfirmCondition) {
            ((DelayConfirmCondition) decoratedBasicCondition).clearDirty();
        }

        if (decoratedCrossCondition instanceof DelayConfirmCondition) {
            ((DelayConfirmCondition) decoratedCrossCondition).clearDirty();
        }
    }

    @Override
    public int getDelayConfirmedCount() {
        return 0;
    }

    @Override
    public void resetCounter() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TurnPointConditionFacade that = (TurnPointConditionFacade) o;
        return Objects.equals(decoratedBasicCondition, that.decoratedBasicCondition) &&
               Objects.equals(decoratedCrossCondition, that.decoratedCrossCondition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(decoratedBasicCondition, decoratedCrossCondition);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(TurnPointConditionFacade.class).omitNullValues()
                          .add("decoratedBasicCondition", decoratedBasicCondition)
                          .add("decoratedCrossCondition", decoratedCrossCondition)
                          .toString();
    }
}
