package hbec.intellitrade.strategy.domain.factor;

import java.math.BigDecimal;

/**
 * 数值变化方向
 *
 * @author caoshuhao@touker.com
 * @date 2018/3/24
 */
public enum NumericalDirection {
    /**
     * 更大（对应价格上涨）
     */
    GREATER(BigDecimal.valueOf(1)),

    /**
     * 更小（对应价格下跌）
     */
    LESS(BigDecimal.valueOf(-1));

    /**
     * 符号因子，1或-1
     */
    BigDecimal signFactor;

    NumericalDirection(BigDecimal signFactor) {
        this.signFactor = signFactor;
    }

    public BigDecimal getSignFactor() {
        return signFactor;
    }
}
