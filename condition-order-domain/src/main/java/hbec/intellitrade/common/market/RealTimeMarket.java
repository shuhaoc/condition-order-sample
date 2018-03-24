package hbec.intellitrade.common.market;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import org.joda.time.LocalDateTime;

import java.math.BigDecimal;
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

    public RealTimeMarket(MarketID marketID,
                          BigDecimal currentPrice,
                          BigDecimal previousPrice,
                          List<BigDecimal> offeredPrices,
                          LocalDateTime marketTime) {
        this(marketID, currentPrice, previousPrice, offeredPrices, marketTime, marketTime);
    }

    /**
     * 构造实时行情对象
     *
     * @param marketID      行情ID
     * @param currentPrice  当前价
     * @param previousPrice 昨收价
     * @param offeredPrices 买卖五档价格，必须为10个元素，元素允许为空
     * @param marketTime    行情时间
     * @param arriveTime    行情到达时间
     */
    public RealTimeMarket(MarketID marketID,
                          BigDecimal currentPrice,
                          BigDecimal previousPrice,
                          List<BigDecimal> offeredPrices,
                          LocalDateTime marketTime,
                          LocalDateTime arriveTime) {
        Preconditions.checkArgument(offeredPrices.size() == 10, "Offered prices must be 10 elements");
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
        return offeredPrices;
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
