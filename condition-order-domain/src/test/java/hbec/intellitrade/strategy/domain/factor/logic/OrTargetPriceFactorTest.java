package hbec.intellitrade.strategy.domain.factor.logic;

import hbec.intellitrade.strategy.domain.factor.BasicTargetPriceFactor;
import hbec.intellitrade.strategy.domain.factor.CompareOperator;
import hbec.intellitrade.strategy.domain.factor.TargetPriceFactor;
import org.testng.annotations.Test;

import java.math.BigDecimal;

import static org.testng.Assert.assertEquals;

/**
 * @author caoshuhao@touker.com
 * @date 2018/3/18
 */
public class OrTargetPriceFactorTest {
    @Test
    public void test() throws Exception {
        TargetPriceFactor fa = new BasicTargetPriceFactor(CompareOperator.GE, new BigDecimal("10"));
        TargetPriceFactor fb = new BasicTargetPriceFactor(CompareOperator.GE, new BigDecimal("11"));

        OrTargetPriceFactor orTargetPriceFactor = new OrTargetPriceFactor(fa, fb);
        assertEquals(orTargetPriceFactor.getCompareOperator(), CompareOperator.GE);
        assertEquals(orTargetPriceFactor.getTargetPrice(), new BigDecimal("10"));
    }
}
