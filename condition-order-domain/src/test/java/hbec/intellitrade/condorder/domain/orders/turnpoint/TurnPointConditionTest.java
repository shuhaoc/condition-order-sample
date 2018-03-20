package hbec.intellitrade.condorder.domain.orders.turnpoint;

import com.google.common.base.Optional;
import hbec.intellitrade.strategy.domain.factor.CompareOperator;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.math.BigDecimal;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;


/**
 * Created by caosh on 2017/8/19.
 */
public class TurnPointConditionTest {
    @Test
    public void testTurnUp() throws Exception {
        TurnPointCondition turnPointCondition = new TurnPointCondition(CompareOperator.LE,
                                                                       BigDecimal.valueOf(11),
                                                                       BigDecimal.valueOf(1));
        assertFalse(turnPointCondition.getTargetPriceFactor().apply(new BigDecimal("10.01")));
        assertTrue(turnPointCondition.isDirty());
        turnPointCondition.clearDirty();

        assertFalse(turnPointCondition.getTargetPriceFactor().apply(new BigDecimal("10.00")));
        assertTrue(turnPointCondition.isDirty());
        turnPointCondition.clearDirty();

        assertFalse(turnPointCondition.getTargetPriceFactor().apply(new BigDecimal("10.01")));
        assertFalse(turnPointCondition.isDirty());

        assertFalse(turnPointCondition.getTargetPriceFactor().apply(new BigDecimal("9.85"))); // 9.9485
        assertTrue(turnPointCondition.isDirty());
        turnPointCondition.clearDirty();

        assertFalse(turnPointCondition.getTargetPriceFactor().apply(new BigDecimal("9.94")));
        assertFalse(turnPointCondition.isDirty());

        assertTrue(turnPointCondition.getTargetPriceFactor().apply(new BigDecimal("9.95")));
    }

    @Test
    public void testSwapNonBroken() throws Exception {
        TurnPointCondition turnPointCondition1 = new TurnPointCondition(CompareOperator.LE, new BigDecimal("10.00"),
                                                                        new BigDecimal("1.00"));

        TurnPointCondition turnPointCondition2 = new TurnPointCondition(CompareOperator.LE, new BigDecimal("10.000"),
                                                                        new BigDecimal("2.00"));
//        assertTrue(turnPointCondition2.isNeedSwap(turnPointCondition1));
        TurnPointCondition turnPointCondition2Expected = new TurnPointCondition(
                CompareOperator.LE,
                new BigDecimal("10.000"),
                new BigDecimal("2.00"),
                null, false,
                null);
        Assert.assertEquals(turnPointCondition2, turnPointCondition2Expected);

        Assert.assertEquals(turnPointCondition2.getBreakPrice(), new BigDecimal("10.000"));
        Assert.assertEquals(turnPointCondition2.getTurnUpPercent(), new BigDecimal("2.00"));
        Assert.assertEquals(turnPointCondition2.getExtremePrice(), Optional.<BigDecimal>absent());
        assertFalse(turnPointCondition2.isBroken());
        Assert.assertEquals(turnPointCondition2.hashCode(), turnPointCondition2Expected.hashCode());
        System.out.println(turnPointCondition2);
    }

    @Test
    public void testSwapBroken() throws Exception {
        TurnPointCondition turnPointCondition1 = new TurnPointCondition(CompareOperator.LE, new BigDecimal("10.00"),
                                                                        new BigDecimal("1.00"));
        turnPointCondition1.getTargetPriceFactor().apply(new BigDecimal("9.99"));

        TurnPointCondition turnPointCondition2 = new TurnPointCondition(CompareOperator.LE, new BigDecimal("10.000"),
                                                                        new BigDecimal("2.00"));
//        assertTrue(turnPointCondition2.isNeedSwap(turnPointCondition1));
//        turnPointCondition2.swap(turnPointCondition1);
        Assert.assertEquals(turnPointCondition2,
                            new TurnPointCondition(CompareOperator.LE,
                                                   new BigDecimal("10.000"),
                                                   new BigDecimal("2.00"),
                                                   null, true,
                                                   new BigDecimal("9.99")));

        TurnPointCondition turnPointCondition3 = new TurnPointCondition(CompareOperator.LE, new BigDecimal("10.05"),
                                                                        new BigDecimal("1.00"));
//        assertFalse(turnPointCondition3.isNeedSwap(turnPointCondition1));
    }
}
