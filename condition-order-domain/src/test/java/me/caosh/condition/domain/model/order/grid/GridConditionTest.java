package me.caosh.condition.domain.model.order.grid;

import me.caosh.condition.domain.model.constants.SecurityType;
import me.caosh.condition.domain.model.market.MarketID;
import me.caosh.condition.domain.model.market.RealTimeMarket;
import me.caosh.condition.domain.model.signal.SignalFactory;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Collections;

import static org.junit.Assert.assertEquals;

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
        assertEquals(SignalFactory.getInstance().none(),
                gridCondition.onRealTimeMarketUpdate(new RealTimeMarket(pfyh, new BigDecimal("10.99"), Collections.emptyList())));
        assertEquals(SignalFactory.getInstance().sell(),
                gridCondition.onRealTimeMarketUpdate(new RealTimeMarket(pfyh, new BigDecimal("11.00"), Collections.emptyList())));
        assertEquals(SignalFactory.getInstance().none(),
                gridCondition.onRealTimeMarketUpdate(new RealTimeMarket(pfyh, new BigDecimal("9.01"), Collections.emptyList())));
        assertEquals(SignalFactory.getInstance().buy(),
                gridCondition.onRealTimeMarketUpdate(new RealTimeMarket(pfyh, new BigDecimal("9.00"), Collections.emptyList())));
    }
}
