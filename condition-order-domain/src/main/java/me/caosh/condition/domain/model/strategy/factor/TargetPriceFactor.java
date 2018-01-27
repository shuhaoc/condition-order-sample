package me.caosh.condition.domain.model.strategy.factor;

import java.math.BigDecimal;

/**
 * 目标价因子，即存在比较条件和目标价的价格因子
 * <p>
 * 对于无状态的实现来说，{@link TargetPriceFactor#apply(Object)}等价于
 * <pre>
 *     getCompareOperator().apply(getTargetPrice())
 * </pre>
 *
 * 对于有状态的实现来说，此等价关系不成立。必须通过{@link TargetPriceFactor#apply(Object)}判断条件，
 * {@link TargetPriceFactor#getTargetPrice()}可以用于查询目标价
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2018/1/30
 */
public interface TargetPriceFactor extends PriceFactor {
    /**
     * 价格条件比较操作符
     *
     * @return 比较操作符
     */
    CompareOperator getCompareOperator();

    /**
     * 目标价格
     *
     * @return 目标价格
     */
    BigDecimal getTargetPrice();
}
