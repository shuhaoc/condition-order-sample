package me.caosh.condition.domain.model.strategy.factor;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/1/30
 */
public class CompareOperatorsTest {
    @Test
    public void testReverse() throws Exception {
        assertEquals(CompareOperators.reverse(CompareOperator.GE), CompareOperator.LE);
        assertEquals(CompareOperators.reverse(CompareOperator.LE), CompareOperator.GE);
        assertEquals(CompareOperators.reverse(CompareOperator.GT), CompareOperator.LT);
        assertEquals(CompareOperators.reverse(CompareOperator.LT), CompareOperator.GT);
    }

    @Test
    public void testNonEquals() throws Exception {
        assertEquals(CompareOperators.nonEquals(CompareOperator.GE), CompareOperator.GT);
        assertEquals(CompareOperators.nonEquals(CompareOperator.LE), CompareOperator.LT);
        assertEquals(CompareOperators.nonEquals(CompareOperator.GT), CompareOperator.GT);
        assertEquals(CompareOperators.nonEquals(CompareOperator.LT), CompareOperator.LT);
    }
}
