package hbec.intellitrade.condorder.domain.orders.price;

import hbec.intellitrade.common.market.MarketID;
import hbec.intellitrade.common.market.MarketType;
import hbec.intellitrade.common.market.RealTimeMarket;
import hbec.intellitrade.common.market.RealTimeMarketSupplier;
import hbec.intellitrade.common.market.index.IndexSource;
import hbec.intellitrade.common.security.SecurityExchange;
import hbec.intellitrade.common.security.SecurityInfo;
import hbec.intellitrade.common.security.SecurityType;
import hbec.intellitrade.condorder.domain.OrderState;
import hbec.intellitrade.condorder.domain.TradeCustomerInfo;
import hbec.intellitrade.condorder.domain.trackindex.NoneTrackedIndex;
import hbec.intellitrade.condorder.domain.trackindex.TrackIndexOption;
import hbec.intellitrade.condorder.domain.trackindex.TrackedIndexInfo;
import hbec.intellitrade.condorder.domain.tradeplan.BasicTradePlan;
import hbec.intellitrade.condorder.domain.tradeplan.EntrustStrategy;
import hbec.intellitrade.condorder.domain.tradeplan.TradeNumberDirect;
import hbec.intellitrade.condorder.domain.trigger.BasicTriggerTradingContext;
import hbec.intellitrade.mock.MockMarkets;
import hbec.intellitrade.strategy.domain.condition.delayconfirm.DelayConfirmInfo;
import hbec.intellitrade.strategy.domain.condition.delayconfirm.DelayConfirmOption;
import hbec.intellitrade.strategy.domain.condition.delayconfirm.DisabledDelayConfirm;
import hbec.intellitrade.strategy.domain.condition.deviation.DeviationCtrlInfo;
import hbec.intellitrade.strategy.domain.condition.deviation.DisabledDeviationCtrl;
import hbec.intellitrade.strategy.domain.factor.CompareOperator;
import hbec.intellitrade.strategy.domain.shared.Week;
import hbec.intellitrade.strategy.domain.signal.Signals;
import hbec.intellitrade.strategy.domain.timerange.LocalTimeRange;
import hbec.intellitrade.strategy.domain.timerange.NoneMonitorTimeRange;
import hbec.intellitrade.strategy.domain.timerange.WeekRange;
import hbec.intellitrade.strategy.domain.timerange.WeekTimeRange;
import hbec.intellitrade.trade.domain.EntrustCommand;
import hbec.intellitrade.trade.domain.EntrustOrderInfo;
import hbec.intellitrade.trade.domain.EntrustOrderWriter;
import hbec.intellitrade.trade.domain.EntrustSuccessResult;
import hbec.intellitrade.trade.domain.ExchangeType;
import hbec.intellitrade.trade.domain.OrderType;
import hbec.intellitrade.trade.domain.TradeCustomer;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.testng.Assert.*;

/**
 * Created by caosh on 2017/8/31.
 *
 * @author caoshuhao@touker.com
 */
public class PriceOrderTest {
    @Mock
    private TradeCustomer tradeCustomer;

    @Mock
    private RealTimeMarketSupplier realTimeMarketSupplier;

    @Mock
    private EntrustOrderWriter entrustOrderWriter;

    @BeforeMethod
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                System.out.println("Save entrust order: " + Arrays.toString(invocationOnMock.getArguments()));
                return null;
            }
        }).when(entrustOrderWriter).save(Matchers.<EntrustOrderInfo>any());
    }

    @Test
    public void testBasic() throws Exception {
        TradeCustomerInfo tradeCustomerInfo = new TradeCustomerInfo(303348, "010000061086");
        SecurityInfo pfyh = new SecurityInfo(SecurityType.STOCK, "600000", SecurityExchange.SH, "PFYH");
        ExchangeType exchangeType = ExchangeType.BUY;

        PriceOrder priceOrder1 = new PriceOrder(123L, tradeCustomerInfo, OrderState.ACTIVE, pfyh,
                                                new PriceCondition(CompareOperator.LE, new BigDecimal("13.00")),
                                                new BasicTradePlan(exchangeType,
                                                                   EntrustStrategy.CURRENT_PRICE,
                                                                   new TradeNumberDirect(100))
        );
        PriceOrder priceOrder2 = new PriceOrder(123L, tradeCustomerInfo, OrderState.ACTIVE, pfyh,
                                                new PriceCondition(CompareOperator.LE, new BigDecimal("13.00")),
                                                new BasicTradePlan(exchangeType,
                                                                   EntrustStrategy.CURRENT_PRICE,
                                                                   new TradeNumberDirect(100))
        );

        System.out.println(priceOrder1);
        assertEquals(priceOrder1, priceOrder2);
        assertEquals(priceOrder1.hashCode(), priceOrder2.hashCode());
    }

    @Test
    public void testConditionAndTrigger() throws Exception {
        TradeCustomerInfo tradeCustomerInfo = new TradeCustomerInfo(303348, "010000061086");
        SecurityInfo pfyh = new SecurityInfo(SecurityType.STOCK, "600000", SecurityExchange.SH, "PFYH");
        ExchangeType exchangeType = ExchangeType.BUY;
        PriceOrder priceOrder = new PriceOrder(123L,
                                               tradeCustomerInfo,
                                               OrderState.ACTIVE, pfyh,
                                               new PriceCondition(CompareOperator.LE, new BigDecimal("13.00")),
                                               new BasicTradePlan(exchangeType,
                                                                  EntrustStrategy.CURRENT_PRICE,
                                                                  new TradeNumberDirect(100))
        );
        assertEquals(priceOrder.getTrackedIndex(), NoneTrackedIndex.NONE);

        assertEquals(priceOrder.onTimeTick(LocalDateTime.now()), Signals.none());

        assertEquals(priceOrder.onMarketTick(MockMarkets.withCurrentPrice(new BigDecimal("13.01"))), Signals.none());

        final RealTimeMarket realTimeMarket = MockMarkets.withCurrentPrice(new BigDecimal("13.00"));
        assertEquals(priceOrder.onMarketTick(realTimeMarket), Signals.buyOrSell());

        BasicTriggerTradingContext triggerTradingContext = new BasicTriggerTradingContext(Signals.buyOrSell(),
                                                                                          priceOrder,
                                                                                          tradeCustomer,
                                                                                          realTimeMarketSupplier,
                                                                                          entrustOrderWriter,
                                                                                          realTimeMarket);

        EntrustSuccessResult result = new EntrustSuccessResult("OK",
                                                               123,
                                                               realTimeMarket.getCurrentPrice());
        when(tradeCustomer.entrust(Matchers.<EntrustCommand>any())).thenReturn(result);

        priceOrder.onTradeSignal(triggerTradingContext);

        EntrustCommand entrustCommand = new EntrustCommand(pfyh,
                                                           exchangeType,
                                                           realTimeMarket.getCurrentPrice(),
                                                           100,
                                                           OrderType.LIMITED);

        EntrustOrderInfo entrustOrderInfo = new EntrustOrderInfo(priceOrder.getOrderId(),
                                                                 tradeCustomerInfo,
                                                                 entrustCommand,
                                                                 result);

        assertEquals(priceOrder.getOrderState(), OrderState.TERMINATED);
        verify(tradeCustomer, times(1)).entrust(eq(entrustCommand));
        verify(entrustOrderWriter, times(1)).save(eq(entrustOrderInfo));
    }

    @Test
    public void testExpire() throws Exception {
        TradeCustomerInfo tradeCustomerInfo = new TradeCustomerInfo(303348, "010000061086");
        SecurityInfo pfyh = new SecurityInfo(SecurityType.STOCK, "600000", SecurityExchange.SH, "PFYH");
        ExchangeType exchangeType = ExchangeType.BUY;

        PriceOrder priceOrder = new PriceOrder(123L, tradeCustomerInfo, OrderState.ACTIVE, pfyh,
                                               new DecoratedPriceCondition(
                                                       new PriceCondition(CompareOperator.LE, new BigDecimal("13.00")),
                                                       DisabledDelayConfirm.DISABLED,
                                                       DisabledDeviationCtrl.DISABLED,
                                                       0),
                                               LocalDateTime.parse("2018-03-06T10:00:00"),
                                               new BasicTradePlan(exchangeType,
                                                                  EntrustStrategy.CURRENT_PRICE,
                                                                  new TradeNumberDirect(100)),
                                               NoneTrackedIndex.NONE,
                                               NoneMonitorTimeRange.NONE
        );

        assertEquals(priceOrder.onTimeTick(LocalDateTime.parse("2018-03-06T09:59:59")), Signals.none());
        assertEquals(priceOrder.onTimeTick(LocalDateTime.parse("2018-03-06T10:00:00")), Signals.expire());

        priceOrder.onExpired();

        assertEquals(priceOrder.getOrderState(), OrderState.EXPIRED);
    }

    @Test
    public void testDelayConfirmParam() throws Exception {
        TradeCustomerInfo tradeCustomerInfo = new TradeCustomerInfo(303348, "010000061086");
        SecurityInfo pfyh = new SecurityInfo(SecurityType.STOCK, "600000", SecurityExchange.SH, "PFYH");

        PriceOrder priceOrder1 = new PriceOrder(123L,
                                                tradeCustomerInfo,
                                                OrderState.ACTIVE,
                                                pfyh,
                                                new PriceCondition(CompareOperator.LE, new BigDecimal("13.00")),
                                                new BasicTradePlan(ExchangeType.BUY,
                                                                   EntrustStrategy.CURRENT_PRICE,
                                                                   new TradeNumberDirect(100))
        );

        assertEquals(((DecoratedPriceCondition) priceOrder1.getCondition()).getDelayConfirm(),
                     DisabledDelayConfirm.DISABLED);

        PriceOrder priceOrder2 = new PriceOrder(123L, tradeCustomerInfo,
                                                OrderState.ACTIVE,
                                                pfyh,
                                                new DecoratedPriceCondition(
                                                        new PriceCondition(CompareOperator.LE, new BigDecimal("13.00")),
                                                        new DelayConfirmInfo(DelayConfirmOption.ACCUMULATE, 3),
                                                        DisabledDeviationCtrl.DISABLED,
                                                        0),
                                                LocalDateTime.now().plusDays(1),
                                                new BasicTradePlan(ExchangeType.BUY,
                                                                   EntrustStrategy.CURRENT_PRICE,
                                                                   new TradeNumberDirect(100)),
                                                NoneTrackedIndex.NONE,
                                                NoneMonitorTimeRange.NONE);

        assertEquals(((DecoratedPriceCondition) priceOrder2.getCondition()).getDelayConfirm(),
                     new DelayConfirmInfo(DelayConfirmOption.ACCUMULATE, 3));
    }

    @Test
    public void testDelayConfirmCondition() throws Exception {
        PriceOrder priceOrder = new PriceOrder(123L, new TradeCustomerInfo(303348, "010000061086"),
                                               OrderState.ACTIVE,
                                               new SecurityInfo(SecurityType.STOCK,
                                                                "600000",
                                                                SecurityExchange.SH,
                                                                "PFYH"),
                                               new DecoratedPriceCondition(
                                                       new PriceCondition(CompareOperator.LE, new BigDecimal("13.00")),

                                                       new DelayConfirmInfo(DelayConfirmOption.ACCUMULATE, 3),
                                                       DisabledDeviationCtrl.DISABLED,
                                                       0),
                                               LocalDateTime.now().plusDays(1),
                                               new BasicTradePlan(ExchangeType.BUY,
                                                                  EntrustStrategy.CURRENT_PRICE,
                                                                  new TradeNumberDirect(100)),
                                               NoneTrackedIndex.NONE,
                                               NoneMonitorTimeRange.NONE);

        assertEquals(priceOrder.onMarketTick(MockMarkets.withCurrentPrice(new BigDecimal("13.00"))), Signals.none());
        assertTrue(priceOrder.isDirty());
        assertFalse(priceOrder.isPersistentPropertyDirty());
        priceOrder.clearDirty();
        assertFalse(priceOrder.isDirty());

        assertEquals(priceOrder.onMarketTick(MockMarkets.withCurrentPrice(new BigDecimal("13.00"))), Signals.none());

        // false condition
        assertEquals(priceOrder.onMarketTick(MockMarkets.withCurrentPrice(new BigDecimal("14.00"))), Signals.none());

        assertEquals(priceOrder.onMarketTick(MockMarkets.withCurrentPrice(new BigDecimal("13.00"))),
                     Signals.buyOrSell());
        priceOrder.onMarketClosed(LocalDateTime.now());
    }

    @Test
    public void testMonitorTimeRange() throws Exception {
        PriceOrder priceOrder = new PriceOrder(123L, new TradeCustomerInfo(303348, "010000061086"),
                                               OrderState.ACTIVE,
                                               new SecurityInfo(SecurityType.STOCK,
                                                                "600000",
                                                                SecurityExchange.SH,
                                                                "PFYH"),
                                               new DecoratedPriceCondition(
                                                       new PriceCondition(CompareOperator.LE, new BigDecimal("13.00")),
                                                       DisabledDelayConfirm.DISABLED,
                                                       DisabledDeviationCtrl.DISABLED,
                                                       0),
                                               LocalDateTime.now().plusDays(1),
                                               new BasicTradePlan(ExchangeType.BUY,
                                                                  EntrustStrategy.CURRENT_PRICE,
                                                                  new TradeNumberDirect(100)),
                                               NoneTrackedIndex.NONE,
                                               new WeekTimeRange(new WeekRange(Week.TUE, Week.THU),
                                                                 new LocalTimeRange(LocalTime.parse("10:00:00"),
                                                                                    LocalTime.parse("11:00:00"))));

        RealTimeMarket realTimeMarket1 = MockMarkets.builderWithCurrentPrice(new BigDecimal("12.99"))
                                                    .setArriveTime(LocalDateTime.parse("2018-03-12T09:30:00"))
                                                    .build();
        assertEquals(priceOrder.onMarketTick(realTimeMarket1), Signals.none());

        RealTimeMarket realTimeMarket2 = MockMarkets.builderWithCurrentPrice(new BigDecimal("12.99"))
                                                    .setArriveTime(LocalDateTime.parse("2018-03-14T10:30:00"))
                                                    .build();
        assertEquals(priceOrder.onMarketTick(realTimeMarket2), Signals.buyOrSell());
    }

    @Test
    public void testDeviationCtrl() throws Exception {
        PriceOrder priceOrder = new PriceOrder(123L, new TradeCustomerInfo(303348, "010000061086"),
                                               OrderState.ACTIVE,
                                               new SecurityInfo(SecurityType.STOCK,
                                                                "600000",
                                                                SecurityExchange.SH,
                                                                "PFYH"),
                                               new DecoratedPriceCondition(
                                                       new PriceCondition(CompareOperator.LE, new BigDecimal("10.00")),
                                                       DisabledDelayConfirm.DISABLED,
                                                       new DeviationCtrlInfo(new BigDecimal("1")),
                                                       0),
                                               LocalDateTime.now().plusDays(1),
                                               new BasicTradePlan(ExchangeType.BUY,
                                                                  EntrustStrategy.CURRENT_PRICE,
                                                                  new TradeNumberDirect(100)),
                                               NoneTrackedIndex.NONE,
                                               NoneMonitorTimeRange.NONE);

        System.out.println(priceOrder);

        assertEquals(priceOrder.onMarketTick(MockMarkets.withCurrentPrice(new BigDecimal("9.99"))),
                     Signals.buyOrSell());

        assertEquals(priceOrder.onMarketTick(MockMarkets.withCurrentPrice(new BigDecimal("9.90"))),
                     Signals.buyOrSell().withDeviationExceeded());
    }

    @Test
    public void testDeviationCtrlWithDelayConfirm() throws Exception {
        PriceOrder priceOrder = new PriceOrder(123L, new TradeCustomerInfo(303348, "010000061086"),
                                               OrderState.ACTIVE,
                                               new SecurityInfo(SecurityType.STOCK,
                                                                "600000",
                                                                SecurityExchange.SH,
                                                                "PFYH"),
                                               new DecoratedPriceCondition(
                                                       new PriceCondition(CompareOperator.LE, new BigDecimal("10.00")),
                                                       new DelayConfirmInfo(DelayConfirmOption.CONTINUOUS, 2),
                                                       new DeviationCtrlInfo(new BigDecimal("1")),
                                                       0),
                                               LocalDateTime.now().plusDays(1),
                                               new BasicTradePlan(ExchangeType.BUY,
                                                                  EntrustStrategy.CURRENT_PRICE,
                                                                  new TradeNumberDirect(100)),
                                               NoneTrackedIndex.NONE,
                                               NoneMonitorTimeRange.NONE);

        System.out.println(priceOrder);

        assertEquals(priceOrder.onMarketTick(MockMarkets.withCurrentPrice(new BigDecimal("9.99"))),
                     Signals.none());
        assertEquals(priceOrder.onMarketTick(MockMarkets.withCurrentPrice(new BigDecimal("9.99"))),
                     Signals.buyOrSell());

        priceOrder.onMarketClosed(LocalDateTime.now());

        assertEquals(priceOrder.onMarketTick(MockMarkets.withCurrentPrice(new BigDecimal("9.90"))),
                     Signals.none());
        assertEquals(priceOrder.onMarketTick(MockMarkets.withCurrentPrice(new BigDecimal("9.90"))),
                     Signals.buyOrSell().withDeviationExceeded());
    }

    @Test
    public void testTrackIndex() throws Exception {
        PriceOrder priceOrder = new PriceOrder(123L,
                                               new TradeCustomerInfo(303348, "010000061086"),
                                               OrderState.ACTIVE,
                                               new SecurityInfo(SecurityType.STOCK,
                                                                "600000",
                                                                SecurityExchange.SH,
                                                                "PFYH"),
                                               new DecoratedPriceCondition(
                                                       new PriceCondition(CompareOperator.LE, new BigDecimal("10.00")),
                                                       new DelayConfirmInfo(DelayConfirmOption.CONTINUOUS, 2),
                                                       new DeviationCtrlInfo(new BigDecimal("1")),
                                                       0),
                                               LocalDateTime.now().plusDays(1),
                                               new BasicTradePlan(ExchangeType.BUY,
                                                                  EntrustStrategy.CURRENT_PRICE,
                                                                  new TradeNumberDirect(100)),
                                               new TrackedIndexInfo(IndexSource.SZ, "399001", "深证成指"),
                                               NoneMonitorTimeRange.NONE);

        assertEquals(priceOrder.getTrackIndexOption(), TrackIndexOption.ENABLED);
        assertEquals(priceOrder.getTrackedIndex(), new TrackedIndexInfo(IndexSource.SZ, "399001", "深证成指"));

        assertEquals(priceOrder.getTrackMarketID(), new MarketID(MarketType.INDEX, "399001"));
    }
}
