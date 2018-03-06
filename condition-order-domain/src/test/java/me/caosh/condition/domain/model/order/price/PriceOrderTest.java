package me.caosh.condition.domain.model.order.price;

import hbec.intellitrade.common.market.RealTimeMarket;
import hbec.intellitrade.common.security.SecurityExchange;
import hbec.intellitrade.common.security.SecurityInfo;
import hbec.intellitrade.common.security.SecurityType;
import hbec.intellitrade.strategy.domain.factor.CompareOperator;
import me.caosh.condition.domain.model.account.TradeCustomer;
import me.caosh.condition.domain.model.condition.PriceCondition;
import me.caosh.condition.domain.model.order.BasicTriggerTradingContext;
import me.caosh.condition.domain.model.order.TradeCustomerInfo;
import me.caosh.condition.domain.model.order.constant.EntrustStrategy;
import me.caosh.condition.domain.model.order.constant.ExchangeType;
import me.caosh.condition.domain.model.order.constant.StrategyState;
import me.caosh.condition.domain.model.order.plan.BasicTradePlan;
import me.caosh.condition.domain.model.order.plan.TradeNumberDirect;
import me.caosh.condition.domain.model.signal.Signal;
import me.caosh.condition.domain.model.signal.Signals;
import me.caosh.condition.domain.model.trade.EntrustOrderInfo;
import me.caosh.condition.domain.model.trade.EntrustOrderWriter;
import me.caosh.condition.domain.service.RealTimeMarketSupplier;
import me.caosh.condition.domain.util.MockMarkets;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.testng.Assert.assertEquals;

/**
 * Created by caosh on 2017/8/31.
 *
 * @author caoshuhao@touker.com
 */
public class PriceOrderTest {
    @Mock
    private RealTimeMarketSupplier realTimeMarketSupplier;

    @Mock
    private EntrustOrderWriter entrustOrderWriter;

    @BeforeMethod
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void test() throws Exception {
        TradeCustomerInfo tradeCustomerInfo = new TradeCustomerInfo(303348, "010000061086");
        SecurityInfo pfyh = new SecurityInfo(SecurityType.STOCK, "600000", SecurityExchange.SH, "PFYH");
        ExchangeType exchangeType = ExchangeType.BUY;
        PriceOrder priceOrder = new PriceOrder(123L, tradeCustomerInfo, pfyh,
                new PriceCondition(CompareOperator.LE, new BigDecimal("13.00")),
                new BasicTradePlan(exchangeType, EntrustStrategy.CURRENT_PRICE, new TradeNumberDirect(100)),
                StrategyState.ACTIVE);

        assertEquals(priceOrder.getCondition().onMarketTick(MockMarkets.withCurrentPrice(new BigDecimal("13.01"))),
                Signals.none());

        final RealTimeMarket realTimeMarket = MockMarkets.withCurrentPrice(new BigDecimal("13.00"));
        Signal signal = priceOrder.getCondition().onMarketTick(realTimeMarket);
        assertEquals(signal, Signals.buyOrSell());

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                System.out.println("Save entrust order: " + Arrays.toString(invocationOnMock.getArguments()));
                return null;
            }
        }).when(entrustOrderWriter).save(Matchers.<EntrustOrderInfo>any());

        TradeCustomer tradeCustomer = new TradeCustomer(303348, "010000061086");
        BasicTriggerTradingContext triggerTradingContext = new BasicTriggerTradingContext(signal, priceOrder, tradeCustomer,
                realTimeMarketSupplier, entrustOrderWriter, realTimeMarket);

        priceOrder.onTradeSignal(triggerTradingContext);

//        List<EntrustCommand> entrustCommands = priceOrder.createEntrustCommands((TradeSignal) signal,
//                triggerTradingContext);
//        assertEquals(entrustCommands, Collections.singletonList(new EntrustCommand(pfyh, exchangeType, new BigDecimal("13.00"),
//                100, OrderType.LIMITED)));
//
//        priceOrder.afterEntrustSuccess(triggerTradingContext, entrustCommands.get(0), EntrustResult.ofSuccess("OK", 98));
//        priceOrder.afterEntrustCommandsExecuted(triggerTradingContext);
        assertEquals(priceOrder.getStrategyState(), StrategyState.TERMINATED);
        verify(entrustOrderWriter, times(1)).save(Matchers.<EntrustOrderInfo>any());
    }

    @Test
    public void testBasic() throws Exception {
        TradeCustomerInfo tradeCustomerInfo = new TradeCustomerInfo(303348, "010000061086");
        SecurityInfo pfyh = new SecurityInfo(SecurityType.STOCK, "600000", SecurityExchange.SH, "PFYH");
        ExchangeType exchangeType = ExchangeType.BUY;

        PriceOrder priceOrder1 = new PriceOrder(123L, tradeCustomerInfo, pfyh,
                new PriceCondition(CompareOperator.LE, new BigDecimal("13.00")),
                new BasicTradePlan(exchangeType, EntrustStrategy.CURRENT_PRICE, new TradeNumberDirect(100)),
                StrategyState.ACTIVE);
        PriceOrder priceOrder2 = new PriceOrder(123L, tradeCustomerInfo, pfyh,
                new PriceCondition(CompareOperator.LE, new BigDecimal("13.00")),
                new BasicTradePlan(exchangeType, EntrustStrategy.CURRENT_PRICE, new TradeNumberDirect(100)),
                StrategyState.ACTIVE);

        assertEquals(priceOrder1, priceOrder2);
        assertEquals(priceOrder1.hashCode(), priceOrder2.hashCode());
    }
}
