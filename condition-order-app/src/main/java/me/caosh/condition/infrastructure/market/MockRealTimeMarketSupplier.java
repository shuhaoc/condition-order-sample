package me.caosh.condition.infrastructure.market;

import hbec.intellitrade.common.market.MarketID;
import hbec.intellitrade.common.market.RealTimeMarket;
import hbec.intellitrade.common.market.RealTimeMarketSupplier;
import me.caosh.condition.mock.MockMarkets;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Created by caosh on 2017/8/20.
 */
@Service
public class MockRealTimeMarketSupplier implements RealTimeMarketSupplier {
    @Override
    public RealTimeMarket getCurrentMarket(MarketID marketID) {
        return MockMarkets.withCurrentPrice(new BigDecimal("10.00"));
    }
}
