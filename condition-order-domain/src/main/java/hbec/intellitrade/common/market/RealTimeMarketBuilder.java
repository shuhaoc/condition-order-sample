package hbec.intellitrade.common.market;

import com.google.common.base.MoreObjects;
import me.caosh.autoasm.ConvertibleBuilder;
import org.joda.time.LocalDateTime;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/3/7
 */
public class RealTimeMarketBuilder implements ConvertibleBuilder<RealTimeMarket> {
    private MarketIDBuilder marketID = new MarketIDBuilder();
    private BigDecimal currentPrice;
    private BigDecimal previousPrice;
    private List<BigDecimal> offeredPrices;
    private LocalDateTime marketTime;
    private LocalDateTime arriveTime;

    public MarketIDBuilder getMarketID() {
        return marketID;
    }

    public RealTimeMarketBuilder setMarketID(MarketIDBuilder marketID) {
        this.marketID = marketID;
        return this;
    }

    public RealTimeMarketBuilder setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
        return this;
    }

    public RealTimeMarketBuilder setPreviousPrice(BigDecimal previousPrice) {
        this.previousPrice = previousPrice;
        return this;
    }

    public RealTimeMarketBuilder setOfferedPrices(List<BigDecimal> offeredPrices) {
        this.offeredPrices = offeredPrices;
        return this;
    }

    public RealTimeMarketBuilder setMarketTime(LocalDateTime marketTime) {
        this.marketTime = marketTime;
        return this;
    }

    public RealTimeMarketBuilder setArriveTime(LocalDateTime arriveTime) {
        this.arriveTime = arriveTime;
        return this;
    }

    @Override
    public RealTimeMarket build() {
        LocalDateTime arriveTime = MoreObjects.firstNonNull(this.arriveTime, this.marketTime);
        return new RealTimeMarket(marketID.build(),
                                  currentPrice,
                                  previousPrice,
                                  offeredPrices,
                                  marketTime,
                                  arriveTime);
    }
}
