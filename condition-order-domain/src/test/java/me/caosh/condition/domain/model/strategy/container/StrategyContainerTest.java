package me.caosh.condition.domain.model.strategy.container;

import hbec.intellitrade.strategy.domain.factor.CompareOperator;
import me.caosh.condition.domain.model.condition.PriceCondition;
import me.caosh.condition.domain.model.condition.TimeReachedCondition;
import me.caosh.condition.domain.model.constants.SecurityType;
import me.caosh.condition.domain.model.market.MarketID;
import me.caosh.condition.domain.model.market.RealTimeMarket;
import me.caosh.condition.domain.model.order.constant.StrategyState;
import me.caosh.condition.domain.model.signal.SignalPayload;
import me.caosh.condition.domain.model.signal.Signals;
import me.caosh.condition.domain.util.MockMarkets;
import org.joda.time.LocalDateTime;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * @author caoshuhao@touker.com
 * @date 2018/1/31
 */
public class StrategyContainerTest {

    private static final MarketID MARKET_ID = new MarketID(SecurityType.STOCK, "600000");
    private static final PriceCondition PRICE_CONDITION = new PriceCondition(CompareOperator.GE, new BigDecimal("10.00"));

    @Test
    public void testBasic() throws Exception {
        StrategyContainer container1 = new StrategyContainer();
        TestPriceStrategy testPriceStrategy1 = new TestPriceStrategy(1, MARKET_ID, PRICE_CONDITION);
        container1.add(testPriceStrategy1);

        StrategyContainer container2 = new StrategyContainer();
        TestPriceStrategy testPriceStrategy2 = new TestPriceStrategy(1, MARKET_ID, PRICE_CONDITION);
        container2.add(testPriceStrategy2);

        assertEquals(container1, container2);
        assertEquals(container1.hashCode(), container2.hashCode());
        System.out.println(container1);
    }

    @Test
    public void testOperate() throws Exception {
        StrategyContainer container = new StrategyContainer();
        TestPriceStrategy testPriceStrategy1 = new TestPriceStrategy(1, MARKET_ID, PRICE_CONDITION);
        container.add(testPriceStrategy1);
        TestPriceStrategy testPriceStrategy2 = new TestPriceStrategy(1, MARKET_ID, PRICE_CONDITION);
        container.add(testPriceStrategy2);
        assertEquals(testPriceStrategy1, testPriceStrategy2);
        assertTrue(container.getBucket(new MarketID(SecurityType.FUND, "510050")).isEmpty());
        assertEquals(container.getBucket(MARKET_ID).size(), 1);
        assertEquals(container.getBucket(MARKET_ID).iterator().next(), testPriceStrategy1);

        container.remove(2);
        assertEquals(container.getBucket(MARKET_ID).size(), 1);

        container.remove(1);
        assertTrue(container.getBucket(MARKET_ID).isEmpty());
    }

    @Test
    public void testOnMarketUpdate() throws Exception {
        StrategyContainer container = new StrategyContainer();
        TestPriceStrategy testPriceStrategy = new TestPriceStrategy(1, MARKET_ID, PRICE_CONDITION);
        container.add(testPriceStrategy);

        RealTimeMarket realTimeMarket1 = new RealTimeMarket(new MarketID(SecurityType.STOCK, "600012"),
                new BigDecimal("10.99"), new BigDecimal("11.00"), Collections.<BigDecimal>emptyList());
        assertTrue(container.onMarketUpdate(Collections.singleton(realTimeMarket1)).isEmpty());

        RealTimeMarket realTimeMarket2 = new RealTimeMarket(MARKET_ID, new BigDecimal("10.00"), new BigDecimal("9.99"),
                Collections.<BigDecimal>emptyList());
        Collection<SignalPayload> signalPayloads = container.onMarketUpdate(Collections.singleton(realTimeMarket2));
        assertEquals(signalPayloads.size(), 1);
        SignalPayload signalPayload = signalPayloads.iterator().next();
        System.out.println(signalPayload.getTriggerId());
        assertEquals(signalPayload.getSignal(), Signals.buyOrSell());
        assertEquals(signalPayload.getStrategy(), testPriceStrategy);

        assertTrue(container.onSecondTick().isEmpty());
    }

    @Test
    public void testOnSecondTick() throws Exception {
        StrategyContainer container = new StrategyContainer();
        TestPriceStrategy testPriceStrategy = new TestPriceStrategy(1, MARKET_ID, PRICE_CONDITION);
        container.add(testPriceStrategy);

        TestTimeStrategy testTimeStrategy1 = new TestTimeStrategy(2,
                new TimeReachedCondition(LocalDateTime.now().plusHours(1)));
        container.add(testTimeStrategy1);
        assertTrue(container.onSecondTick().isEmpty());

        TestTimeStrategy testTimeStrategy2 = new TestTimeStrategy(2,
                new TimeReachedCondition(LocalDateTime.now()));
        container.add(testTimeStrategy2);
        assertEquals(container.getBucket(TimeDrivenBucketKey.INSTANCE).size(), 1);
        Collection<SignalPayload> signalPayloads = container.onSecondTick();
        assertEquals(signalPayloads.size(), 1);
        SignalPayload signalPayload = signalPayloads.iterator().next();
        assertEquals(signalPayload.getSignal(), Signals.buyOrSell());
        assertEquals(signalPayload.getStrategy(), testTimeStrategy2);
    }

    @Test
    public void testState() throws Exception {
        TestPriceStrategy testPriceStrategy = new TestPriceStrategy(1, MARKET_ID, PRICE_CONDITION);
        testPriceStrategy.setStrategyState(StrategyState.PAUSED);

        StrategyContainer container = new StrategyContainer();
        container.add(testPriceStrategy);

        assertTrue(container.onMarketUpdate(Collections.singleton(MockMarkets.withCurrentPrice(
                new BigDecimal("10.00")))).isEmpty());

        assertTrue(container.onSecondTick().isEmpty());
    }
}
