package me.caosh.condition.domain.model.order.grid;

import hbec.intellitrade.strategy.domain.signal.Signals;
import me.caosh.condition.mock.MockMarkets;
import org.testng.annotations.Test;

import java.math.BigDecimal;

import static org.testng.Assert.assertEquals;

/**
 * Created by caosh on 2017/8/31.
 *
 * @author caoshuhao@touker.com
 */
public class GridConditionTest {
    @Test
    public void test() throws Exception {
        GridCondition gridCondition = new GridCondition(new BigDecimal("1.00"), new BigDecimal("10.00"));
        assertEquals(Signals.none(),
                     gridCondition.onMarketTick(MockMarkets.withCurrentPrice(new BigDecimal("10.99"))));
        assertEquals(Signals.sell(),
                     gridCondition.onMarketTick(MockMarkets.withCurrentPrice(new BigDecimal("11.00"))));
        assertEquals(Signals.none(),
                     gridCondition.onMarketTick(MockMarkets.withCurrentPrice(new BigDecimal("9.01"))));
        assertEquals(Signals.buy(),
                     gridCondition.onMarketTick(MockMarkets.withCurrentPrice(new BigDecimal("9.00"))));
    }
}
