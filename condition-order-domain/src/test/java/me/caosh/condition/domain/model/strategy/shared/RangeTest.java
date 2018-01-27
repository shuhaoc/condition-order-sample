package me.caosh.condition.domain.model.strategy.shared;

import me.caosh.condition.domain.model.strategy.factor.CompareOperator;
import org.testng.annotations.Test;

import java.math.BigDecimal;

import static org.testng.Assert.*;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/1/29
 */
public class RangeTest {
    @Test
    public void test() throws Exception {
        Range<BigDecimal> range = new Range<>(
                new BigDecimal("1.00"), new BigDecimal("2.00"), CompareOperator.GE, CompareOperator.LE
        );
        assertFalse(range.isInRange(new BigDecimal("0.99")));
        assertTrue(range.isInRange(new BigDecimal("1")));
        assertTrue(range.isInRange(new BigDecimal("1.5")));
        assertTrue(range.isInRange(new BigDecimal("2")));
        assertFalse(range.isInRange(new BigDecimal("2.01")));
        assertEquals(range.toString(), "[1.00, 2.00]");
        assertEquals(range, new Range<>(
                new BigDecimal("1.00"), new BigDecimal("2.00"), CompareOperator.GE, CompareOperator.LE));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testIllegal() throws Exception {
        new Range<>(new BigDecimal(2), new BigDecimal(1), CompareOperator.GE, CompareOperator.LE);
    }
}
