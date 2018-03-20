package hbec.intellitrade.condorder.domain.orders;

import hbec.intellitrade.condorder.domain.ConditionOrder;
import hbec.intellitrade.condorder.domain.orders.price.PriceOrderBuilder;
import hbec.intellitrade.condorder.domain.strategyinfo.NativeStrategyInfo;
import me.caosh.autoasm.ConvertibleBuilder;

/**
 * @author caoshuhao@touker.com
 * @date 2018/3/20
 */
public enum ConditionOrderBuilderFactory {
    /**
     * 单例
     */
    INSTANCE;

    public ConvertibleBuilder<? extends ConditionOrder> create(int strategyType) {
        if (strategyType == NativeStrategyInfo.PRICE.getStrategyType()) {
            return new PriceOrderBuilder();
        }
        throw new IllegalArgumentException("strategyType=" + strategyType);
    }
}
