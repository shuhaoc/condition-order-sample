package hbec.intellitrade.condorder.domain.orders.price;

import com.google.common.base.Optional;
import hbec.intellitrade.common.market.RealTimeMarket;
import hbec.intellitrade.condorder.domain.delayconfirm.count.SingleDelayConfirmCount;
import hbec.intellitrade.strategy.domain.condition.DynamicCondition;
import hbec.intellitrade.strategy.domain.condition.delayconfirm.DelayConfirm;
import hbec.intellitrade.strategy.domain.condition.delayconfirm.DelayConfirmCondition;
import hbec.intellitrade.strategy.domain.condition.delayconfirm.DelayConfirmConditionFactory;
import hbec.intellitrade.strategy.domain.condition.deviation.DeviationCtrl;
import hbec.intellitrade.strategy.domain.condition.deviation.DeviationCtrlConditionFactory;
import hbec.intellitrade.strategy.domain.condition.market.MarketCondition;
import hbec.intellitrade.strategy.domain.condition.market.PredictableMarketCondition;
import hbec.intellitrade.strategy.domain.factor.CompareOperator;
import hbec.intellitrade.strategy.domain.signal.TradeSignal;

import java.math.BigDecimal;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/3/20
 */
public class DecoratedPriceCondition implements MarketCondition, DynamicCondition {
    private final PriceCondition rawPriceCondition;
    private final DelayConfirm delayConfirm;
    // TODO: refactor

    private final SingleDelayConfirmCount singleDelayConfirmCount;
    private final DeviationCtrl deviationCtrl;
    private final MarketCondition decoratedCondition;

    public DecoratedPriceCondition(PriceCondition rawPriceCondition,
                                   DelayConfirm delayConfirm,
                                   SingleDelayConfirmCount singleDelayConfirmCount,
                                   DeviationCtrl deviationCtrl) {
        this.rawPriceCondition = rawPriceCondition;
        this.delayConfirm = delayConfirm;
        this.singleDelayConfirmCount = singleDelayConfirmCount;
        this.deviationCtrl = deviationCtrl;

        PredictableMarketCondition deviationCtrlWrappedCondition = DeviationCtrlConditionFactory.INSTANCE.wrap(
                rawPriceCondition,
                deviationCtrl);

        int confirmedCount = singleDelayConfirmCount != null ? singleDelayConfirmCount.getConfirmedCount() : 0;
        this.decoratedCondition = DelayConfirmConditionFactory.INSTANCE.wrapWith(
                deviationCtrlWrappedCondition,
                delayConfirm,
                confirmedCount);

    }

    public CompareOperator getCompareOperator() {
        return rawPriceCondition.getCompareOperator();
    }

    public BigDecimal getTargetPrice() {
        return rawPriceCondition.getTargetPrice();
    }

    public DelayConfirm getDelayConfirm() {
        return delayConfirm;
    }

    public SingleDelayConfirmCount getSingleDelayConfirmCount() {
        return singleDelayConfirmCount;
    }

    public DeviationCtrl getDeviationCtrl() {
        return deviationCtrl;
    }

    @Override
    public TradeSignal onMarketTick(RealTimeMarket realTimeMarket) {
        return decoratedCondition.onMarketTick(realTimeMarket);
    }

    @Override
    public boolean isDirty() {
        // 仅延迟确认条件会产生脏标志
        if (decoratedCondition instanceof DelayConfirmCondition) {
            return ((DelayConfirmCondition) decoratedCondition).isDirty();
        }
        return false;
    }

    @Override
    public void clearDirty() {
        ((DynamicCondition) decoratedCondition).clearDirty();
    }

    public void reset() {
        if (decoratedCondition instanceof DelayConfirmCondition) {
            ((DelayConfirmCondition) decoratedCondition).resetCounter();
        }
    }

    public Optional<SingleDelayConfirmCount> getDelayConfirmCount() {
        if (decoratedCondition instanceof DelayConfirmCondition) {
            int confirmedCount = ((DelayConfirmCondition) decoratedCondition).getConfirmedCount();
            return Optional.of(new SingleDelayConfirmCount(confirmedCount));
        }
        return Optional.absent();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DecoratedPriceCondition that = (DecoratedPriceCondition) o;

        return decoratedCondition.equals(that.decoratedCondition);
    }

    @Override
    public int hashCode() {
        return decoratedCondition.hashCode();
    }

    @Override
    public String toString() {
        return decoratedCondition.toString();
    }
}
