package hbec.intellitrade.strategy.domain.factor;

import com.google.common.base.Optional;
import org.testng.annotations.Test;

import java.math.BigDecimal;

import static org.testng.Assert.*;


/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/1/30
 */
public class InflexionFactorTest {
    @Test
    public void testBasic() throws Exception {
        InflexionFactor a = new InflexionFactor(
                new BasicTargetPriceFactor(CompareOperator.LE, new BigDecimal("11.00")),
                new PercentBinaryTargetPriceFactor(CompareOperator.GE, new BigDecimal("1.00")),
                false);
        InflexionFactor b = new InflexionFactor(
                new BasicTargetPriceFactor(CompareOperator.LE, new BigDecimal("11.00")),
                new PercentBinaryTargetPriceFactor(CompareOperator.GE, new BigDecimal("1.00")),
                false);
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());

        assertEquals(a.getBreakPriceFactor(), new BasicTargetPriceFactor(CompareOperator.LE, new BigDecimal("11.00")));
        assertEquals(a.getTurnBackBinaryPriceFactor(),
                     new PercentBinaryTargetPriceFactor(CompareOperator.GE, new BigDecimal("1.00")));
    }

    @Test
    public void testNoGuaranteedPrice() throws Exception {
        InflexionFactor inflexionFactor = new InflexionFactor(
                new BasicTargetPriceFactor(CompareOperator.LE, new BigDecimal("11.00")),
                new PercentBinaryTargetPriceFactor(CompareOperator.GE, new BigDecimal("1.00")),
                false);

        assertEquals(inflexionFactor.getCompareOperator(), CompareOperator.GE);
        System.out.println(inflexionFactor);

        assertFalse(inflexionFactor.apply(new BigDecimal("11.01")));
        assertFalse(inflexionFactor.isDirty());
        assertFalse(inflexionFactor.isBroken());
        assertEquals(inflexionFactor.getExtremePrice(), Optional.<BigDecimal>absent());

        assertFalse(inflexionFactor.apply(new BigDecimal("10.02")));
        assertTrue(inflexionFactor.isDirty());
        assertTrue(inflexionFactor.isBroken());
        assertEquals(inflexionFactor.getExtremePrice(), Optional.of(new BigDecimal("10.02")));
        System.out.println(inflexionFactor);
        inflexionFactor.clearDirty();

        assertFalse(inflexionFactor.apply(new BigDecimal("10.00")));
        assertTrue(inflexionFactor.isDirty());
        inflexionFactor.clearDirty();

        assertFalse(inflexionFactor.apply(new BigDecimal("10.00")));
        assertFalse(inflexionFactor.isDirty());

        assertFalse(inflexionFactor.apply(new BigDecimal("10.01")));
        assertFalse(inflexionFactor.isDirty());

        assertFalse(inflexionFactor.apply(new BigDecimal("9.85")));
        assertTrue(inflexionFactor.isDirty());
        inflexionFactor.clearDirty();

        // 临界值9.9485
        assertFalse(inflexionFactor.apply(new BigDecimal("9.94")));
        assertFalse(inflexionFactor.isDirty());

        assertTrue(inflexionFactor.apply(new BigDecimal("9.95")));
        assertEquals(inflexionFactor.getTargetPrice(), new BigDecimal("9.948500"));
        System.out.println(inflexionFactor);
    }

    @Test
    public void testGuaranteedPrice() throws Exception {
        InflexionFactor inflexionFactor = new InflexionFactor(
                new BasicTargetPriceFactor(CompareOperator.LE, new BigDecimal("11.00")),
                new PercentBinaryTargetPriceFactor(CompareOperator.GE, new BigDecimal("1.00")),
                true);

        assertEquals(inflexionFactor.getCompareOperator(), CompareOperator.GE);
        System.out.println(inflexionFactor);

        // 未突破
        assertFalse(inflexionFactor.apply(new BigDecimal("11.01")));
        assertFalse(inflexionFactor.isDirty());
        assertFalse(inflexionFactor.isBroken());
        assertEquals(inflexionFactor.getExtremePrice(), Optional.<BigDecimal>absent());

        // 突破
        assertFalse(inflexionFactor.apply(new BigDecimal("10.96")));
        assertTrue(inflexionFactor.isDirty());
        assertTrue(inflexionFactor.isBroken());
        assertEquals(inflexionFactor.getExtremePrice(), Optional.of(new BigDecimal("10.96")));
        System.out.println(inflexionFactor);
        inflexionFactor.clearDirty();

        // 更新最低价
        assertFalse(inflexionFactor.apply(new BigDecimal("10.95")));
        assertTrue(inflexionFactor.isDirty());
        inflexionFactor.clearDirty();

        // 未创新低也未达到拐点目标价
        assertFalse(inflexionFactor.apply(new BigDecimal("10.96")));
        assertFalse(inflexionFactor.isDirty());

        // 拐点价11.0595，保底价11
        assertTrue(inflexionFactor.apply(new BigDecimal("11.00")));
        assertEquals(inflexionFactor.getTargetPrice(), new BigDecimal("11.00"));
        // 改变不触发、触发不改变
        assertFalse(inflexionFactor.isDirty());

        System.out.println(inflexionFactor);
    }
}
