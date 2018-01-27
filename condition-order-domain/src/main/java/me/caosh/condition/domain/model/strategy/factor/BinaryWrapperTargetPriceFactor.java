package me.caosh.condition.domain.model.strategy.factor;

import java.math.BigDecimal;

/**
 * 包装二元因子，绑定参数实现一元因子接口的包装器
 * <p>
 * 对于函数f(x,y)，绑定x变量实现f'(y)函数
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2018/1/30
 */
public class BinaryWrapperTargetPriceFactor extends BasicTargetPriceFactor {
    public BinaryWrapperTargetPriceFactor(BinaryTargetPriceFactor binaryTargetPriceFactor, BigDecimal boundVariable) {
        super(binaryTargetPriceFactor.getCompareOperator(), binaryTargetPriceFactor.getTargetPrice(boundVariable));
    }
}
