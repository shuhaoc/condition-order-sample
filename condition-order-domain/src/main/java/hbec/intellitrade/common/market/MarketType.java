package hbec.intellitrade.common.market;

import me.caosh.autoasm.ConvertibleEnum;

/**
 * 行情类别，可以是可交易标的或指数等
 *
 * @author caoshuhao@touker.com
 * @date 2018/3/17
 */
public enum MarketType implements ConvertibleEnum<Integer> {
    /**
     * 基金
     */
    FUND(3),

    /**
     * 股票
     */
    STOCK(4),

    /**
     * 指数
     */
    INDEX(5);

    int value;

    MarketType(int value) {
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return value;
    }
}
