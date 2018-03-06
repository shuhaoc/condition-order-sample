package me.caosh.condition.infrastructure.market;

import com.google.common.collect.Lists;
import hbec.intellitrade.common.market.MarketID;
import hbec.intellitrade.common.market.RealTimeMarket;
import hbec.intellitrade.common.market.RealTimeMarketSupplier;
import org.joda.time.LocalDateTime;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Created by caosh on 2017/8/20.
 */
@Service
public class RealTimeMarketSupplierMock implements RealTimeMarketSupplier {
    @Override
    public RealTimeMarket getCurrentMarket(MarketID marketID) {
        return new RealTimeMarket(marketID, new BigDecimal("10.00"), new BigDecimal("10.00"), Lists.newArrayList(
                new BigDecimal("10.04"),
                new BigDecimal("10.03"),
                new BigDecimal("10.02"),
                new BigDecimal("10.01"),
                new BigDecimal("10.00"),
                new BigDecimal("9.99"),
                new BigDecimal("9.98"),
                new BigDecimal("9.97"),
                new BigDecimal("9.96"),
                new BigDecimal("9.95")
        ), LocalDateTime.now());
    }
}
