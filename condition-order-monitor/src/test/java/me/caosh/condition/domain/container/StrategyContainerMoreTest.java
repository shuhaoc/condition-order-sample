package me.caosh.condition.domain.container;

import hbec.intellitrade.common.market.MarketID;
import hbec.intellitrade.common.market.MarketType;
import hbec.intellitrade.common.market.RealTimeMarket;
import hbec.intellitrade.condorder.domain.orders.price.PriceCondition;
import hbec.intellitrade.condorder.domain.orders.turnpoint.TurnPointCondition;
import hbec.intellitrade.strategy.domain.factor.BinaryFactorType;
import hbec.intellitrade.strategy.domain.factor.CompareOperator;
import hbec.intellitrade.strategy.domain.signal.Signals;
import hbec.intellitrade.strategy.domain.signalpayload.SignalPayload;
import me.caosh.condition.mock.MockMarkets;
import org.joda.time.LocalDateTime;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.*;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/2/7
 */
public class StrategyContainerMoreTest {
    private static final MarketID MARKET_ID = new MarketID(MarketType.STOCK, "600000");
    private static final PriceCondition PRICE_CONDITION = new PriceCondition(CompareOperator.GE, new BigDecimal("10.00"));

    @Test
    public void testExpire() throws Exception {
        StrategyContainer container = new StrategyContainer();
        TestPriceStrategy testPriceStrategy1 = new TestPriceStrategy(1, MARKET_ID, PRICE_CONDITION);
        TestPriceStrategy testPriceStrategy2 = new TestPriceStrategy(2, MARKET_ID, PRICE_CONDITION,
                LocalDateTime.now().plusMinutes(1));
        container.add(testPriceStrategy1);
        container.add(testPriceStrategy2);

        assertTrue(container.onTimeTick(LocalDateTime.now()).isEmpty());

        testPriceStrategy2.setExpireTime(LocalDateTime.now());
        Collection<SignalPayload> signalPayloads = container.onTimeTick(LocalDateTime.now());
        assertEquals(signalPayloads.size(), 1);
        SignalPayload signalPayload = signalPayloads.iterator().next();
        assertEquals(signalPayload.getSignal(), Signals.expire());
        assertEquals(signalPayload.getStrategy(), testPriceStrategy2);
    }

    @Test
    public void testTriggerLock() throws Exception {
        StrategyContainer container = new StrategyContainer(new StrategyContextConfig(1, 1),
                NopStrategyWriter.INSTANCE);
        TestPriceStrategy testPriceStrategy = new TestPriceStrategy(1, MARKET_ID, PRICE_CONDITION);
        container.add(testPriceStrategy);

        RealTimeMarket realTimeMarket1 = MockMarkets.withCurrentPrice(new BigDecimal("10.00"));
        Collection<SignalPayload> signalPayloads1 = container.onMarketTicks(Collections.singleton(realTimeMarket1));
        assertEquals(signalPayloads1.size(), 1);
        assertEquals(signalPayloads1.iterator().next().getSignal(), Signals.buyOrSell());

        Collection<SignalPayload> signalPayloads2 = container.onMarketTicks(Collections.singleton(realTimeMarket1));
        assertTrue(signalPayloads2.isEmpty());

        Collection<SignalPayload> signalPayloads21 = container.onTimeTick(LocalDateTime.now());
        assertTrue(signalPayloads21.isEmpty());

        TimeUnit.SECONDS.sleep(1);

        Collection<SignalPayload> signalPayloads22 = container.onTimeTick(LocalDateTime.now());
        assertTrue(signalPayloads22.isEmpty());

        Collection<SignalPayload> signalPayloads3 = container.onMarketTicks(Collections.singleton(realTimeMarket1));
        assertEquals(signalPayloads3.size(), 1);
        assertEquals(signalPayloads3.iterator().next().getSignal(), Signals.buyOrSell());
    }

    @Test
    public void testDelaySync() throws Exception {
        StrategyContainer container = new StrategyContainer(new StrategyContextConfig(1, 1),
                NopStrategyWriter.INSTANCE);
        TestTurnUpStrategy testTurnUpStrategy = new TestTurnUpStrategy(1, MARKET_ID,
                                                                       new TurnPointCondition(CompareOperator.LE,
                                                                                              new BigDecimal("10.00"),
                                                                                              BinaryFactorType.PERCENT,
                                                                                              new BigDecimal("1.00"),
                                                                                              null,
                                                                                              false));
        container.add(testTurnUpStrategy);

        RealTimeMarket realTimeMarket1 = MockMarkets.withCurrentPrice(new BigDecimal("9.00"));
        Collection<SignalPayload> signalPayloads1 = container.onMarketTicks(Collections.singleton(realTimeMarket1));
        assertTrue(signalPayloads1.isEmpty());
        assertFalse(testTurnUpStrategy.isDirty());

        assertTrue(container.onTimeTick(LocalDateTime.now()).isEmpty());

        TimeUnit.MILLISECONDS.sleep(500);
        RealTimeMarket realTimeMarket2 = MockMarkets.withCurrentPrice(new BigDecimal("9.01"));
        assertTrue(container.onMarketTicks(Collections.singleton(realTimeMarket2)).isEmpty());

        TimeUnit.MILLISECONDS.sleep(500);
        Collection<SignalPayload> signalPayloads2 = container.onTimeTick(LocalDateTime.now());
        assertEquals(signalPayloads2.size(), 1);
        SignalPayload signalPayload = signalPayloads2.iterator().next();
        assertEquals(signalPayload.getSignal(), Signals.cacheSync());

        assertTrue(container.onMarketTicks(Collections.singleton(realTimeMarket2)).isEmpty());
    }
}
