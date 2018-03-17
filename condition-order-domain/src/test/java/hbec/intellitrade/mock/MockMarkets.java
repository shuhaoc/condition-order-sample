package hbec.intellitrade.mock;

import hbec.intellitrade.common.market.*;
import org.joda.time.LocalDateTime;

import java.math.BigDecimal;
import java.util.Collections;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/2/8
 */
public class MockMarkets {
    public static RealTimeMarket withCurrentPrice(BigDecimal currentPrice) {
        return new RealTimeMarket(new MarketID(MarketType.STOCK, "600000"),
                                  currentPrice,
                                  currentPrice.subtract(new BigDecimal("0.01")),
                                  Collections.<BigDecimal>emptyList(),
                                  LocalDateTime.now());
    }

    public static RealTimeMarketBuilder builderWithCurrentPrice(BigDecimal currentPrice) {
        return new RealTimeMarketBuilder()
                .setMarketID(new MarketIDBuilder().setType(MarketType.STOCK).setCode("600000"))
                .setCurrentPrice(currentPrice)
                .setPreviousPrice(currentPrice.subtract(new BigDecimal("0.01")))
                .setOfferedPrices(Collections.<BigDecimal>emptyList())
                .setMarketTime(LocalDateTime.now());
    }
}
