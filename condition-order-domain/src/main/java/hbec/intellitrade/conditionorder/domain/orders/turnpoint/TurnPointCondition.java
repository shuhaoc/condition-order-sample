package hbec.intellitrade.conditionorder.domain.orders.turnpoint;

import com.google.common.base.MoreObjects;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import hbec.intellitrade.strategy.domain.condition.AbstractMarketCondition;
import hbec.intellitrade.strategy.domain.condition.DynamicCondition;
import hbec.intellitrade.strategy.domain.factor.BasicTargetPriceFactor;
import hbec.intellitrade.strategy.domain.factor.BinaryFactorType;
import hbec.intellitrade.strategy.domain.factor.BinaryTargetPriceFactor;
import hbec.intellitrade.strategy.domain.factor.BinaryTargetPriceFactorFactory;
import hbec.intellitrade.strategy.domain.factor.CompareOperator;
import hbec.intellitrade.strategy.domain.factor.InflexionFactor;
import hbec.intellitrade.strategy.domain.factor.NumericalDirection;
import hbec.intellitrade.strategy.domain.factor.TargetPriceFactor;

import java.math.BigDecimal;

/**
 * 基本拐点条件
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2018/1/30
 */
public class TurnPointCondition extends AbstractMarketCondition implements DynamicCondition {
    private final InflexionFactor inflexionFactor;

    public TurnPointCondition(CompareOperator compareOperator,
                              BigDecimal breakPrice,
                              BinaryFactorType binaryFactorType,
                              BigDecimal turnBackPercent,
                              BigDecimal turnBackIncrement,
                              boolean useGuaranteedPrice) {
        this(compareOperator,
             breakPrice,
             binaryFactorType,
             turnBackPercent,
             turnBackIncrement,
             useGuaranteedPrice,
             false,
             null);
    }

    public TurnPointCondition(CompareOperator compareOperator,
                              BigDecimal breakPrice,
                              BinaryFactorType binaryFactorType,
                              BigDecimal turnBackPercent,
                              BigDecimal turnBackIncrement,
                              boolean useGuaranteedPrice,
                              boolean broken,
                              BigDecimal extremePrice) {
        Preconditions.checkNotNull(compareOperator, "compareOperator cannot be null");
        Preconditions.checkNotNull(breakPrice, "breakPrice cannot be null");
        Preconditions.checkNotNull(binaryFactorType, "binaryFactorType cannot be null");

        if (compareOperator.getNumericalDirection() == NumericalDirection.LESS) {
            Preconditions.checkArgument(turnBackPercent == null || turnBackPercent.compareTo(BigDecimal.ZERO) > 0,
                                        "Turn up percent should be greater than 0");
            Preconditions.checkArgument(turnBackIncrement == null || turnBackIncrement.compareTo(BigDecimal.ZERO) > 0,
                                        "Turn up increment should be greater than 0");
        }
        if (compareOperator.getNumericalDirection() == NumericalDirection.GREATER) {
            Preconditions.checkArgument(turnBackPercent == null || turnBackPercent.compareTo(BigDecimal.ZERO) < 0,
                                        "Turn down percent should be less than 0");
            Preconditions.checkArgument(turnBackIncrement == null || turnBackIncrement.compareTo(BigDecimal.ZERO) < 0,
                                        "Turn down increment should be less than 0");
        }

        BinaryTargetPriceFactor turnBackFactor = BinaryTargetPriceFactorFactory.INSTANCE.create(
                compareOperator.reverse(),
                binaryFactorType,
                turnBackPercent,
                turnBackIncrement);
        this.inflexionFactor = new InflexionFactor(
                new BasicTargetPriceFactor(compareOperator, breakPrice),
                turnBackFactor,
                useGuaranteedPrice,
                broken,
                extremePrice);
    }

    public CompareOperator getCompareOperator() {
        return inflexionFactor.getBreakPriceFactor().getCompareOperator();
    }

    public BigDecimal getBreakPrice() {
        return inflexionFactor.getBreakPriceFactor().getTargetPrice();
    }

    public boolean isUseGuaranteedPrice() {
        return inflexionFactor.isUseGuaranteedPrice();
    }

    public boolean isBroken() {
        return inflexionFactor.isBroken();
    }

    public Optional<BigDecimal> getExtremePrice() {
        return inflexionFactor.getExtremePrice();
    }

    public BinaryTargetPriceFactor getTurnBackBinaryPriceFactor() {
        return inflexionFactor.getTurnBackBinaryPriceFactor();
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
