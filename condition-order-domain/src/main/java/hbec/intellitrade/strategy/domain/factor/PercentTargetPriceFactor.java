package hbec.intellitrade.strategy.domain.factor;

import java.math.BigDecimal;

/**
 * 基于百分比增减的目标价因子
 *
 * @author caoshuhao@touker.com
 * @date 2018/2/3
 */
public class PercentTargetPriceFactor extends AbstractStatelessTargetPriceFactor {
    private final CompareOperator compareOperator;
    private final BigDecimal basePrice;
    private final BigDecimal percent;

    public PercentTargetPriceFactor(CompareOperator compareOperator, BigDecimal basePrice, BigDecimal percent) {
        this.compareOperator = compareOperator;
        this.basePrice = basePrice;
        this.percent = percent;
    }

    @Override
    public CompareOperator getCompareOperator() {
        return compareOperator;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public BigDecimal getPercent() {
        return percent;
    }

    @Override
    public BigDecimal getTargetPrice() {
        return new PercentBinaryTargetPriceFactor(compareOperator, percent).getTargetPrice(basePrice);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PercentTargetPriceFactor that = (PercentTargetPriceFactor) o;

        if (compareOperator != that.compareOperator) {
            return false;
        }
        if (!basePrice.equals(that.basePrice)) {
            return false;
        }
        return percent.equals(that.percent);
    }

    @Override
    public int hashCode() {
        int result = compareOperator.hashCode();
        result = 31 * result + basePrice.hashCode();
        result = 31 * result + percent.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "{{x " + compareOperator.getMathSign() + " (" + basePrice + " * (1 + (" + percent + "%)))}, " +
                getTargetPrice() + "}";
    }
}
