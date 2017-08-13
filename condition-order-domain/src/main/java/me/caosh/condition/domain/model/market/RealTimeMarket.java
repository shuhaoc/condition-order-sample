package me.caosh.condition.domain.model.market;

import com.google.common.base.MoreObjects;

import java.math.BigDecimal;

/**
 * Created by caosh on 2017/8/7.
 */
public class RealTimeMarket {
    private final MarketID marketID;
    private final BigDecimal currentPrice;

    public RealTimeMarket(MarketID marketID, BigDecimal currentPrice) {
        this.marketID = marketID;
        this.currentPrice = currentPrice;
    }

    public MarketID getMarketID() {
        return marketID;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("marketID", marketID)
                .add("currentPrice", currentPrice)
                .toString();
    }
}
