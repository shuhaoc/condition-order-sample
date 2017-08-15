package me.caosh.condition.domain.model.market;

import com.google.common.base.MoreObjects;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

/**
 * Created by caosh on 2017/8/7.
 */
public class RealTimeMarket {
    private final MarketID marketID;
    private final BigDecimal currentPrice;
    private final List<BigDecimal> offeredPrices;

    public RealTimeMarket(MarketID marketID, BigDecimal currentPrice, List<BigDecimal> offeredPrices) {
        this.marketID = marketID;
        this.currentPrice = currentPrice;
        this.offeredPrices = offeredPrices;
    }

    public MarketID getMarketID() {
        return marketID;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public List<BigDecimal> getOfferedPrices() {
        return Collections.unmodifiableList(offeredPrices);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("marketID", marketID)
                .add("currentPrice", currentPrice)
                .add("offeredPrices", offeredPrices)
                .toString();
    }
}
