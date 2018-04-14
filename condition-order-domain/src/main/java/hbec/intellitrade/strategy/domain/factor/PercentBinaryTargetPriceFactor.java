package hbec.intellitrade.strategy.domain.factor;

import com.google.common.base.Preconditions;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 基于百分比增减的二元价格因子
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2018/1/30
 */
public class PercentBinaryTargetPriceFactor implements BinaryTargetPriceFactor {
    private final CompareOperator compareOperator;
    private final BigDecimal percent;

    /**
     * 构造
     *
     * @param compareOperator 比较操作符
     * @param percent         百分比，可正可负
     */
    public PercentBinaryTargetPriceFactor(CompareOperator compareOperator, BigDecimal percent) {
        Preconditions.checkNotNull(compareOperator, "compareOperator cannot be null");
        Preconditions.checkNotNull(percent, "percent cannot be null");

        this.compareOperator = compareOperator;
        this.percent = percent;
    }

    public BigDecimal getPercent() {
        return percent;
    }

    @Override
    public CompareOperator getCompareOperator() {
        return compareOperator;
    }

    @Override
    public BigDecimal getTargetPrice(BigDecimal boundVariable) {
        // 除以100时scale+2保证精度不丢失
        return boundVariable.multiply(BigDecimal.ONE.add(
                percent.divide(BigDecimal.valueOf(100), percent.scale() + 2, RoundingMode.HALF_UP)));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PercentBinaryTargetPriceFactor that = (PercentBinaryTargetPriceFactor) o;

        if (compareOperator != that.compareOperator) {
            return false;
        }
        return percent.equals(that.percent);
    }

    @Override
    public int hashCode() {
        int result = compareOperator.hashCode();
        result = 31 * result + percent.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "{y " + compareOperator.getMathSign() + " x * (1 + (" + percent + "%))}";
    }
}
