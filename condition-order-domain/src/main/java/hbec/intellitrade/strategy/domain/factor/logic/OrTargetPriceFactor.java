package hbec.intellitrade.strategy.domain.factor.logic;

import com.google.common.base.Preconditions;
import hbec.intellitrade.strategy.domain.factor.CompareOperator;
import hbec.intellitrade.strategy.domain.factor.TargetPriceFactor;
import hbec.intellitrade.strategy.domain.shared.BigDecimals;

import java.math.BigDecimal;

/**
 * 目标价因子的或运算因子
 *
 * @author caoshuhao@touker.com
 * @date 2018/3/17
 */
public class OrTargetPriceFactor implements TargetPriceFactor {
    private final TargetPriceFactor fa;
    private final TargetPriceFactor fb;

    public OrTargetPriceFactor(TargetPriceFactor fa, TargetPriceFactor fb) {
        Preconditions.checkNotNull(fa, "fa cannot be null");
        Preconditions.checkNotNull(fb, "fb cannot be null");
        Preconditions.checkArgument(fa.getCompareOperator() == fb.getCompareOperator(),
                                    "compareOperator muste be same");

        this.fa = fa;
        this.fb = fb;
    }

    @Override
    public CompareOperator getCompareOperator() {
        // 构造中已限定比较操作符必须相同
        return fa.getCompareOperator();
    }

    @Override
    public BigDecimal getTargetPrice() {
        CompareOperator compareOperator = getCompareOperator();
        if (compareOperator == CompareOperator.LE || compareOperator == CompareOperator.LT) {
            return BigDecimals.max(fa.getTargetPrice(), fb.getTargetPrice());
        } else if (compareOperator == CompareOperator.GE || compareOperator == CompareOperator.GT) {
            return BigDecimals.min(fa.getTargetPrice(), fb.getTargetPrice());
        }
        throw new IllegalArgumentException("compareOperator=" + compareOperator);
    }

    @Override
    public boolean apply(BigDecimal price) {
        return fa.apply(price) || fb.apply(price);
    }
}
