package me.caosh.condition.domain.model.strategy.factor;

import org.testng.annotations.Test;

import java.math.BigDecimal;

import static org.testng.Assert.*;

/**
 * @author caoshuhao@touker.com
 * @date 2018/2/3
 */
public class IncrementTargetPriceFactorTest {
    @Test
    public void testBasic() throws Exception {
        IncrementTargetPriceFactor factor = new IncrementTargetPriceFactor(CompareOperator.GE,
                new BigDecimal("10.00"), new BigDecimal("1.00"));
        IncrementTargetPriceFactor factor1 = new IncrementTargetPriceFactor(CompareOperator.GE,
                new BigDecimal("10.00"), new BigDecimal("1.00"));
        assertEquals(factor, factor1);
        assertEquals(factor.hashCode(), factor1.hashCode());
        assertEquals(factor.toString(), "{{x >= (10.00 * (1 + (1.00)))}, 11.00}");

        assertEquals(factor.getCompareOperator(), CompareOperator.GE);
        assertEquals(factor.getBasePrice(), new BigDecimal("10.00"));
        assertEquals(factor.getIncrement(), new BigDecimal("1.00"));
        assertEquals(factor.getTargetPrice(), new BigDecimal("11.00"));

        assertFalse(factor.apply(new BigDecimal("10.99")));
        assertTrue(factor.apply(new BigDecimal("11.00")));
        assertTrue(factor.apply(new BigDecimal("11.01")));
    }
}
