package me.caosh.condition.domain.model.strategy.factor;

import org.testng.annotations.Test;

import java.math.BigDecimal;

import static org.testng.Assert.assertEquals;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/1/30
 */
public class PercentBinaryTargetPriceFactorTest {
    @Test
    public void test() throws Exception {
        PercentBinaryTargetPriceFactor percentBinaryTargetPriceFactor = new PercentBinaryTargetPriceFactor(
                CompareOperator.GE, new BigDecimal("1.00"));

        assertEquals(percentBinaryTargetPriceFactor.getCompareOperator(), CompareOperator.GE);
        assertEquals(percentBinaryTargetPriceFactor.getPercent(), new BigDecimal("1.00"));
        assertEquals(percentBinaryTargetPriceFactor, new PercentBinaryTargetPriceFactor(
                CompareOperator.GE, new BigDecimal("1.00")));
        System.out.println(percentBinaryTargetPriceFactor.hashCode());
        assertEquals(percentBinaryTargetPriceFactor.toString(), "{y >= x * (1 + (1.00%))}");

        assertEquals(percentBinaryTargetPriceFactor.getTargetPrice(new BigDecimal("10.00")), new BigDecimal("10.100000"));
    }
}
