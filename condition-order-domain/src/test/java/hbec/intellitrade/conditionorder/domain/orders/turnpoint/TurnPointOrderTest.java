package hbec.intellitrade.conditionorder.domain.orders.turnpoint;

import hbec.intellitrade.common.security.SecurityExchange;
import hbec.intellitrade.common.security.SecurityInfo;
import hbec.intellitrade.common.security.SecurityType;
import hbec.intellitrade.conditionorder.domain.OrderState;
import hbec.intellitrade.conditionorder.domain.TradeCustomerInfo;
import hbec.intellitrade.conditionorder.domain.strategyinfo.NativeStrategyInfo;
import hbec.intellitrade.conditionorder.domain.trackindex.NoneTrackedIndex;
import hbec.intellitrade.conditionorder.domain.tradeplan.EntrustStrategy;
import hbec.intellitrade.conditionorder.domain.tradeplan.MarketPriceTradePlan;
import hbec.intellitrade.conditionorder.domain.tradeplan.OfferedPriceTradePlan;
import hbec.intellitrade.conditionorder.domain.tradeplan.TradeNumberByAmount;
import hbec.intellitrade.conditionorder.domain.tradeplan.TradeNumberDirect;
import hbec.intellitrade.mock.MockMarkets;
import hbec.intellitrade.strategy.domain.condition.delayconfirm.DelayConfirmInfo;
import hbec.intellitrade.strategy.domain.condition.delayconfirm.DelayConfirmOption;
import hbec.intellitrade.strategy.domain.condition.delayconfirm.DisabledDelayConfirm;
import hbec.intellitrade.strategy.domain.condition.deviation.DeviationCtrlInfo;
import hbec.intellitrade.strategy.domain.factor.BinaryFactorType;
import hbec.intellitrade.strategy.domain.factor.CompareOperator;
import hbec.intellitrade.strategy.domain.signal.Signals;
import hbec.intellitrade.strategy.domain.timerange.NoneMonitorTimeRange;
import hbec.intellitrade.trade.domain.ExchangeType;
import hbec.intellitrade.trade.domain.OrderType;
import org.joda.time.LocalDateTime;
import org.testng.annotations.Test;

import java.math.BigDecimal;

import static org.testng.Assert.*;

/**
 * @author caoshuhao@touker.com
 * @date 2018/4/13
 */
public class TurnPointOrderTest {
    @Test
    public void testBasic() throws Exception {
        TradeCustomerInfo tradeCustomerInfo = new TradeCustomerInfo(303348, "010000061086");
        SecurityInfo pfyh = new SecurityInfo(SecurityType.STOCK, "600000", SecurityExchange.SH, "PFYH");
        TurnPointOrder turnPointOrder1 = new TurnPointOrder(123L,
                                                            tradeCustomerInfo,
                                                            OrderState.ACTIVE,
                                                            pfyh,
                                                            new TurnPointCondition(CompareOperator.LE,
                                                                                   new BigDecimal("11.00"),
                                                                                   BinaryFactorType.INCREMENT,
                                                                                   null,
                                                                                   new BigDecimal("0.1"),
                                                                                   true),
                                                            new MarketPriceTradePlan(ExchangeType.BUY,
                                                                                     new TradeNumberDirect(200),
                                                                                     OrderType.SH_WDJSCJSYCX));
        TurnPointOrder turnPointOrder2 = new TurnPointOrder(123L,
                                                            tradeCustomerInfo,
                                                            OrderState.ACTIVE,
                                                            pfyh,
                                                            new TurnPointCondition(CompareOperator.LE,
                                                                                   new BigDecimal("11.00"),
                                                                                   BinaryFactorType.INCREMENT,
                                                                                   null,
                                                                                   new BigDecimal("0.1"),
                                                                                   true),
                                                            new MarketPriceTradePlan(ExchangeType.BUY,
                                                                                     new TradeNumberDirect(200),
                                                                                     OrderType.SH_WDJSCJSYCX));
        assertEquals(turnPointOrder1, turnPointOrder2);
        assertEquals(turnPointOrder1.hashCode(), turnPointOrder2.hashCode());
        assertEquals(turnPointOrder1.getStrategyInfo(), NativeStrategyInfo.TURN_POINT);
    }

    @Test
    public void testToString() throws Exception {
        TurnPointOrder turnPointOrder = newTurnPointOrder();
        System.out.println(turnPointOrder);
    }

    @Test
    public void testDirtyFlag() throws Exception {
        TurnPointOrder turnPointOrder = newTurnPointOrder();

        // 突破
        assertEquals(turnPointOrder.onMarketTick(MockMarkets.withCurrentPrice("10.00")), Signals.none());
        assertTrue(turnPointOrder.isDirty());
        assertTrue(turnPointOrder.isPersistentPropertyDirty());
        turnPointOrder.clearDirty();

        // 延迟确认穿越底线
        assertEquals(turnPointOrder.onMarketTick(MockMarkets.withCurrentPrice("8.00")), Signals.none());

        assertTrue(turnPointOrder.isDirty());
        // 最低价更新
        assertTrue(turnPointOrder.isPersistentPropertyDirty());
        turnPointOrder.clearDirty();

        // 穿越底线确认次数增加
        assertEquals(turnPointOrder.onMarketTick(MockMarkets.withCurrentPrice("8.01")), Signals.none());
        assertTrue(turnPointOrder.isDirty());
        assertFalse(turnPointOrder.isPersistentPropertyDirty());

        turnPointOrder.onMarketClosed(LocalDateTime.now());
        assertTrue(turnPointOrder.isDirty());
        assertFalse(turnPointOrder.isPersistentPropertyDirty());
        System.out.println(turnPointOrder);
    }

    @Test
    public void testCrossBaseline() throws Exception {
        TradeCustomerInfo tradeCustomerInfo = new TradeCustomerInfo(303348, "010000061086");
        SecurityInfo pfyh = new SecurityInfo(SecurityType.STOCK, "600000", SecurityExchange.SH, "PFYH");
        TurnPointOrder turnPointOrder = new TurnPointOrder(
                123L,
                tradeCustomerInfo,
                OrderState.ACTIVE,
                pfyh,
                new DecoratedTurnPointCondition(
                        new TurnPointCondition(CompareOperator.LE,
                                               new BigDecimal("11.00"),
                                               BinaryFactorType.PERCENT,
                                               new BigDecimal("1.00"),
                                               null,
                                               true),
                        new BigDecimal("9.00"),
                        DisabledDelayConfirm.DISABLED,
                        new DeviationCtrlInfo(new BigDecimal("0.5")),
                        0,
                        0),
                null,
                new OfferedPriceTradePlan(ExchangeType.BUY,
                                          EntrustStrategy.SELL1,
                                          new TradeNumberByAmount(new BigDecimal("10000.00"))),
                NoneTrackedIndex.NONE,
                NoneMonitorTimeRange.NONE
        );

        assertEquals(turnPointOrder.onMarketTick(MockMarkets.withCurrentPrice("8.00")), Signals.crossBaseline());
        turnPointOrder.onCrossBaseline();
        assertEquals(turnPointOrder.getOrderState(), OrderState.TERMINATED);
    }

    private TurnPointOrder newTurnPointOrder() {
        TradeCustomerInfo tradeCustomerInfo = new TradeCustomerInfo(303348, "010000061086");
        SecurityInfo pfyh = new SecurityInfo(SecurityType.STOCK, "600000", SecurityExchange.SH, "PFYH");
        return new TurnPointOrder(
                123L,
                tradeCustomerInfo,
                OrderState.ACTIVE,
                pfyh,
                new DecoratedTurnPointCondition(
                        new TurnPointCondition(CompareOperator.LE,
                                               new BigDecimal("11.00"),
                                               BinaryFactorType.PERCENT,
                                               new BigDecimal("1.00"),
                                               null,
                                               true),
                        new BigDecimal("9.00"),
                        new DelayConfirmInfo(DelayConfirmOption.CONTINUOUS, 3),
                        new DeviationCtrlInfo(new BigDecimal("0.5")),
                        0,
                        0),
                LocalDateTime.parse("2019-03-24T15:00:00"),
                new OfferedPriceTradePlan(ExchangeType.BUY,
                                          EntrustStrategy.SELL1,
                                          new TradeNumberByAmount(new BigDecimal("10000.00"))),
                NoneTrackedIndex.NONE,
                NoneMonitorTimeRange.NONE
        );
    }
}
