package hbec.intellitrade.conditionorder.domain.orders.time;

import hbec.intellitrade.common.market.MarketID;
import hbec.intellitrade.common.market.RealTimeMarket;
import hbec.intellitrade.common.market.RealTimeMarketSupplier;
import hbec.intellitrade.common.security.SecurityExchange;
import hbec.intellitrade.common.security.SecurityInfo;
import hbec.intellitrade.common.security.SecurityType;
import hbec.intellitrade.conditionorder.domain.OrderState;
import hbec.intellitrade.conditionorder.domain.TradeCustomerInfo;
import hbec.intellitrade.conditionorder.domain.tradeplan.EntrustStrategy;
import hbec.intellitrade.conditionorder.domain.tradeplan.OfferedPriceTradePlan;
import hbec.intellitrade.conditionorder.domain.tradeplan.TradeNumberByAmount;
import hbec.intellitrade.conditionorder.domain.trigger.BasicTriggerTradingContext;
import hbec.intellitrade.mock.MockMarkets;
import hbec.intellitrade.strategy.domain.signal.Signals;
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
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;
import static org.testng.Assert.assertEquals;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/4/25
 */
public class TimeOrderTest {
    @Mock
    private TradeCustomer tradeCustomer;

    @Mock
    private RealTimeMarketSupplier realTimeMarketSupplier;

    @Mock
    private EntrustOrderWriter entrustOrderWriter;

    @BeforeMethod
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testBasic() throws Exception {
        TimeOrder timeOrder1 = newTimeOrder();
        TimeOrder timeOrder2 = newTimeOrder();
        assertEquals(timeOrder1, timeOrder2);
        assertEquals(timeOrder1.hashCode(), timeOrder2.hashCode());
        System.out.println(timeOrder1);
    }

    private TimeOrder newTimeOrder() {
        return new TimeOrder(123L,
                new TradeCustomerInfo(303348, "010000061086"),
                OrderState.ACTIVE,
                new SecurityInfo(SecurityType.STOCK, "600000", SecurityExchange.SH, "浦发银行"),
                new TimeReachedCondition(LocalDateTime.parse("2018-04-29T10:00:00")),
                LocalDateTime.parse("2018-04-29T15:00:00"),
                new OfferedPriceTradePlan(ExchangeType.BUY,
                        EntrustStrategy.CURRENT_PRICE,
                        new TradeNumberByAmount(new BigDecimal("10000.00"))));
    }

    @Test
    public void test() throws Exception {
        TimeOrder timeOrder = newTimeOrder();
        assertEquals(timeOrder.onTimeTick(LocalDateTime.parse("2018-04-28T10:00:00")), Signals.none());
        assertEquals(timeOrder.onTimeTick(LocalDateTime.parse("2018-04-29T09:59:59")), Signals.none());
        assertEquals(timeOrder.onTimeTick(LocalDateTime.parse("2018-04-29T10:00:00")), Signals.buyOrSell());


        RealTimeMarket realTimeMarket = MockMarkets.withCurrentPrice(new BigDecimal("13.00"));
        when(realTimeMarketSupplier.getCurrentMarket(Matchers.<MarketID>any()))
                .thenReturn(realTimeMarket);

        BasicTriggerTradingContext triggerTradingContext = new BasicTriggerTradingContext(Signals.buyOrSell(),
                timeOrder,
                tradeCustomer,
                realTimeMarketSupplier,
                entrustOrderWriter,
                null);

        EntrustSuccessResult result = new EntrustSuccessResult("OK", 123,
                realTimeMarket.getCurrentPrice());
        when(tradeCustomer.entrust(Matchers.<EntrustCommand>any())).thenReturn(result);

        timeOrder.onTradeSignal(triggerTradingContext);

        verify(tradeCustomer, times(1)).entrust(Matchers.<EntrustCommand>any());
        verify(entrustOrderWriter, times(1)).save(Matchers.<EntrustOrderInfo>any());
    }
}
