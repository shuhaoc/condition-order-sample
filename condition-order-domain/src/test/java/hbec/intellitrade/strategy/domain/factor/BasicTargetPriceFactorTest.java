package hbec.intellitrade.strategy.domain.factor;

import org.testng.annotations.Test;

import java.math.BigDecimal;

import static org.testng.Assert.*;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/1/29
 */
public class BasicTargetPriceFactorTest {
    @Test
    public void testGE() throws Exception {
        BasicTargetPriceFactor targetPriceFactor = new BasicTargetPriceFactor(CompareOperator.GE, new BigDecimal("12.34"));

        assertEquals(targetPriceFactor.getCompareOperator(), CompareOperator.GE);
        assertEquals(targetPriceFactor.getCompareOperator().getValue().intValue(), 1);
        assertEquals(targetPriceFactor.getTargetPrice(), new BigDecimal("12.34"));
        assertEquals(targetPriceFactor.toString(), "{x >= 12.34}");

        assertFalse(targetPriceFactor.apply(new BigDecimal("12.33")));
        assertTrue(targetPriceFactor.apply(new BigDecimal("12.34")));
        assertTrue(targetPriceFactor.apply(new BigDecimal("12.35")));
    }

    @Test
    public void testLE() throws Exception {
        BasicTargetPriceFactor targetPriceFactor = new BasicTargetPriceFactor(CompareOperator.LE, new BigDecimal("12.34"));

        assertEquals(targetPriceFactor.getCompareOperator().getValue().intValue(), 0);
        assertEquals(targetPriceFactor.toString(), "{x <= 12.34}");

        assertTrue(targetPriceFactor.apply(new BigDecimal("12.33")));
        assertTrue(targetPriceFactor.apply(new BigDecimal("12.34")));
        assertFalse(targetPriceFactor.apply(new BigDecimal("12.35")));
    }

    @Test
    public void testEquals() throws Exception {
        BasicTargetPriceFactor a = new BasicTargetPriceFactor(CompareOperator.GE, new BigDecimal("12.34"));
        BasicTargetPriceFactor b = new BasicTargetPriceFactor(CompareOperator.GE, new BigDecimal("12.34"));
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }
}
