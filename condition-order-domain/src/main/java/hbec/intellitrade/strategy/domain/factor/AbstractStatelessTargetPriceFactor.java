package hbec.intellitrade.strategy.domain.factor;

import java.math.BigDecimal;

/**
 * 无状态的价格因子
 * <p>
 * 对于无状态的实现来说，{@link TargetPriceFactor#apply(Object)}等价于
 * <pre>
 *     getCompareOperator().apply(getTargetPrice())
 * </pre>
 *
 * @author caoshuhao@touker.com
 * @date 2018/2/3
 */
public abstract class AbstractStatelessTargetPriceFactor implements TargetPriceFactor {
    @Override
    public boolean apply(BigDecimal x) {
        return getCompareOperator().apply(x, getTargetPrice());
    }
}
