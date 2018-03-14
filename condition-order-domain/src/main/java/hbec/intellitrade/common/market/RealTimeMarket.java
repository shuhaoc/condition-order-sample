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
    private final LocalDateTime arriveTime;

    @Deprecated
    public RealTimeMarket(MarketID marketID, BigDecimal currentPrice, List<BigDecimal> offeredPrices) {
        this(marketID, currentPrice, currentPrice, offeredPrices, LocalDateTime.now(), LocalDateTime.now());
    }

    @Deprecated
    public RealTimeMarket(MarketID marketID, BigDecimal currentPrice,
                          BigDecimal previousPrice,
                          List<BigDecimal> offeredPrices) {
        this(marketID, currentPrice, previousPrice, offeredPrices, LocalDateTime.now(), LocalDateTime.now());
    }

    public RealTimeMarket(MarketID marketID,
                          BigDecimal currentPrice,
                          BigDecimal previousPrice,
                          List<BigDecimal> offeredPrices,
                          LocalDateTime marketTime) {
        this(marketID, currentPrice, previousPrice, offeredPrices, marketTime, marketTime);
    }

    public RealTimeMarket(MarketID marketID,
                          BigDecimal currentPrice,
                          BigDecimal previousPrice,
                          List<BigDecimal> offeredPrices,
                          LocalDateTime marketTime,
                          LocalDateTime arriveTime) {
        this.marketID = marketID;
        this.currentPrice = currentPrice;
        this.previousPrice = previousPrice;
        this.offeredPrices = offeredPrices;
        this.marketTime = marketTime;
        this.arriveTime = arriveTime;
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

    public LocalDateTime getArriveTime() {
        return arriveTime;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(RealTimeMarket.class).omitNullValues()
                          .add("marketID", marketID)
                          .add("currentPrice", currentPrice)
                          .add("previousPrice", previousPrice)
                          .add("offeredPrices", offeredPrices)
                          .add("marketTime", marketTime)
                          .add("arriveTime", arriveTime)
                          .toString();
    }
}
