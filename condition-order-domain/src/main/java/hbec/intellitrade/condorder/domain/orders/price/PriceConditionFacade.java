package hbec.intellitrade.condorder.domain.orders.price;

import hbec.intellitrade.common.market.RealTimeMarket;
import hbec.intellitrade.condorder.domain.orders.MarketConditionFacade;
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
 * 价格条件Facade
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2018/3/20
 */
public class PriceConditionFacade implements MarketConditionFacade, DynamicCondition {
    private final PriceCondition priceCondition;
    private final DelayConfirm delayConfirm;
    private final DeviationCtrl deviationCtrl;
    private final MarketCondition decoratedCondition;

    public PriceConditionFacade(PriceCondition priceCondition,
                                DelayConfirm delayConfirm,
                                DeviationCtrl deviationCtrl,
                                int delayConfirmedCount) {
        this.priceCondition = priceCondition;
        this.delayConfirm = delayConfirm;
        this.deviationCtrl = deviationCtrl;

        PredictableMarketCondition deviationCtrlWrappedCondition = DeviationCtrlConditionFactory.INSTANCE.wrapWith(
                priceCondition,
                deviationCtrl);

        this.decoratedCondition = DelayConfirmConditionFactory.INSTANCE.wrapWith(
                deviationCtrlWrappedCondition,
                delayConfirm,
                delayConfirmedCount);
    }

    public CompareOperator getCompareOperator() {
        return priceCondition.getCompareOperator();
    }

    public BigDecimal getTargetPrice() {
        return priceCondition.getTargetPrice();
    }

    public DelayConfirm getDelayConfirm() {
        return delayConfirm;
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
            return ((DynamicCondition) decoratedCondition).isDirty();
        }
        return false;
    }

    @Override
    public void clearDirty() {
        if (decoratedCondition instanceof DelayConfirmCondition) {
            ((DynamicCondition) decoratedCondition).clearDirty();
        }
    }

    public int getDelayConfirmedCount() {
        if (decoratedCondition instanceof DelayConfirmCondition) {
            return ((DelayConfirmCondition) decoratedCondition).getDelayConfirmedCount();
        }
        return 0;
    }

    public void resetCounter() {
        if (decoratedCondition instanceof DelayConfirmCondition) {
            ((DelayConfirmCondition) decoratedCondition).resetCounter();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PriceConditionFacade that = (PriceConditionFacade) o;

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
