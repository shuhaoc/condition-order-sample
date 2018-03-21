package hbec.intellitrade.condorder.domain.orders.turnpoint;

import com.google.common.base.MoreObjects;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import hbec.intellitrade.strategy.domain.condition.AbstractBasicMarketCondition;
import hbec.intellitrade.strategy.domain.condition.DynamicCondition;
import hbec.intellitrade.strategy.domain.factor.BasicTargetPriceFactor;
import hbec.intellitrade.strategy.domain.factor.CompareOperator;
import hbec.intellitrade.strategy.domain.factor.InflexionFactor;
import hbec.intellitrade.strategy.domain.factor.PercentBinaryTargetPriceFactor;
import hbec.intellitrade.strategy.domain.factor.TargetPriceFactor;

import java.math.BigDecimal;

/**
 * 拐点条件
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2018/1/30
 */
public class BasicTurnPointCondition extends AbstractBasicMarketCondition implements DynamicCondition {
    private final InflexionFactor inflexionFactor;
    /**
     * 底线价，可以为空
     */
    private final BigDecimal baselinePrice;

    public BasicTurnPointCondition(CompareOperator compareOperator,
                                   BigDecimal breakPrice,
                                   BigDecimal turnBackPercent) {
        this(compareOperator, breakPrice, turnBackPercent, null, false, null);
    }

    public BasicTurnPointCondition(CompareOperator compareOperator,
                                   BigDecimal breakPrice,
                                   BigDecimal turnBackPercent,
                                   BigDecimal baselinePrice,
                                   boolean broken,
                                   BigDecimal extremePrice) {
        if (compareOperator == CompareOperator.LE) {
            Preconditions.checkArgument(turnBackPercent.compareTo(BigDecimal.ZERO) > 0,
                                        "Turn up percent should be greater than 0");
        }
        if (compareOperator == CompareOperator.GE) {
            Preconditions.checkArgument(turnBackPercent.compareTo(BigDecimal.ZERO) < 0,
                                        "Turn down percent should be less than 0");
        }

        this.inflexionFactor = new InflexionFactor(
                new BasicTargetPriceFactor(CompareOperator.LE, breakPrice),
                new PercentBinaryTargetPriceFactor(CompareOperator.GE, turnBackPercent),
                false, broken,
                extremePrice);
        this.baselinePrice = baselinePrice;
    }

    public BigDecimal getBreakPrice() {
        return inflexionFactor.getBreakPriceFactor().getTargetPrice();
    }

    public BigDecimal getTurnUpPercent() {
        return ((PercentBinaryTargetPriceFactor) inflexionFactor.getTurnBackBinaryPriceFactor()).getPercent();
    }

    public Optional<BigDecimal> getBaselinePrice() {
        return Optional.fromNullable(baselinePrice);
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
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BasicTurnPointCondition that = (BasicTurnPointCondition) o;

        return inflexionFactor.equals(that.inflexionFactor);
    }

    @Override
    public int hashCode() {
        return inflexionFactor.hashCode();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(BasicTurnPointCondition.class).omitNullValues()
                          .addValue(inflexionFactor)
                          .toString();
    }
}
