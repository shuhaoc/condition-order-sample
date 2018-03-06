package me.caosh.condition.domain.model.order;

import com.google.common.base.MoreObjects;
import hbec.intellitrade.common.market.RealTimeMarket;

/**
 * @author caoshuhao@touker.com
 * @date 2018/3/4
 */
public class WrapperTradingMarketSupplier implements TradingMarketSupplier {
    private final RealTimeMarket tradingMarket;

    public WrapperTradingMarketSupplier(RealTimeMarket tradingMarket) {
        this.tradingMarket = tradingMarket;
    }

    @Override
    public RealTimeMarket getTradingMarket() {
        return tradingMarket;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(WrapperTradingMarketSupplier.class).omitNullValues()
                .add("tradingMarket", tradingMarket)
                .toString();
    }
}
