package hbec.intellitrade.conditionorder.domain.orders.turnpoint;

import hbec.intellitrade.mock.MockMarkets;
import hbec.intellitrade.strategy.domain.condition.delayconfirm.DelayConfirmInfo;
import hbec.intellitrade.strategy.domain.condition.delayconfirm.DelayConfirmOption;
import hbec.intellitrade.strategy.domain.condition.delayconfirm.DisabledDelayConfirm;
import hbec.intellitrade.strategy.domain.condition.deviation.DeviationCtrlInfo;
import hbec.intellitrade.strategy.domain.condition.deviation.DisabledDeviationCtrl;
import hbec.intellitrade.strategy.domain.factor.BinaryFactorType;
import hbec.intellitrade.strategy.domain.factor.CompareOperator;
import hbec.intellitrade.strategy.domain.factor.PercentBinaryTargetPriceFactor;
import hbec.intellitrade.strategy.domain.signal.Signals;
import hbec.intellitrade.strategy.domain.signal.TradeSignal;
import org.testng.annotations.Test;

import java.math.BigDecimal;

import static org.testng.Assert.*;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/3/30
 */
public class DecoratedTurnPointConditionTest {
    @Test
    public void testBasic() throws Exception {
        DecoratedTurnPointCondition turnPointCondition = new DecoratedTurnPointCondition(
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
                0
        );
        DecoratedTurnPointCondition turnPointCondition1 = new DecoratedTurnPointCondition(
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
                0
        );
        assertEquals(turnPointCondition1, turnPointCondition);
        assertEquals(turnPointCondition1.hashCode(), turnPointCondition.hashCode());
        assertEquals(turnPointCondition1.getCompareOperator(), CompareOperator.LE);
        assertEquals(turnPointCondition1.getBreakPrice(), new BigDecimal("11.00"));
        assertEquals(turnPointCondition1.getBinaryFactorType(), BinaryFactorType.PERCENT);
        assertEquals(turnPointCondition1.getTurnBackBinaryPriceFactor(),
                     new PercentBinaryTargetPriceFactor(CompareOperator.GE, new BigDecimal("1.00")));
        assertTrue(turnPointCondition1.isUseGuaranteedPrice());
        assertEquals(turnPointCondition1.getBaselinePrice().orNull(), new BigDecimal("9.00"));
        assertEquals(turnPointCondition1.getDelayConfirm(), new DelayConfirmInfo(DelayConfirmOption.CONTINUOUS, 3));
        assertEquals(turnPointCondition1.getDeviationCtrl(), new DeviationCtrlInfo(new BigDecimal("0.5")));
        System.out.println(turnPointCondition);
    }

    @Test
    public void testInflexionWithDelayConfirm() throws Exception {
        DecoratedTurnPointCondition turnPointCondition = new DecoratedTurnPointCondition(
                new TurnPointCondition(CompareOperator.LE,
                                       new BigDecimal("11.00"),
                                       BinaryFactorType.PERCENT,
                                       new BigDecimal("1.00"),
                                       null,
                                       true),
                null,
                new DelayConfirmInfo(DelayConfirmOption.CONTINUOUS, 3),
                new DeviationCtrlInfo(new BigDecimal("0.5")));

        // 未突破
        assertEquals(turnPointCondition.onMarketTick(MockMarkets.withCurrentPrice(new BigDecimal("11.01"))),
                     Signals.none());
        assertFalse(turnPointCondition.isDirty());
        assertFalse(turnPointCondition.isBroken());

        // 突破突破价
        assertEquals(turnPointCondition.onMarketTick(MockMarkets.withCurrentPrice(new BigDecimal("11.00"))),
                     Signals.none());
        assertTrue(turnPointCondition.getTurnPointCondition().isDirty());
        assertTrue(turnPointCondition.isDirty());
        assertTrue(turnPointCondition.isBroken());
        assertEquals(turnPointCondition.getExtremePrice().orNull(), new BigDecimal("11.00"));

        turnPointCondition.clearDirty();
        assertFalse(turnPointCondition.isDirty());

        //  继续下跌
        assertEquals(turnPointCondition.onMarketTick(MockMarkets.withCurrentPrice(new BigDecimal("10.00"))),
                     Signals.none());
        assertTrue(turnPointCondition.getTurnPointCondition().isDirty());
        assertTrue(turnPointCondition.isBroken());
        assertEquals(turnPointCondition.getExtremePrice().orNull(), new BigDecimal("10.00"));

        turnPointCondition.clearDirty();

        // 开始反弹
        assertEquals(turnPointCondition.onMarketTick(MockMarkets.withCurrentPrice(new BigDecimal("10.09"))),
                     Signals.none());
        assertFalse(turnPointCondition.getTurnPointCondition().isDirty());
        assertTrue(turnPointCondition.isBroken());
        assertEquals(turnPointCondition.getExtremePrice().orNull(), new BigDecimal("10.00"));


        // 达到反弹目标价，延迟确认
        assertEquals(turnPointCondition.onMarketTick(MockMarkets.withCurrentPrice(new BigDecimal("10.10"))),
                     Signals.none());
        assertTrue(turnPointCondition.getTurnPointCondition().isDirty());
        assertEquals(turnPointCondition.getTurnPointDelayConfirmedCount(), 1);

        turnPointCondition.clearDirty();

        assertEquals(turnPointCondition.onMarketTick(MockMarkets.withCurrentPrice(new BigDecimal("10.10"))),
                     Signals.none());
        assertEquals(turnPointCondition.getTurnPointDelayConfirmedCount(), 2);

        turnPointCondition.clearDirty();

        TradeSignal tradeSignal = turnPointCondition.onMarketTick(MockMarkets.withCurrentPrice(new BigDecimal("10.10")));
        assertEquals(tradeSignal, Signals.buyOrSell());
        assertFalse(tradeSignal.getDeviationExceeded());
        assertEquals(turnPointCondition.getTurnPointDelayConfirmedCount(), 3);
    }

    @Test
    public void testResetCounter() throws Exception {
        DecoratedTurnPointCondition turnPointCondition = new DecoratedTurnPointCondition(
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
                0);

        // 突破突破价
        assertEquals(turnPointCondition.onMarketTick(MockMarkets.withCurrentPrice(new BigDecimal("11.00"))),
                     Signals.none());
        assertTrue(turnPointCondition.isDirty());
        turnPointCondition.clearDirty();

        // 达到反弹目标价，延迟确认
        assertEquals(turnPointCondition.onMarketTick(MockMarkets.withCurrentPrice(new BigDecimal("11.11"))),
                     Signals.none());
        assertTrue(turnPointCondition.isDirty());
        assertEquals(turnPointCondition.getTurnPointCondition().getDelayConfirmedCount(), 1);
        turnPointCondition.clearDirty();

        turnPointCondition.resetCounter();
        // 延迟确认次数被重置
        assertTrue(turnPointCondition.isDirty());
        assertEquals(turnPointCondition.getTurnPointCondition().getDelayConfirmedCount(), 0);
    }

    @Test
    public void testCrossBaseline() throws Exception {
        DecoratedTurnPointCondition turnPointCondition = new DecoratedTurnPointCondition(
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
                0
        );
        // 突破突破价，未突破底线价
        assertEquals(turnPointCondition.onMarketTick(MockMarkets.withCurrentPrice(new BigDecimal("11.00"))),
                     Signals.none());
        assertTrue(turnPointCondition.getTurnPointCondition().isDirty());
        assertFalse(turnPointCondition.getCrossBaselineCondition().isDirty());
        assertTrue(turnPointCondition.isDirty());

        turnPointCondition.clearDirty();
        assertFalse(turnPointCondition.isDirty());

        //  继续下跌，刚刚到底线价
        assertEquals(turnPointCondition.onMarketTick(MockMarkets.withCurrentPrice(new BigDecimal("9.00"))),
                     Signals.none());
        assertTrue(turnPointCondition.getTurnPointCondition().isDirty());
        assertFalse(turnPointCondition.getCrossBaselineCondition().isDirty());

        // 突破底线价，延迟确认
        assertEquals(turnPointCondition.onMarketTick(MockMarkets.withCurrentPrice(new BigDecimal("8.99"))),
                     Signals.none());
        assertTrue(turnPointCondition.getTurnPointCondition().isDirty());
        assertTrue(turnPointCondition.getCrossBaselineCondition().isDirty());
        assertEquals(turnPointCondition.getCrossDelayConfirmedCount(), 1);

        turnPointCondition.clearDirty();
        assertFalse(turnPointCondition.isDirty());

        assertEquals(turnPointCondition.onMarketTick(MockMarkets.withCurrentPrice(new BigDecimal("8.99"))),
                     Signals.none());
        assertEquals(turnPointCondition.getCrossDelayConfirmedCount(), 2);
        // 穿越底线无偏差控制
        assertEquals(turnPointCondition.onMarketTick(MockMarkets.withCurrentPrice(new BigDecimal("8.00"))),
                     Signals.crossBaseline());
        assertEquals(turnPointCondition.getCrossDelayConfirmedCount(), 3);
    }

    @Test
    public void testDeviationCtrl() throws Exception {
        DecoratedTurnPointCondition turnPointCondition = new DecoratedTurnPointCondition(
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
                0
        );

        // 穿越底线无偏差控制
        assertEquals(turnPointCondition.onMarketTick(MockMarkets.withCurrentPrice(new BigDecimal("8.00"))),
                     Signals.crossBaseline());

        assertEquals(turnPointCondition.onMarketTick(MockMarkets.withCurrentPrice(new BigDecimal("10.00"))),
                     Signals.none());

        // 刚刚到达触发价，偏差为0
        assertEquals(turnPointCondition.onMarketTick(MockMarkets.withCurrentPrice(new BigDecimal("10.10"))),
                     Signals.buyOrSell());

        // 超过偏差控制
        assertEquals(turnPointCondition.onMarketTick(MockMarkets.withCurrentPrice(new BigDecimal("10.20"))),
                     Signals.buyOrSell().withDeviationExceeded());
    }

    @Test
    public void testAccumulatedDelayConfirmCountReset() throws Exception {
        DecoratedTurnPointCondition turnPointCondition = new DecoratedTurnPointCondition(
                new TurnPointCondition(CompareOperator.LE,
                                       new BigDecimal("11.00"),
                                       BinaryFactorType.PERCENT,
                                       new BigDecimal("1.00"),
                                       null,
                                       true),
                null,
                new DelayConfirmInfo(DelayConfirmOption.ACCUMULATE, 3),
                DisabledDeviationCtrl.DISABLED,
                0,
                0
        );

        // 突破突破价
        assertEquals(turnPointCondition.onMarketTick(MockMarkets.withCurrentPrice("10.50")), Signals.none());
        assertTrue(turnPointCondition.isDirty());

        turnPointCondition.clearDirty();

        // 达到反弹目标价，延迟确认
        assertEquals(turnPointCondition.onMarketTick(MockMarkets.withCurrentPrice("10.70")), Signals.none());
        assertTrue(turnPointCondition.isDirty());
        assertEquals(turnPointCondition.getTurnPointDelayConfirmedCount(), 1);

        turnPointCondition.clearDirty();

        // 继续下跌，更新最低价
        assertEquals(turnPointCondition.onMarketTick(MockMarkets.withCurrentPrice("10.00")), Signals.none());
        // 累计确认次数重置
        assertEquals(turnPointCondition.getTurnPointDelayConfirmedCount(), 0);

        turnPointCondition.clearDirty();

        assertEquals(turnPointCondition.onMarketTick(MockMarkets.withCurrentPrice("10.10")), Signals.none());
        assertEquals(turnPointCondition.getTurnPointDelayConfirmedCount(), 1);

        assertEquals(turnPointCondition.onMarketTick(MockMarkets.withCurrentPrice("10.10")), Signals.none());
        assertEquals(turnPointCondition.getTurnPointDelayConfirmedCount(), 2);

        TradeSignal tradeSignal = turnPointCondition.onMarketTick(MockMarkets.withCurrentPrice("10.10"));
        assertEquals(tradeSignal, Signals.buyOrSell());
        assertEquals(turnPointCondition.getTurnPointDelayConfirmedCount(), 3);
    }
}
