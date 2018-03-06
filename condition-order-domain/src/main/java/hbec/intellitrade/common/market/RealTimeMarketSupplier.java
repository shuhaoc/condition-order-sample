package hbec.intellitrade.common.market;

/**
 * Created by caosh on 2017/8/2.
 */
public interface RealTimeMarketSupplier {
    RealTimeMarket getCurrentMarket(MarketID marketID);
}
