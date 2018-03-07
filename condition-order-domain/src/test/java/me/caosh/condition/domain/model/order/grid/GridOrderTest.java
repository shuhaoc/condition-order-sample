package me.caosh.condition.domain.model.order.grid;

import hbec.intellitrade.common.market.RealTimeMarket;
import hbec.intellitrade.common.market.RealTimeMarketSupplier;
import hbec.intellitrade.common.security.SecurityExchange;
import hbec.intellitrade.common.security.SecurityInfo;
import hbec.intellitrade.common.security.SecurityType;
import hbec.intellitrade.condorder.domain.OrderState;
import hbec.intellitrade.condorder.domain.TradeCustomerInfo;
import hbec.intellitrade.condorder.domain.tradeplan.DoubleDirectionTradePlan;
import hbec.intellitrade.condorder.domain.tradeplan.EntrustStrategy;
import hbec.intellitrade.condorder.domain.tradeplan.TradePlanFactory;
import hbec.intellitrade.condorder.domain.trigger.BasicTriggerTradingContext;
import hbec.intellitrade.condorder.domain.trigger.TriggerTradingContext;
import hbec.intellitrade.strategy.domain.signal.Signal;
import hbec.intellitrade.strategy.domain.signal.Signals;
import hbec.intellitrade.trade.domain.EntrustOrderWriter;
import me.caosh.condition.domain.model.account.TradeCustomer;
import me.caosh.condition.domain.model.constants.EntrustMethod;
import me.caosh.condition.domain.util.MockMarkets;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;

import static org.testng.Assert.assertEquals;

/**
 * Created by caosh on 2017/8/31.
 *
 * @author caoshuhao@touker.com
 */
public class GridOrderTest {
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
        GridCondition gridCondition = new GridCondition(new BigDecimal("1.00"), new BigDecimal("13.00"));
        DoubleDirectionTradePlan tradePlan = TradePlanFactory.getInstance().createDouble(
                EntrustStrategy.CURRENT_PRICE.getValue(), EntrustMethod.AMOUNT.getValue(), 0, new BigDecimal("4500"));
        GridTradeOrder gridTradeOrder = new GridTradeOrder(123L, tradeCustomerInfo, pfyh, gridCondition,
                tradePlan, OrderState.ACTIVE);

        assertEquals(Signals.none(),
                gridTradeOrder.getCondition().onMarketTick(MockMarkets.withCurrentPrice(new BigDecimal("13.01"))));

        RealTimeMarket realTimeMarket = MockMarkets.withCurrentPrice(new BigDecimal("14.00"));
        Signal signal = gridTradeOrder.getCondition().onMarketTick(realTimeMarket);
        assertEquals(Signals.sell(), signal);
        TradeCustomer tradeCustomer = new TradeCustomer(303348, "010000061086");
        TriggerTradingContext triggerTradingContext = new BasicTriggerTradingContext(signal, gridTradeOrder, tradeCustomer,
                realTimeMarketSupplier, entrustOrderWriter, realTimeMarket);

        gridTradeOrder.onTradeSignal(triggerTradingContext);
        gridTradeOrder.afterEntrustCommandsExecuted(triggerTradingContext);
        assertEquals(gridTradeOrder.getOrderState(), OrderState.ACTIVE);
        assertEquals(gridTradeOrder.getGridCondition().getBasePrice(), new BigDecimal("14.00"));

       RealTimeMarket realTimeMarket2 = MockMarkets.withCurrentPrice(new BigDecimal("13.00"));
        signal = gridTradeOrder.getCondition().onMarketTick(realTimeMarket2);
        assertEquals(Signals.buy(), signal);
        triggerTradingContext = new BasicTriggerTradingContext(signal, gridTradeOrder, tradeCustomer,
                realTimeMarketSupplier, entrustOrderWriter, realTimeMarket2);

        gridTradeOrder.onTradeSignal(triggerTradingContext);
        assertEquals(gridTradeOrder.getOrderState(), OrderState.ACTIVE);
        assertEquals(gridTradeOrder.getGridCondition().getBasePrice(), new BigDecimal("13.00"));
    }
}
