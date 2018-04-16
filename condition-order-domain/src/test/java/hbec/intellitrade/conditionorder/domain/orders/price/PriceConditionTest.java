package hbec.intellitrade.conditionorder.domain.orders.price;

import hbec.intellitrade.strategy.domain.factor.CompareOperator;
import org.testng.annotations.Test;

import java.math.BigDecimal;

import static org.testng.Assert.*;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/1/29
 */
public class PriceConditionTest {
    @Test
    public void testBasicUpward() throws Exception {
        PriceCondition priceCondition = new PriceCondition(CompareOperator.GE, new BigDecimal("12.34"));
        assertFalse(priceCondition.getTargetPriceFactor().apply(new BigDecimal("12.33")));
        assertTrue(priceCondition.getTargetPriceFactor().apply(new BigDecimal("12.34")));
        assertTrue(priceCondition.getTargetPriceFactor().apply(new BigDecimal("12.35")));
        assertEquals(priceCondition.getCompareOperator(), CompareOperator.GE);
        assertEquals(priceCondition.getTargetPrice(), new BigDecimal("12.34"));

        System.out.println(priceCondition);
        System.out.println(priceCondition.hashCode());
        assertEquals(priceCondition, new PriceCondition(CompareOperator.GE, new BigDecimal("12.34")));
    }
}
