package hbec.intellitrade.common.market;

import com.google.common.base.MoreObjects;
import org.joda.time.LocalDateTime;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

/**
 * Created by caosh on 2017/8/7.
 */
public class RealTimeMarket {
    private final MarketID marketID;
    private final BigDecimal currentPrice;
    private final BigDecimal previousPrice;
    private final List<BigDecimal> offeredPrices;
    private final LocalDateTime marketTime;

    @Deprecated
    public RealTimeMarket(MarketID marketID, BigDecimal currentPrice, List<BigDecimal> offeredPrices) {
        this.marketID = marketID;
        this.currentPrice = currentPrice;
        this.previousPrice = null;
        this.offeredPrices = offeredPrices;
        this.marketTime = null;
    }

    @Deprecated
    public RealTimeMarket(MarketID marketID, BigDecimal currentPrice,
                          BigDecimal previousPrice,
                          List<BigDecimal> offeredPrices) {
        this.marketID = marketID;
        this.currentPrice = currentPrice;
        this.previousPrice = previousPrice;
        this.offeredPrices = offeredPrices;
        this.marketTime = null;
    }

    public RealTimeMarket(MarketID marketID,
                          BigDecimal currentPrice,
                          BigDecimal previousPrice,
                          List<BigDecimal> offeredPrices,
                          LocalDateTime marketTime) {
        this.marketID = marketID;
        this.currentPrice = currentPrice;
        this.previousPrice = previousPrice;
        this.offeredPrices = offeredPrices;
        this.marketTime = marketTime;
    }

    public MarketID getMarketID() {
        return marketID;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public BigDecimal getPreviousPrice() {
        return previousPrice;
    }

    public List<BigDecimal> getOfferedPrices() {
        return Collections.unmodifiableList(offeredPrices);
    }

    public LocalDateTime getMarketTime() {
        return marketTime;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(RealTimeMarket.class).omitNullValues()
                .add("marketID", marketID)
                .add("currentPrice", currentPrice)
                .add("previousPrice", previousPrice)
                .add("offeredPrices", offeredPrices)
                .add("marketTime", marketTime)
                .toString();
    }
}
