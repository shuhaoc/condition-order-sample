package hbec.intellitrade.conditionorder.domain.strategyinfo;

import me.caosh.autoasm.ConvertibleEnum;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2017/8/1
 */
public enum NativeStrategyInfo implements StrategyInfo, ConvertibleEnum<Integer> {
    /**
     * 价格条件单
     */
    PRICE(1),

    /**
     * 时间条件单
     */
    TIME(5),

    /**
     * 拐点买入、回落卖出条件单
     */
    TURN_POINT(7),

    /**
     * 网格交易条件单
     */
    GRID(9),

    /**
     * 定期打新条件单
     */
    NEW_STOCK(10);

    private final int strategyType;

    NativeStrategyInfo(int strategyType) {
        this.strategyType = strategyType;
    }

    @Override
    public StrategySystemType getSystemType() {
        return StrategySystemType.NATIVE;
    }

    @Override
    public int getStrategyType() {
        return strategyType;
    }

    @Override
    public Integer getValue() {
        return strategyType;
    }
}
