package hbec.intellitrade.common.market;

import me.caosh.autoasm.ConvertibleBuilder;
import org.joda.time.LocalDateTime;

import java.math.BigDecimal;
import java.util.Date;
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
    private Date marketTime;

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

    public RealTimeMarketBuilder setMarketTime(Date marketTime) {
        this.marketTime = marketTime;
        return this;
    }

    @Override
    public RealTimeMarket build() {
        return new RealTimeMarket(marketID.build(), currentPrice, previousPrice, offeredPrices, LocalDateTime.fromDateFields(marketTime));
    }
}
