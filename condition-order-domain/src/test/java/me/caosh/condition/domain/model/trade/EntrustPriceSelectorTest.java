package me.caosh.condition.domain.model.trade;

import com.google.common.collect.Lists;
import me.caosh.condition.domain.model.constants.SecurityType;
import me.caosh.condition.domain.model.market.MarketID;
import me.caosh.condition.domain.model.market.RealTimeMarket;
import me.caosh.condition.domain.model.order.constant.EntrustStrategy;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

/**
 * Created by caosh on 2017/8/31.
 *
 * @author caoshuhao@touker.com
 */
public class EntrustPriceSelectorTest {
    @Test
    public void test() throws Exception {
        MarketID pfyh = new MarketID(SecurityType.STOCK, "600000");
        RealTimeMarket realTimeMarket = new RealTimeMarket(pfyh,
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
                        new BigDecimal("9.95")));
        assertEquals(new BigDecimal("10.00"), EntrustPriceSelector.selectPrice(realTimeMarket, EntrustStrategy.CURRENT_PRICE));
        assertEquals(new BigDecimal("10.04"), EntrustPriceSelector.selectPrice(realTimeMarket, EntrustStrategy.SELL5));
        assertEquals(new BigDecimal("10.00"), EntrustPriceSelector.selectPrice(realTimeMarket, EntrustStrategy.SELL1));
        assertEquals(new BigDecimal("9.99"), EntrustPriceSelector.selectPrice(realTimeMarket, EntrustStrategy.BUY1));
        assertEquals(new BigDecimal("9.95"), EntrustPriceSelector.selectPrice(realTimeMarket, EntrustStrategy.BUY5));
    }
}
