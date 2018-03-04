package me.caosh.condition.domain.model.strategy.shared;

import com.google.common.base.Preconditions;
import hbec.intellitrade.strategy.domain.factor.CompareOperator;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 面向{@link BigDecimal}的区间工具类
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2018/1/29
 */
public class BigDecimalRanges {
    private static final BigDecimal HUNDRED = BigDecimal.valueOf(100);

    /**
     * 基于中心点向左右分别延伸percent%得出的开区间
     *
     * @param center  中心点
     * @param percent 百分比绝对值
     * @return 区间
     */
    public static Range<BigDecimal> openCenterWithPercent(BigDecimal center, BigDecimal percent) {
        Preconditions.checkArgument(percent.compareTo(BigDecimal.ZERO) >= 0,
                "Percent should be absolute value");

        // 除以100，scale+2保证精度不丢失
        BigDecimal factor = percent.divide(HUNDRED, percent.scale() + 2, RoundingMode.HALF_UP);
        BigDecimal min = center.multiply(BigDecimal.ONE.subtract(factor));
        BigDecimal max = center.multiply(BigDecimal.ONE.add(factor));
        return new Range<>(min, max, CompareOperator.GT, CompareOperator.LT);
    }

    private BigDecimalRanges() {
    }
}
