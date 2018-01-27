package me.caosh.condition.domain.model.strategy.factor;

import java.math.BigDecimal;

/**
 * 基本的目标价价格因子，由价格方向（比较操作）与目标价组成
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2018/1/29
 */
public class BasicTargetPriceFactor extends AbstractStatelessTargetPriceFactor {
    private final CompareOperator compareOperator;
    private final BigDecimal targetPrice;

    /**
     * 构造
     *
     * @param compareOperator 比较操作符
     * @param targetPrice     目标价格，精度不限
     */
    public BasicTargetPriceFactor(CompareOperator compareOperator, BigDecimal targetPrice) {
        this.compareOperator = compareOperator;
        this.targetPrice = targetPrice;
    }

    @Override
    public CompareOperator getCompareOperator() {
        return compareOperator;
    }

    @Override
    public BigDecimal getTargetPrice() {
        return targetPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BasicTargetPriceFactor that = (BasicTargetPriceFactor) o;

        if (compareOperator != that.compareOperator) {
            return false;
        }
        return targetPrice.equals(that.targetPrice);
    }

    @Override
    public int hashCode() {
        int result = compareOperator.hashCode();
        result = 31 * result + targetPrice.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "{x " + compareOperator.getMathSign() + " " + targetPrice + "}";
    }
}
