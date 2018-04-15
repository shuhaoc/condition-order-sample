package hbec.intellitrade.strategy.domain.factor;

import java.math.BigDecimal;

/**
 * 二元目标价因子
 *
 * 存在既定比较条件和目标价算法的二元因子，其中目标价的计算需要通过一个价格变量作为入参
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2018/1/30
 */
public interface BinaryTargetPriceFactor {
    /**
     * 获取二元因子类别
     *
     * @return 二元因子类别
     */
    BinaryFactorType getBinaryFactorType();

    /**
     * 价格条件比较操作符
     *
     * @return 比较操作符
     */
    CompareOperator getCompareOperator();

    /**
     * 计算目标价格
     *
     * @param boundVariable 绑定变量
     * @return 目标价格
     */
    BigDecimal getTargetPrice(BigDecimal boundVariable);
}
