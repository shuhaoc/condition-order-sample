package hbec.intellitrade.conditionorder.domain.trigger;

import hbec.intellitrade.common.market.RealTimeMarket;

/**
 * @author caoshuhao@touker.com
 * @date 2018/3/4
 */
public interface TradingMarketSupplier {
    /**
     * 获取交易标的实时行情
     *
     * @return 交易标的实时行情
     */
    RealTimeMarket getTradingMarket();
}
