package hbec.intellitrade.strategy.domain.shared;

import java.math.BigDecimal;

/**
 * @author caoshuhao@touker.com
 * @date 2018/3/18
 */
public class BigDecimals {
    public static BigDecimal max(BigDecimal a, BigDecimal b) {
        return a.compareTo(b) >= 0 ? a : b;
    }

    public static BigDecimal min(BigDecimal a, BigDecimal b) {
        return a.compareTo(b) <= 0 ? a : b;
    }

    private BigDecimals() {
    }

    private static final BigDecimals _CODE_COVERAGE = new BigDecimals();
}
