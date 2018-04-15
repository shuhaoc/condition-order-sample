package hbec.intellitrade.strategy.domain.factor;

import com.google.common.base.Preconditions;

import java.math.BigDecimal;

/**
 * 基于价格增量的二元价格因子
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2018/1/30
 */
public class IncrementBinaryTargetPriceFactor implements BinaryTargetPriceFactor {
    private final CompareOperator compareOperator;
    private final BigDecimal increment;

    /**
     * 构造
     *
     * @param compareOperator 比较操作符
     * @param increment       价格增量，可正可负
     */
    public IncrementBinaryTargetPriceFactor(CompareOperator compareOperator, BigDecimal increment) {
        Preconditions.checkNotNull(compareOperator, "compareOperator cannot be null");
        Preconditions.checkNotNull(increment, "increment cannot be null");

        this.compareOperator = compareOperator;
        this.increment = increment;
    }

    @Override
    public BinaryFactorType getBinaryFactorType() {
        return BinaryFactorType.INCREMENT;
    }

    public BigDecimal getIncrement() {
        return increment;
    }

    @Override
    public CompareOperator getCompareOperator() {
        return compareOperator;
    }

    @Override
    public BigDecimal getTargetPrice(BigDecimal boundVariable) {
        return boundVariable.add(increment);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        IncrementBinaryTargetPriceFactor that = (IncrementBinaryTargetPriceFactor) o;

        if (compareOperator != that.compareOperator) {
            return false;
        }
        return increment.equals(that.increment);
    }

    @Override
    public int hashCode() {
        int result = compareOperator.hashCode();
        result = 31 * result + increment.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "{y " + compareOperator.getMathSign() + " x + (" + increment + ")}";
    }
}
