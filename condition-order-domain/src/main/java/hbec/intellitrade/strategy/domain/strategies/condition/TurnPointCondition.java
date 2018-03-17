package hbec.intellitrade.strategy.domain.strategies.condition;

import com.google.common.base.MoreObjects;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import hbec.intellitrade.strategy.domain.condition.AbstractBasicMarketCondition;
import hbec.intellitrade.strategy.domain.condition.DynamicCondition;
import hbec.intellitrade.strategy.domain.factor.*;

import java.math.BigDecimal;

/**
 * 拐点条件
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2018/1/30
 */
public class TurnPointCondition extends AbstractBasicMarketCondition implements DynamicCondition {
    private InflexionFactor inflexionFactor;

    public TurnPointCondition(BigDecimal breakPrice, BigDecimal turnUpPercent) {
        Preconditions.checkArgument(turnUpPercent.compareTo(BigDecimal.ZERO) > 0,
                "Turn up percent should be greater than 0");
        this.inflexionFactor = new InflexionFactor(
                new BasicTargetPriceFactor(CompareOperator.LE, breakPrice),
                new PercentBinaryTargetPriceFactor(CompareOperator.GE, turnUpPercent));
    }

    public TurnPointCondition(BigDecimal breakPrice, BigDecimal turnUpPercent, Boolean broken, BigDecimal lowestPrice) {
        this.inflexionFactor = new InflexionFactor(
                new BasicTargetPriceFactor(CompareOperator.LE, breakPrice),
                new PercentBinaryTargetPriceFactor(CompareOperator.GE, turnUpPercent),
                broken,
                lowestPrice);
    }

    public BigDecimal getBreakPrice() {
        return inflexionFactor.getBreakPriceFactor().getTargetPrice();
    }

    public BigDecimal getTurnUpPercent() {
        return ((PercentBinaryTargetPriceFactor) inflexionFactor.getTurnBackBinaryPriceFactor()).getPercent();
    }

    public boolean isBroken() {
        return inflexionFactor.isBroken();
    }

    public Optional<BigDecimal> getExtremePrice() {
        return inflexionFactor.getExtremePrice();
    }

    @Override
    public TargetPriceFactor getTargetPriceFactor() {
        return inflexionFactor;
    }

    @Override
    public boolean isDirty() {
        return inflexionFactor.isDirty();
    }

    @Override
    public void clearDirty() {
        inflexionFactor.clearDirty();
    }

    @Override
    public boolean isNeedSwap(DynamicCondition origin) {
        BigDecimal newBreakPrice = inflexionFactor.getBreakPriceFactor().getTargetPrice();
        BigDecimal oldBreakPrice = ((TurnPointCondition) origin).inflexionFactor.getBreakPriceFactor().getTargetPrice();
        return newBreakPrice.compareTo(oldBreakPrice) == 0;
    }

    @Override
    public void swap(DynamicCondition origin) {
        TurnPointCondition that = (TurnPointCondition) origin;
        this.inflexionFactor = new InflexionFactor(
                inflexionFactor.getBreakPriceFactor(),
                inflexionFactor.getTurnBackBinaryPriceFactor(),
                that.isBroken(),
                that.getExtremePrice().orNull());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TurnPointCondition that = (TurnPointCondition) o;

        return inflexionFactor.equals(that.inflexionFactor);
    }

    @Override
    public int hashCode() {
        return inflexionFactor.hashCode();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(TurnPointCondition.class).omitNullValues()
                          .addValue(inflexionFactor)
                          .toString();
    }
}
