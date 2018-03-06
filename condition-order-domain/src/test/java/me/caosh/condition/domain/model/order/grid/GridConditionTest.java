package me.caosh.condition.domain.model.order.grid;

import hbec.intellitrade.common.market.MarketID;
import hbec.intellitrade.common.market.RealTimeMarket;
import hbec.intellitrade.common.security.SecurityType;
import me.caosh.condition.domain.model.signal.Signals;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.Collections;

import static org.testng.Assert.assertEquals;

/**
 * Created by caosh on 2017/8/31.
 *
 * @author caoshuhao@touker.com
 */
public class GridConditionTest {
    @Test
    public void test() throws Exception {
        MarketID pfyh = new MarketID(SecurityType.STOCK, "600000");
        GridCondition gridCondition = new GridCondition(new BigDecimal("1.00"), new BigDecimal("10.00"));
        assertEquals(Signals.none(),
                gridCondition.onMarketTick(new RealTimeMarket(pfyh, new BigDecimal("10.99"),
                        Collections.<BigDecimal>emptyList())));
        assertEquals(Signals.sell(),
                gridCondition.onMarketTick(new RealTimeMarket(pfyh, new BigDecimal("11.00"),
                        Collections.<BigDecimal>emptyList())));
        assertEquals(Signals.none(),
                gridCondition.onMarketTick(new RealTimeMarket(pfyh, new BigDecimal("9.01"),
                        Collections.<BigDecimal>emptyList())));
        assertEquals(Signals.buy(),
                gridCondition.onMarketTick(new RealTimeMarket(pfyh, new BigDecimal("9.00"),
                        Collections.<BigDecimal>emptyList())));
    }
}
