package hbec.intellitrade.strategy.domain.factor;

import java.math.BigDecimal;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/5/2
 */
public enum BinaryTargetPriceFactorFactory {
    /**
     * 单例
     */
    INSTANCE;

    public BinaryTargetPriceFactor create(CompareOperator compareOperator,
            BinaryFactorType binaryFactorType,
            BigDecimal percent,
            BigDecimal increment) {
        BinaryTargetPriceFactor turnBackFactor;
        if (binaryFactorType == BinaryFactorType.PERCENT) {
            turnBackFactor = new PercentBinaryTargetPriceFactor(compareOperator, percent);
        } else if (binaryFactorType == BinaryFactorType.INCREMENT) {
            turnBackFactor = new IncrementBinaryTargetPriceFactor(compareOperator, increment);
        } else {
            throw new IllegalArgumentException("binaryFactorType=" + binaryFactorType);
        }
        return turnBackFactor;
    }
}
