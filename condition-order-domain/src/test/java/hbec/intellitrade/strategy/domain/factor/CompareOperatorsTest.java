package hbec.intellitrade.strategy.domain.factor;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/1/30
 */
public class CompareOperatorsTest {
    @Test
    public void testReverse() throws Exception {
        assertEquals(CompareOperator.GE.reverse(), CompareOperator.LE);
        assertEquals(CompareOperator.LE.reverse(), CompareOperator.GE);
        assertEquals(CompareOperator.GT.reverse(), CompareOperator.LT);
        assertEquals(CompareOperator.LT.reverse(), CompareOperator.GT);
    }

    @Test
    public void testNonEquals() throws Exception {
        assertEquals(CompareOperator.GE.withoutEquals(), CompareOperator.GT);
        assertEquals(CompareOperator.LE.withoutEquals(), CompareOperator.LT);
        assertEquals(CompareOperator.GT.withoutEquals(), CompareOperator.GT);
        assertEquals(CompareOperator.LT.withoutEquals(), CompareOperator.LT);
    }
}
