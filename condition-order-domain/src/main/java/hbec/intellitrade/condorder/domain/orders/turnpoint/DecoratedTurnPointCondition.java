package hbec.intellitrade.condorder.domain.orders.turnpoint;

import com.google.common.base.MoreObjects;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import hbec.intellitrade.common.market.RealTimeMarket;
import hbec.intellitrade.condorder.domain.orders.DecoratedMarketCondition;
import hbec.intellitrade.strategy.domain.condition.DynamicCondition;
import hbec.intellitrade.strategy.domain.condition.delayconfirm.DelayConfirm;
import hbec.intellitrade.strategy.domain.condition.deviation.DeviationCtrl;
import hbec.intellitrade.strategy.domain.condition.deviation.DisabledDeviationCtrl;
import hbec.intellitrade.strategy.domain.condition.market.MarketCondition;
import hbec.intellitrade.strategy.domain.factor.BinaryTargetPriceFactor;
import hbec.intellitrade.strategy.domain.factor.CompareOperator;
import hbec.intellitrade.strategy.domain.signal.TradeSignal;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * 拐点条件包装类
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2018/3/21
 */
public class DecoratedTurnPointCondition implements MarketCondition, DynamicCondition {
    private final TurnPointCondition rawTurnPointCondition;
    private final DecoratedMarketCondition<TurnPointCondition> turnPointCondition;
    private final DecoratedMarketCondition<CrossBaselineCondition> crossBaselineCondition;
    private final DelayConfirm delayConfirm;
    private final DeviationCtrl deviationCtrl;

    /**
     * 构造拐点包装条件
     *
     * @param turnPointCondition           原始拐点条件
     * @param baselinePrice                底线价，可以为空
     * @param delayConfirm                 延迟确认参数
     * @param deviationCtrl                偏差控制参数
     * @param turnPointDelayConfirmedCount 拐点条件当前延迟确认次数
     * @param crossDelayConfirmedCount     穿越底线当前延迟确认次数
     */
    public DecoratedTurnPointCondition(TurnPointCondition turnPointCondition,
                                       BigDecimal baselinePrice,
                                       DelayConfirm delayConfirm,
                                       DeviationCtrl deviationCtrl,
                                       int turnPointDelayConfirmedCount,
                                       int crossDelayConfirmedCount) {
        Preconditions.checkNotNull(turnPointCondition, "turnPointCondition cannot be null");
        Preconditions.checkNotNull(delayConfirm, "delayConfirm cannot be null");
        Preconditions.checkNotNull(deviationCtrl, "deviationCtrl cannot be null");

        if (baselinePrice != null) {
            CompareOperator breakCompareOperator = turnPointCondition.getCompareOperator().withoutEquals();
            Preconditions.checkArgument(breakCompareOperator.apply(baselinePrice, turnPointCondition.getBreakPrice()),
                                        "Baseline price must be more faraway than break price");
        }

        this.rawTurnPointCondition = turnPointCondition;
        this.turnPointCondition = new DecoratedMarketCondition<>(turnPointCondition,
                                                                 delayConfirm,
                                                                 deviationCtrl,
                                                                 turnPointDelayConfirmedCount);

        // 踩线条件是可选的
        if (baselinePrice != null) {
            // 穿越底线条件的价格方向，与拐点条件的突破条件方向相同，但是价格等于底线价时不满足条件
            CompareOperator compareOperator = turnPointCondition.getCompareOperator();
            CrossBaselineCondition rawCrossBaselineCondition = new CrossBaselineCondition(
                    compareOperator.withoutEquals(),
                    baselinePrice);
            // 穿越底线条件仅支持延迟确认
            this.crossBaselineCondition = new DecoratedMarketCondition<>(rawCrossBaselineCondition,
                                                                         delayConfirm,
                                                                         DisabledDeviationCtrl.DISABLED,
                                                                         crossDelayConfirmedCount);
        } else {
            crossBaselineCondition = null;
        }

        this.delayConfirm = delayConfirm;
        this.deviationCtrl = deviationCtrl;
    }

    public TurnPointCondition getRawTurnPointCondition() {
        return rawTurnPointCondition;
    }

    public CompareOperator getCompareOperator() {
        return rawTurnPointCondition.getCompareOperator();
    }

    public BigDecimal getBreakPrice() {
        return rawTurnPointCondition.getBreakPrice();
    }

    public boolean isBroken() {
        return rawTurnPointCondition.isBroken();
    }

    public Optional<BigDecimal> getExtremePrice() {
        return rawTurnPointCondition.getExtremePrice();
    }

    public BinaryTargetPriceFactor getTurnBackBinaryPriceFactor() {
        return rawTurnPointCondition.getTurnBackBinaryPriceFactor();
    }

    public Optional<CrossBaselineCondition> getRawCrossBaselineCondition() {
        if (crossBaselineCondition == null) {
            return Optional.absent();
        }
        return Optional.of(crossBaselineCondition.getRawCondition());
    }

    public DelayConfirm getDelayConfirm() {
        return delayConfirm;
    }

    public DeviationCtrl getDeviationCtrl() {
        return deviationCtrl;
    }

    DecoratedMarketCondition<TurnPointCondition> getTurnPointCondition() {
        return turnPointCondition;
    }

    DecoratedMarketCondition<CrossBaselineCondition> getCrossBaselineCondition() {
        return crossBaselineCondition;
    }

    @Override
    public TradeSignal onMarketTick(RealTimeMarket realTimeMarket) {
        // 底线价是前置条件
        if (crossBaselineCondition != null) {
            TradeSignal crossSignal = crossBaselineCondition.onMarketTick(realTimeMarket);
            if (crossSignal.isValid()) {
                return crossSignal;
            }
        }

        return turnPointCondition.onMarketTick(realTimeMarket);
    }

    @Override
    public boolean isDirty() {
        boolean crossBaselineConditionDirty = crossBaselineCondition != null && crossBaselineCondition.isDirty();
        boolean turnPointConditionDirty = turnPointCondition.isDirty();
        return crossBaselineConditionDirty || turnPointConditionDirty;
    }

    @Override
    public void clearDirty() {
        if (crossBaselineCondition != null) {
            crossBaselineCondition.clearDirty();
        }
        turnPointCondition.clearDirty();
    }

    public void resetCounter() {
        if (crossBaselineCondition != null) {
            crossBaselineCondition.resetCounter();
        }
        turnPointCondition.resetCounter();
    }

    @Override
    public boolean equals(Object o) {
        // 仅比较关键字段，忽略冗余字段
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DecoratedTurnPointCondition that = (DecoratedTurnPointCondition) o;
        return Objects.equals(turnPointCondition, that.turnPointCondition) &&
               Objects.equals(crossBaselineCondition, that.crossBaselineCondition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(turnPointCondition, crossBaselineCondition);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(DecoratedTurnPointCondition.class).omitNullValues()
                          .add("turnPointCondition", turnPointCondition)
                          .add("crossBaselineCondition", crossBaselineCondition)
                          .toString();
    }
}
