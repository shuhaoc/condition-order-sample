package hbec.intellitrade.condorder.domain.orders.turnpoint;

import hbec.intellitrade.mock.MockMarkets;
import hbec.intellitrade.strategy.domain.condition.delayconfirm.DelayConfirmInfo;
import hbec.intellitrade.strategy.domain.condition.delayconfirm.DelayConfirmOption;
import hbec.intellitrade.strategy.domain.condition.deviation.DeviationCtrlInfo;
import hbec.intellitrade.strategy.domain.factor.BinaryFactorType;
import hbec.intellitrade.strategy.domain.factor.CompareOperator;
import hbec.intellitrade.strategy.domain.signal.Signals;
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
        System.out.println(turnPointCondition);
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

        turnPointCondition.clearDirty();
        assertFalse(turnPointCondition.isDirty());

        assertEquals(turnPointCondition.onMarketTick(MockMarkets.withCurrentPrice(new BigDecimal("8.99"))),
                     Signals.none());
        assertEquals(turnPointCondition.onMarketTick(MockMarkets.withCurrentPrice(new BigDecimal("8.99"))),
                     Signals.crossBaseline());
    }
}
