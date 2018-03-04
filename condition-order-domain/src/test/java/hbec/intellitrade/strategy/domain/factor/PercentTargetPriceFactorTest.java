package hbec.intellitrade.strategy.domain.factor;

import org.testng.annotations.Test;

import java.math.BigDecimal;

import static org.testng.Assert.*;

/**
 * @author caoshuhao@touker.com
 * @date 2018/2/3
 */
public class PercentTargetPriceFactorTest {
    @Test
    public void testBasic() throws Exception {
        PercentTargetPriceFactor factor = new PercentTargetPriceFactor(CompareOperator.GE,
                new BigDecimal("10.00"), new BigDecimal("1.00"));
        PercentTargetPriceFactor factor1 = new PercentTargetPriceFactor(CompareOperator.GE,
                new BigDecimal("10.00"), new BigDecimal("1.00"));
        assertEquals(factor, factor1);
        assertEquals(factor.hashCode(), factor1.hashCode());
        assertEquals(factor.toString(), "{{x >= (10.00 * (1 + (1.00%)))}, 10.100000}");

        assertEquals(factor.getCompareOperator(), CompareOperator.GE);
        assertEquals(factor.getBasePrice(), new BigDecimal("10.00"));
        assertEquals(factor.getPercent(), new BigDecimal("1.00"));
        assertEquals(factor.getTargetPrice(), new BigDecimal("10.100000"));

        assertFalse(factor.apply(new BigDecimal("10.09")));
        assertTrue(factor.apply(new BigDecimal("10.10")));
        assertTrue(factor.apply(new BigDecimal("10.11")));
    }
}
