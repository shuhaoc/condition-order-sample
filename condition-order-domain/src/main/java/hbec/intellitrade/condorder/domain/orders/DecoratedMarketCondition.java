package hbec.intellitrade.condorder.domain.orders;

import hbec.intellitrade.common.market.RealTimeMarket;
import hbec.intellitrade.strategy.domain.condition.DynamicCondition;
import hbec.intellitrade.strategy.domain.condition.delayconfirm.DelayConfirm;
import hbec.intellitrade.strategy.domain.condition.delayconfirm.DelayConfirmCondition;
import hbec.intellitrade.strategy.domain.condition.delayconfirm.DelayConfirmConditionFactory;
import hbec.intellitrade.strategy.domain.condition.deviation.DeviationCtrl;
import hbec.intellitrade.strategy.domain.condition.deviation.DeviationCtrlConditionFactory;
import hbec.intellitrade.strategy.domain.condition.market.MarketCondition;
import hbec.intellitrade.strategy.domain.condition.market.PredictableMarketCondition;
import hbec.intellitrade.strategy.domain.signal.TradeSignal;

import java.util.Objects;

/**
 * 行情类条件包装类（Decorator模式），内部集成了延迟确认、偏差控制等装饰条件，对外开放动态条件、延迟确认次数查询与重置等功能
 *
 * @author caoshuhao@touker.com
 * @date 2018/3/22
 */
public class DecoratedMarketCondition<T extends PredictableMarketCondition>
        implements MarketCondition, DelayConfirmCondition, DynamicCondition {
    /**
     * 原始条件
     */
    private final T rawCondition;

    /**
     * 整体的包装条件
     */
    private final MarketCondition decoratedCondition;

    /**
     * 访问延迟确认条件的快捷引用，可能为空
     */
    private final DelayConfirmCondition delayConfirmCondition;

    public DecoratedMarketCondition(
            T rawCondition,
            DelayConfirm delayConfirm,
            DeviationCtrl deviationCtrl,
            int delayConfirmedCount) {
        this.rawCondition = rawCondition;

        PredictableMarketCondition deviationCtrlWrappedCondition = DeviationCtrlConditionFactory.INSTANCE.wrapWith(
                rawCondition,
                deviationCtrl);

        this.decoratedCondition = DelayConfirmConditionFactory.INSTANCE.wrapWith(
                deviationCtrlWrappedCondition,
                delayConfirm,
                delayConfirmedCount);

        if (decoratedCondition instanceof DelayConfirmCondition) {
            this.delayConfirmCondition = (DelayConfirmCondition) decoratedCondition;
        } else {
            this.delayConfirmCondition = null;
        }
    }

    protected T getRawCondition() {
        return rawCondition;
    }

    @Override
    public TradeSignal onMarketTick(RealTimeMarket realTimeMarket) {
        return decoratedCondition.onMarketTick(realTimeMarket);
    }

    @Override
    public boolean isDirty() {
        boolean delayConfirmConditionDirty = delayConfirmCondition != null && delayConfirmCondition.isDirty();

        boolean rawConditionDirty = rawCondition instanceof DynamicCondition && ((DynamicCondition) rawCondition).isDirty();

        return delayConfirmConditionDirty || rawConditionDirty;
    }

    @Override
    public void clearDirty() {
        if (delayConfirmCondition != null) {
            delayConfirmCondition.clearDirty();
        }

        if (rawCondition instanceof DynamicCondition) {
            ((DynamicCondition) rawCondition).clearDirty();
        }
    }

    @Override
    public int getDelayConfirmedCount() {
        if (delayConfirmCondition != null) {
            return delayConfirmCondition.getDelayConfirmedCount();
        }
        return 0;
    }

    @Override
    public void resetCounter() {
        if (delayConfirmCondition != null) {
            delayConfirmCondition.resetCounter();
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
        DecoratedMarketCondition<?> that = (DecoratedMarketCondition<?>) o;
        return Objects.equals(decoratedCondition, that.decoratedCondition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(decoratedCondition);
    }

    @Override
    public String toString() {
        return decoratedCondition.toString();
    }
}
