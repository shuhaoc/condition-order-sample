package me.caosh.condition.domain.model.strategy.shared;

import com.google.common.base.Function;
import me.caosh.condition.domain.model.strategy.factor.CompareOperator;
import org.testng.annotations.Test;

import java.math.BigDecimal;

import static org.testng.Assert.assertEquals;

/**
 * @author caoshuhao@touker.com
 * @date 2018/1/29
 */
public class RangesTest {
    @Test
    public void testTransform() throws Exception {
        Range<BigDecimal> bigDecimalRange = new Range<>(new BigDecimal(1), new BigDecimal(2),
                CompareOperator.GE, CompareOperator.LE);
        Range<Integer> integerRange = Ranges.transform(bigDecimalRange, new Function<BigDecimal, Integer>() {
            @Override
            public Integer apply(BigDecimal bigDecimal) {
                return bigDecimal.intValue();
            }
        });
        assertEquals(integerRange, new Range<>(1, 2, CompareOperator.GE, CompareOperator.LE));
    }
}
