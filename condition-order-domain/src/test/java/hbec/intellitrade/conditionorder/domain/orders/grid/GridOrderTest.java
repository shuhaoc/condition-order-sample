package hbec.intellitrade.conditionorder.domain.orders.grid;

import hbec.intellitrade.common.market.RealTimeMarket;
import hbec.intellitrade.common.market.RealTimeMarketSupplier;
import hbec.intellitrade.common.security.SecurityExchange;
import hbec.intellitrade.common.security.SecurityInfo;
import hbec.intellitrade.common.security.SecurityType;
import hbec.intellitrade.conditionorder.domain.OrderState;
import hbec.intellitrade.conditionorder.domain.TradeCustomerInfo;
import hbec.intellitrade.conditionorder.domain.tradeplan.EntrustStrategy;
import hbec.intellitrade.conditionorder.domain.tradeplan.OfferedPriceBidirectionalTradePlan;
import hbec.intellitrade.conditionorder.domain.tradeplan.TradeNumberByAmount;
import hbec.intellitrade.conditionorder.domain.trigger.BasicTriggerTradingContext;
import hbec.intellitrade.conditionorder.domain.trigger.TriggerTradingContext;
import hbec.intellitrade.mock.MockMarkets;
import hbec.intellitrade.strategy.domain.condition.delayconfirm.DisabledDelayConfirm;
import hbec.intellitrade.strategy.domain.condition.deviation.DisabledDeviationCtrl;
import hbec.intellitrade.strategy.domain.factor.BinaryFactorType;
import hbec.intellitrade.strategy.domain.factor.CompareOperator;
import hbec.intellitrade.strategy.domain.factor.PercentBinaryTargetPriceFactor;
import hbec.intellitrade.strategy.domain.signal.Signal;
import hbec.intellitrade.strategy.domain.signal.Signals;
import hbec.intellitrade.trade.domain.EntrustOrderWriter;
import hbec.intellitrade.trade.domain.TradeCustomer;
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
    public void test() throws Exception {
        TradeCustomerInfo tradeCustomerInfo = new TradeCustomerInfo(303348, "010000061086");
        SecurityInfo pfyh = new SecurityInfo(SecurityType.STOCK, "600000", SecurityExchange.SH, "PFYH");
        BigDecimal basePrice = new BigDecimal("10.00");
        DecoratedGridCondition gridCondition = new DecoratedGridCondition(basePrice,
                BinaryFactorType.PERCENT,
                new SimpleSubCondition(new PercentBinaryTargetPriceFactor(
                        CompareOperator.LE, new BigDecimal("-3.00")),
                        basePrice),
                new SimpleSubCondition(new PercentBinaryTargetPriceFactor(
                        CompareOperator.GE, new BigDecimal("3.00")),
                        basePrice),
                true,
                DisabledDelayConfirm.DISABLED,
                DisabledDeviationCtrl.DISABLED,
                0,
                0);
        OfferedPriceBidirectionalTradePlan tradePlan = new OfferedPriceBidirectionalTradePlan(
                new TradeNumberByAmount(new BigDecimal("4500")),
                EntrustStrategy.CURRENT_PRICE,
                EntrustStrategy.CURRENT_PRICE);
        GridTradeOrder gridTradeOrder = new GridTradeOrder(123L, tradeCustomerInfo, pfyh, gridCondition,
                null, tradePlan, OrderState.ACTIVE);

        assertEquals(gridTradeOrder.getCondition().onMarketTick(MockMarkets.withCurrentPrice(new BigDecimal("10.00"))),
                Signals.none());

        RealTimeMarket realTimeMarket = MockMarkets.withCurrentPrice(new BigDecimal("11.00"));
        Signal signal = gridTradeOrder.getCondition().onMarketTick(realTimeMarket);
        assertEquals(Signals.sell(), signal);
        TriggerTradingContext triggerTradingContext = new BasicTriggerTradingContext(signal,
                gridTradeOrder,
                tradeCustomer,
                realTimeMarketSupplier,
                entrustOrderWriter,
                realTimeMarket);

        gridTradeOrder.onTradeSignal(triggerTradingContext);
        assertEquals(gridTradeOrder.getOrderState(), OrderState.ACTIVE);
        assertEquals(gridTradeOrder.getCondition().getBasePrice(), new BigDecimal("11.00"));

        RealTimeMarket realTimeMarket2 = MockMarkets.withCurrentPrice(new BigDecimal("10.00"));
        signal = gridTradeOrder.getCondition().onMarketTick(realTimeMarket2);
        assertEquals(signal, Signals.buy());
        triggerTradingContext = new BasicTriggerTradingContext(signal, gridTradeOrder, tradeCustomer,
                realTimeMarketSupplier, entrustOrderWriter, realTimeMarket2);

        gridTradeOrder.onTradeSignal(triggerTradingContext);
        assertEquals(gridTradeOrder.getOrderState(), OrderState.ACTIVE);
        assertEquals(gridTradeOrder.getCondition().getBasePrice(), new BigDecimal("10.00"));
    }
}
