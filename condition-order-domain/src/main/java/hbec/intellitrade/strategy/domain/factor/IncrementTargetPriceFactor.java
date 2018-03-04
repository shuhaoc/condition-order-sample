package hbec.intellitrade.strategy.domain.factor;

import java.math.BigDecimal;

/**
 * @author caoshuhao@touker.com
 * @date 2018/2/3
 */
public class IncrementTargetPriceFactor extends AbstractStatelessTargetPriceFactor {
    private final CompareOperator compareOperator;
    private final BigDecimal basePrice;
    private final BigDecimal increment;

    public IncrementTargetPriceFactor(CompareOperator compareOperator, BigDecimal basePrice, BigDecimal increment) {
        this.compareOperator = compareOperator;
        this.basePrice = basePrice;
        this.increment = increment;
    }

    @Override
    public CompareOperator getCompareOperator() {
        return compareOperator;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public BigDecimal getIncrement() {
        return increment;
    }

    @Override
    public BigDecimal getTargetPrice() {
        return new IncrementBinaryTargetPriceFactor(compareOperator, increment).getTargetPrice(basePrice);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        IncrementTargetPriceFactor that = (IncrementTargetPriceFactor) o;

        if (compareOperator != that.compareOperator) {
            return false;
        }
        if (!basePrice.equals(that.basePrice)) {
            return false;
        }
        return increment.equals(that.increment);
    }

    @Override
    public int hashCode() {
        int result = compareOperator.hashCode();
        result = 31 * result + basePrice.hashCode();
        result = 31 * result + increment.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "{{x " + compareOperator.getMathSign() + " (" + basePrice + " * (1 + (" + increment + ")))}, " +
                getTargetPrice() + "}";
    }
}
