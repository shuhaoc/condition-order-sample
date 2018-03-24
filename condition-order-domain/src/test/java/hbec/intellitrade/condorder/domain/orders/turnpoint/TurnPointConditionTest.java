package hbec.intellitrade.condorder.domain.orders.turnpoint;

import hbec.intellitrade.strategy.domain.factor.BinaryFactorType;
import hbec.intellitrade.strategy.domain.factor.CompareOperator;
import org.testng.annotations.Test;

import java.math.BigDecimal;

import static org.testng.Assert.*;


/**
 * Created by caosh on 2017/8/19.
 */
public class TurnPointConditionTest {
    @Test
    public void testBasic() throws Exception {
        TurnPointCondition turnPointCondition1 = new TurnPointCondition(CompareOperator.LE,
                                                                        BigDecimal.valueOf(11),
                                                                        BinaryFactorType.PERCENT,
                                                                        BigDecimal.valueOf(1),
                                                                        null,
                                                                        true);
        TurnPointCondition turnPointCondition2 = new TurnPointCondition(CompareOperator.LE,
                                                                        BigDecimal.valueOf(11),
                                                                        BinaryFactorType.PERCENT,
                                                                        BigDecimal.valueOf(1),
                                                                        null,
                                                                        true);
        System.out.println(turnPointCondition1);
        assertEquals(turnPointCondition1, turnPointCondition2);
        assertEquals(turnPointCondition1.hashCode(), turnPointCondition2.hashCode());
        assertEquals(turnPointCondition1.getCompareOperator(), CompareOperator.LE);
        assertEquals(turnPointCondition1.getBreakPrice(), BigDecimal.valueOf(11));
        assertEquals(turnPointCondition2.getTurnUpPercent(), BigDecimal.valueOf(1));
    }

    @Test
    public void testTurnUp() throws Exception {
        TurnPointCondition turnPointCondition = new TurnPointCondition(CompareOperator.LE,
                                                                       BigDecimal.valueOf(11),
                                                                       BinaryFactorType.PERCENT,
                                                                       BigDecimal.valueOf(1),
                                                                       null,
                                                                       false);
        assertFalse(turnPointCondition.getTargetPriceFactor().apply(new BigDecimal("11.01")));

        assertFalse(turnPointCondition.getTargetPriceFactor().apply(new BigDecimal("10.01")));
        assertTrue(turnPointCondition.isDirty());
        assertTrue(turnPointCondition.isBroken());
        assertEquals(turnPointCondition.getExtremePrice().orNull(), new BigDecimal("10.01"));
        turnPointCondition.clearDirty();

        assertFalse(turnPointCondition.getTargetPriceFactor().apply(new BigDecimal("10.00")));
        assertTrue(turnPointCondition.isDirty());
        turnPointCondition.clearDirty();

        assertFalse(turnPointCondition.getTargetPriceFactor().apply(new BigDecimal("10.01")));
        assertFalse(turnPointCondition.isDirty());

        // 9.9485
        assertFalse(turnPointCondition.getTargetPriceFactor().apply(new BigDecimal("9.85")));
        assertTrue(turnPointCondition.isDirty());
        turnPointCondition.clearDirty();
        assertTrue(turnPointCondition.isBroken());
        assertEquals(turnPointCondition.getExtremePrice().orNull(), new BigDecimal("9.85"));

        assertFalse(turnPointCondition.getTargetPriceFactor().apply(new BigDecimal("9.94")));
        assertFalse(turnPointCondition.isDirty());

        assertTrue(turnPointCondition.getTargetPriceFactor().apply(new BigDecimal("9.95")));
    }

    @Test
    public void testTurnDown() throws Exception {
        TurnPointCondition turnPointCondition = new TurnPointCondition(CompareOperator.GT,
                                                                       new BigDecimal("10.00"),
                                                                       BinaryFactorType.PERCENT,
                                                                       new BigDecimal("-1.00"),
                                                                       null,
                                                                       false);
        assertFalse(turnPointCondition.getTargetPriceFactor().apply(new BigDecimal("9.99")));

        assertFalse(turnPointCondition.getTargetPriceFactor().apply(new BigDecimal("10.01")));
        assertTrue(turnPointCondition.isDirty());
        assertTrue(turnPointCondition.isBroken());
        assertEquals(turnPointCondition.getExtremePrice().orNull(), new BigDecimal("10.01"));
        turnPointCondition.clearDirty();

        assertFalse(turnPointCondition.getTargetPriceFactor().apply(new BigDecimal("10.21")));
        assertTrue(turnPointCondition.isDirty());
        turnPointCondition.clearDirty();

        assertFalse(turnPointCondition.getTargetPriceFactor().apply(new BigDecimal("10.20")));
        assertFalse(turnPointCondition.isDirty());

        // 10.1277
        assertFalse(turnPointCondition.getTargetPriceFactor().apply(new BigDecimal("10.23")));
        assertTrue(turnPointCondition.isDirty());
        turnPointCondition.clearDirty();
        assertTrue(turnPointCondition.isBroken());
        assertEquals(turnPointCondition.getExtremePrice().orNull(), new BigDecimal("10.23"));

        assertFalse(turnPointCondition.getTargetPriceFactor().apply(new BigDecimal("10.13")));
        assertFalse(turnPointCondition.isDirty());

        assertTrue(turnPointCondition.getTargetPriceFactor().apply(new BigDecimal("10.12")));
    }

    @Test
    public void testTurnUpWithGuaranteedPrice() throws Exception {
        TurnPointCondition turnPointCondition = new TurnPointCondition(CompareOperator.LE,
                                                                       BigDecimal.valueOf(10.2),
                                                                       BinaryFactorType.PERCENT,
                                                                       BigDecimal.valueOf(3),
                                                                       null,
                                                                       true);
        assertFalse(turnPointCondition.getTargetPriceFactor().apply(new BigDecimal("10.19")));
        assertTrue(turnPointCondition.isDirty());
        turnPointCondition.clearDirty();

        assertFalse(turnPointCondition.getTargetPriceFactor().apply(new BigDecimal("10.00")));
        assertTrue(turnPointCondition.isDirty());
        turnPointCondition.clearDirty();

        assertFalse(turnPointCondition.getTargetPriceFactor().apply(new BigDecimal("10.01")));
        assertFalse(turnPointCondition.isDirty());

        assertFalse(turnPointCondition.getTargetPriceFactor().apply(new BigDecimal("10.19")));
        assertFalse(turnPointCondition.isDirty());

        assertTrue(turnPointCondition.getTargetPriceFactor().apply(new BigDecimal("10.20")));
        assertFalse(turnPointCondition.isDirty());
    }

    @Test
    public void testTurnUpWithoutGuaranteedPrice() throws Exception {
        TurnPointCondition turnPointCondition = new TurnPointCondition(CompareOperator.LE,
                                                                       BigDecimal.valueOf(10.2),
                                                                       BinaryFactorType.PERCENT,
                                                                       BigDecimal.valueOf(3),
                                                                       null,
                                                                       false);
        assertFalse(turnPointCondition.getTargetPriceFactor().apply(new BigDecimal("10.19")));
        assertTrue(turnPointCondition.isDirty());
        turnPointCondition.clearDirty();

        assertFalse(turnPointCondition.getTargetPriceFactor().apply(new BigDecimal("10.00")));
        assertTrue(turnPointCondition.isDirty());
        turnPointCondition.clearDirty();

        assertFalse(turnPointCondition.getTargetPriceFactor().apply(new BigDecimal("10.01")));
        assertFalse(turnPointCondition.isDirty());

        assertFalse(turnPointCondition.getTargetPriceFactor().apply(new BigDecimal("10.19")));
        assertFalse(turnPointCondition.isDirty());

        assertFalse(turnPointCondition.getTargetPriceFactor().apply(new BigDecimal("10.20")));
        assertFalse(turnPointCondition.isDirty());

        assertTrue(turnPointCondition.getTargetPriceFactor().apply(new BigDecimal("10.30")));
        assertFalse(turnPointCondition.isDirty());
    }

    @Test
    public void testTurnUpIncrement() throws Exception {
        TurnPointCondition turnPointCondition = new TurnPointCondition(CompareOperator.LE,
                                                                       new BigDecimal("11.00"),
                                                                       BinaryFactorType.INCREMENT,
                                                                       null,
                                                                       new BigDecimal("0.20"),
                                                                       false);
        assertFalse(turnPointCondition.getTargetPriceFactor().apply(new BigDecimal("11.01")));

        assertFalse(turnPointCondition.getTargetPriceFactor().apply(new BigDecimal("10.01")));
        assertTrue(turnPointCondition.isDirty());
        assertTrue(turnPointCondition.isBroken());
        assertEquals(turnPointCondition.getExtremePrice().orNull(), new BigDecimal("10.01"));
        turnPointCondition.clearDirty();

        assertFalse(turnPointCondition.getTargetPriceFactor().apply(new BigDecimal("10.00")));
        assertTrue(turnPointCondition.isDirty());
        turnPointCondition.clearDirty();

        assertFalse(turnPointCondition.getTargetPriceFactor().apply(new BigDecimal("10.01")));
        assertFalse(turnPointCondition.isDirty());

        // 10.05
        assertFalse(turnPointCondition.getTargetPriceFactor().apply(new BigDecimal("9.85")));
        assertTrue(turnPointCondition.isDirty());
        turnPointCondition.clearDirty();
        assertTrue(turnPointCondition.isBroken());
        assertEquals(turnPointCondition.getExtremePrice().orNull(), new BigDecimal("9.85"));

        assertFalse(turnPointCondition.getTargetPriceFactor().apply(new BigDecimal("10.04")));
        assertFalse(turnPointCondition.isDirty());

        assertTrue(turnPointCondition.getTargetPriceFactor().apply(new BigDecimal("10.05")));
    }
}
