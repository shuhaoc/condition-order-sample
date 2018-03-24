package hbec.intellitrade.condorder.domain.tradeplan;

import com.google.common.collect.Lists;
import hbec.intellitrade.common.market.MarketID;
import hbec.intellitrade.common.market.RealTimeMarket;
import hbec.intellitrade.common.security.SecurityType;
import org.joda.time.LocalDateTime;
import org.testng.annotations.Test;

import java.math.BigDecimal;

import static org.testng.Assert.assertEquals;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/3/24
 */
public class EntrustStrategyTest {
    @Test
    public void test() throws Exception {
        MarketID pfyh = new MarketID(SecurityType.STOCK, "600000");
        RealTimeMarket realTimeMarket = new RealTimeMarket(pfyh,
                                                           new BigDecimal("10.00"),
                                                           new BigDecimal("10.00"),
                                                           Lists.newArrayList(
                                                                   new BigDecimal("10.04"),
                                                                   new BigDecimal("10.03"),
                                                                   new BigDecimal("10.02"),
                                                                   new BigDecimal("10.01"),
                                                                   new BigDecimal("10.00"),
                                                                   new BigDecimal("9.99"),
                                                                   new BigDecimal("9.98"),
                                                                   new BigDecimal("9.97"),
                                                                   new BigDecimal("9.96"),
                                                                   new BigDecimal("9.95")),
                                                           LocalDateTime.now());
        assertEquals(new BigDecimal("10.00"), EntrustStrategy.CURRENT_PRICE.selectEntrustPrice(realTimeMarket));
        assertEquals(new BigDecimal("10.04"), EntrustStrategy.SELL5.selectEntrustPrice(realTimeMarket));
        assertEquals(new BigDecimal("10.00"), EntrustStrategy.SELL1.selectEntrustPrice(realTimeMarket));
        assertEquals(new BigDecimal("9.99"), EntrustStrategy.BUY1.selectEntrustPrice(realTimeMarket));
        assertEquals(new BigDecimal("9.95"), EntrustStrategy.BUY5.selectEntrustPrice(realTimeMarket));
    }
}
