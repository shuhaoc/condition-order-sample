package me.caosh.condition.domain.model.strategy.shared;

import me.caosh.condition.domain.model.strategy.factor.CompareOperator;
import org.testng.annotations.Test;

import java.math.BigDecimal;

import static org.testng.Assert.assertEquals;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/1/29
 */
public class BigDecimalRangesTest {
    @Test
    public void test() throws Exception {
        Range<BigDecimal> range = BigDecimalRanges.openCenterWithPercent(
                new BigDecimal("10.00"), new BigDecimal("0.50"));
        assertEquals(range, new Range<>(new BigDecimal("9.950000"), new BigDecimal("10.050000"),
                CompareOperator.GT, CompareOperator.LT));
    }
}
