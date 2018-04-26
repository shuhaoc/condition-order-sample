package hbec.intellitrade.mock;

import com.google.common.collect.Lists;
import hbec.intellitrade.common.market.MarketID;
import hbec.intellitrade.common.market.MarketIDBuilder;
import hbec.intellitrade.common.market.MarketType;
import hbec.intellitrade.common.market.RealTimeMarket;
import hbec.intellitrade.common.market.RealTimeMarketBuilder;
import org.joda.time.LocalDateTime;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/2/8
 */
public class MockMarkets {
    public static final MarketID DEFAULT_MARKET_ID = new MarketID(MarketType.STOCK, "600000");

    public static RealTimeMarket withCurrentPrice(String currentPrice) {
        return withCurrentPrice(new BigDecimal(currentPrice));
    }

    public static RealTimeMarket withCurrentPrice(MarketID marketID, BigDecimal currentPrice) {
        return new RealTimeMarket(marketID,
                currentPrice,
                currentPrice.add(new BigDecimal("-0.10")),
                makeOfferedPrices(currentPrice),
                LocalDateTime.now(),
                LocalDateTime.now().plusMillis(10));
    }

    public static RealTimeMarket withCurrentPrice(BigDecimal currentPrice) {
        return withCurrentPrice(DEFAULT_MARKET_ID, currentPrice);
    }

    private static ArrayList<BigDecimal> makeOfferedPrices(BigDecimal currentPrice) {
        return Lists.newArrayList(
                currentPrice.add(new BigDecimal("0.04")),
                currentPrice.add(new BigDecimal("0.03")),
                currentPrice.add(new BigDecimal("0.02")),
                currentPrice.add(new BigDecimal("0.01")),
                currentPrice,
                currentPrice.add(new BigDecimal("-0.01")),
                currentPrice.add(new BigDecimal("-0.02")),
                currentPrice.add(new BigDecimal("-0.03")),
                currentPrice.add(new BigDecimal("-0.04")),
                currentPrice.add(new BigDecimal("-0.05")));
    }

    public static RealTimeMarketBuilder builderWithCurrentPrice(BigDecimal currentPrice) {
        return new RealTimeMarketBuilder()
                .setMarketID(new MarketIDBuilder().setType(DEFAULT_MARKET_ID.getType())
                                                  .setCode(DEFAULT_MARKET_ID.getCode()))
                .setCurrentPrice(currentPrice)
                .setPreviousPrice(currentPrice.add(new BigDecimal("-0.01")))
                .setOfferedPrices(makeOfferedPrices(currentPrice))
                .setMarketTime(LocalDateTime.now())
                .setArriveTime(LocalDateTime.now().plusMillis(10));
    }
}
