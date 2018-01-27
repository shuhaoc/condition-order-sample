package me.caosh.condition.domain.model.condition;

import com.google.common.base.Optional;
import org.testng.annotations.Test;

import java.math.BigDecimal;

import static org.testng.Assert.*;


/**
 * Created by caosh on 2017/8/19.
 */
public class TurnUpConditionTest {
    @Test
    public void test() throws Exception {
        TurnUpCondition turnUpCondition = new TurnUpCondition(BigDecimal.valueOf(11), BigDecimal.valueOf(1));
        assertFalse(turnUpCondition.getTargetPriceFactor().apply(new BigDecimal("10.01")));
        assertTrue(turnUpCondition.isDirty());
        turnUpCondition.clearDirty();

        assertFalse(turnUpCondition.getTargetPriceFactor().apply(new BigDecimal("10.00")));
        assertTrue(turnUpCondition.isDirty());
        turnUpCondition.clearDirty();

        assertFalse(turnUpCondition.getTargetPriceFactor().apply(new BigDecimal("10.01")));
        assertFalse(turnUpCondition.isDirty());

        assertFalse(turnUpCondition.getTargetPriceFactor().apply(new BigDecimal("9.85"))); // 9.9485
        assertTrue(turnUpCondition.isDirty());
        turnUpCondition.clearDirty();

        assertFalse(turnUpCondition.getTargetPriceFactor().apply(new BigDecimal("9.94")));
        assertFalse(turnUpCondition.isDirty());

        assertTrue(turnUpCondition.getTargetPriceFactor().apply(new BigDecimal("9.95")));
    }

    @Test
    public void testSwapNonBroken() throws Exception {
        TurnUpCondition turnUpCondition1 = new TurnUpCondition(new BigDecimal("10.00"), new BigDecimal("1.00"));

        TurnUpCondition turnUpCondition2 = new TurnUpCondition(new BigDecimal("10.000"), new BigDecimal("2.00"));
        assertTrue(turnUpCondition2.isNeedSwap(turnUpCondition1));
        TurnUpCondition turnUpCondition2Expected = new TurnUpCondition(new BigDecimal("10.000"), new BigDecimal("2.00"),
                false, null);
        assertEquals(turnUpCondition2, turnUpCondition2Expected);

        assertEquals(turnUpCondition2.getBreakPrice(), new BigDecimal("10.000"));
        assertEquals(turnUpCondition2.getTurnUpPercent(), new BigDecimal("2.00"));
        assertEquals(turnUpCondition2.getLowestPrice(), Optional.<BigDecimal>absent());
        assertFalse(turnUpCondition2.isBroken());
        assertEquals(turnUpCondition2.hashCode(), turnUpCondition2Expected.hashCode());
        System.out.println(turnUpCondition2);
    }

    @Test
    public void testSwapBroken() throws Exception {
        TurnUpCondition turnUpCondition1 = new TurnUpCondition(new BigDecimal("10.00"), new BigDecimal("1.00"));
        turnUpCondition1.getTargetPriceFactor().apply(new BigDecimal("9.99"));

        TurnUpCondition turnUpCondition2 = new TurnUpCondition(new BigDecimal("10.000"), new BigDecimal("2.00"));
        assertTrue(turnUpCondition2.isNeedSwap(turnUpCondition1));
        turnUpCondition2.swap(turnUpCondition1);
        assertEquals(turnUpCondition2, new TurnUpCondition(new BigDecimal("10.000"), new BigDecimal("2.00"),
                true, new BigDecimal("9.99")));

        TurnUpCondition turnUpCondition3 = new TurnUpCondition(new BigDecimal("10.05"), new BigDecimal("1.00"));
        assertFalse(turnUpCondition3.isNeedSwap(turnUpCondition1));
    }
}
