package hbec.intellitrade.condorder.domain.orders;

import hbec.intellitrade.common.market.RealTimeMarket;
import hbec.intellitrade.common.market.RealTimeMarketSupplier;
import hbec.intellitrade.common.security.SecurityExchange;
import hbec.intellitrade.common.security.SecurityInfo;
import hbec.intellitrade.common.security.SecurityType;
import hbec.intellitrade.condorder.domain.OrderState;
import hbec.intellitrade.condorder.domain.TradeCustomerInfo;
import hbec.intellitrade.condorder.domain.tradeplan.BasicTradePlan;
import hbec.intellitrade.condorder.domain.tradeplan.EntrustStrategy;
import hbec.intellitrade.condorder.domain.tradeplan.TradeNumberDirect;
import hbec.intellitrade.condorder.domain.trigger.BasicTriggerTradingContext;
import hbec.intellitrade.mock.MockMarkets;
import hbec.intellitrade.strategy.domain.factor.CompareOperator;
import hbec.intellitrade.strategy.domain.signal.Signals;
import hbec.intellitrade.strategy.domain.strategies.condition.PriceCondition;
import hbec.intellitrade.trade.domain.EntrustCommand;
import hbec.intellitrade.trade.domain.EntrustOrderInfo;
import hbec.intellitrade.trade.domain.EntrustOrderWriter;
import hbec.intellitrade.trade.domain.EntrustSuccessResult;
import hbec.intellitrade.trade.domain.ExchangeType;
import hbec.intellitrade.trade.domain.TradeCustomer;
import org.joda.time.LocalDateTime;
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
import static org.testng.Assert.assertEquals;

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
    public void test() throws Exception {
        TradeCustomerInfo tradeCustomerInfo = new TradeCustomerInfo(303348, "010000061086");
        SecurityInfo pfyh = new SecurityInfo(SecurityType.STOCK, "600000", SecurityExchange.SH, "PFYH");
        ExchangeType exchangeType = ExchangeType.BUY;
        PriceOrder priceOrder = new PriceOrder(123L,
                tradeCustomerInfo,
                OrderState.ACTIVE, pfyh,
                new PriceCondition(CompareOperator.LE, new BigDecimal("13.00")),
                null,
                new BasicTradePlan(exchangeType, EntrustStrategy.CURRENT_PRICE, new TradeNumberDirect(100))
        );

        assertEquals(priceOrder.onTimeTick(LocalDateTime.now()), Signals.none());

        assertEquals(priceOrder.onMarketTick(MockMarkets.withCurrentPrice(new BigDecimal("13.01"))), Signals.none());

        final RealTimeMarket realTimeMarket = MockMarkets.withCurrentPrice(new BigDecimal("13.00"));
        assertEquals(priceOrder.onMarketTick(realTimeMarket), Signals.buyOrSell());

        BasicTriggerTradingContext triggerTradingContext = new BasicTriggerTradingContext(Signals.buyOrSell(),
                priceOrder, tradeCustomer, realTimeMarketSupplier, entrustOrderWriter, realTimeMarket);

        when(tradeCustomer.entrust(Matchers.<EntrustCommand>any())).thenReturn(new EntrustSuccessResult("OK",
                123, realTimeMarket.getCurrentPrice()));

        priceOrder.onTradeSignal(triggerTradingContext);

        assertEquals(priceOrder.getOrderState(), OrderState.TERMINATED);
        verify(entrustOrderWriter, times(1)).save(Matchers.<EntrustOrderInfo>any());
    }

    @Test
    public void testBasic() throws Exception {
        TradeCustomerInfo tradeCustomerInfo = new TradeCustomerInfo(303348, "010000061086");
        SecurityInfo pfyh = new SecurityInfo(SecurityType.STOCK, "600000", SecurityExchange.SH, "PFYH");
        ExchangeType exchangeType = ExchangeType.BUY;

        LocalDateTime expireTime = LocalDateTime.now().plusDays(1);
        PriceOrder priceOrder1 = new PriceOrder(123L, tradeCustomerInfo, OrderState.ACTIVE, pfyh,
                new PriceCondition(CompareOperator.LE, new BigDecimal("13.00")),
                expireTime,
                new BasicTradePlan(exchangeType, EntrustStrategy.CURRENT_PRICE, new TradeNumberDirect(100))
        );
        PriceOrder priceOrder2 = new PriceOrder(123L, tradeCustomerInfo, OrderState.ACTIVE, pfyh,
                new PriceCondition(CompareOperator.LE, new BigDecimal("13.00")),
                expireTime,
                new BasicTradePlan(exchangeType, EntrustStrategy.CURRENT_PRICE, new TradeNumberDirect(100))
        );

        assertEquals(priceOrder1, priceOrder2);
        assertEquals(priceOrder1.hashCode(), priceOrder2.hashCode());
    }

    @Test
    public void testExpire() throws Exception {
        TradeCustomerInfo tradeCustomerInfo = new TradeCustomerInfo(303348, "010000061086");
        SecurityInfo pfyh = new SecurityInfo(SecurityType.STOCK, "600000", SecurityExchange.SH, "PFYH");
        ExchangeType exchangeType = ExchangeType.BUY;

        PriceOrder priceOrder = new PriceOrder(123L, tradeCustomerInfo, OrderState.ACTIVE, pfyh,
                new PriceCondition(CompareOperator.LE, new BigDecimal("13.00")),
                LocalDateTime.parse("2018-03-06T10:00:00"),
                new BasicTradePlan(exchangeType, EntrustStrategy.CURRENT_PRICE, new TradeNumberDirect(100))
        );

        assertEquals(priceOrder.onTimeTick(LocalDateTime.parse("2018-03-06T09:59:59")), Signals.none());
        assertEquals(priceOrder.onTimeTick(LocalDateTime.parse("2018-03-06T10:00:00")), Signals.expire());
    }
}
