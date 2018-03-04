package hbec.intellitrade.strategy.domain.factor;

import org.testng.annotations.Test;

import java.math.BigDecimal;

import static org.testng.Assert.assertEquals;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/1/30
 */
public class IncrementBinaryTargetPriceFactorTest {
    @Test
    public void test() throws Exception {
        IncrementBinaryTargetPriceFactor incrementBinaryTargetPriceFactor = new IncrementBinaryTargetPriceFactor(
                CompareOperator.GE, new BigDecimal("1.00"));

        assertEquals(incrementBinaryTargetPriceFactor.getCompareOperator(), CompareOperator.GE);
        assertEquals(incrementBinaryTargetPriceFactor.getIncrement(), new BigDecimal("1.00"));
        assertEquals(incrementBinaryTargetPriceFactor, new IncrementBinaryTargetPriceFactor(
                CompareOperator.GE, new BigDecimal("1.00")));
        System.out.println(incrementBinaryTargetPriceFactor.hashCode());
        assertEquals(incrementBinaryTargetPriceFactor.toString(), "{y >= x + (1.00)}");
        assertEquals(incrementBinaryTargetPriceFactor.getTargetPrice(new BigDecimal("10.00")), new BigDecimal("11.00"));
    }
}
