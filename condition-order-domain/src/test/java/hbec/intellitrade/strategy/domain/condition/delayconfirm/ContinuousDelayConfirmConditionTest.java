package hbec.intellitrade.strategy.domain.condition.delayconfirm;

import hbec.intellitrade.condorder.domain.orders.price.PriceCondition;
import hbec.intellitrade.mock.MockMarkets;
import hbec.intellitrade.strategy.domain.factor.CompareOperator;
import hbec.intellitrade.strategy.domain.signal.Signals;
import org.testng.annotations.Test;

import java.math.BigDecimal;

import static org.testng.Assert.assertEquals;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/2/8
 */
public class ContinuousDelayConfirmConditionTest {
    @Test
    public void testBasic() throws Exception {
        ContinuousDelayConfirmCondition delayConfirmCondition = new ContinuousDelayConfirmCondition(3,
                new PriceCondition(CompareOperator.GE, new BigDecimal("10.00")));

        assertEquals(delayConfirmCondition.onMarketTick(MockMarkets.withCurrentPrice(new BigDecimal("10.00"))),
                Signals.none());
        assertEquals(delayConfirmCondition.onMarketTick(MockMarkets.withCurrentPrice(new BigDecimal("10.00"))),
                Signals.none());
        assertEquals(delayConfirmCondition.onMarketTick(MockMarkets.withCurrentPrice(new BigDecimal("10.00"))),
                Signals.buyOrSell());
    }

    @Test
    public void testCanceled() throws Exception {
        ContinuousDelayConfirmCondition delayConfirmCondition = new ContinuousDelayConfirmCondition(3,
                new PriceCondition(CompareOperator.GE, new BigDecimal("10.00")));

        assertEquals(delayConfirmCondition.onMarketTick(MockMarkets.withCurrentPrice(new BigDecimal("10.00"))),
                Signals.none());
        assertEquals(delayConfirmCondition.onMarketTick(MockMarkets.withCurrentPrice(new BigDecimal("9.00"))),
                Signals.none());
        assertEquals(delayConfirmCondition.onMarketTick(MockMarkets.withCurrentPrice(new BigDecimal("10.00"))),
                Signals.none());
        assertEquals(delayConfirmCondition.onMarketTick(MockMarkets.withCurrentPrice(new BigDecimal("10.00"))),
                Signals.none());
        assertEquals(delayConfirmCondition.onMarketTick(MockMarkets.withCurrentPrice(new BigDecimal("10.00"))),
                Signals.buyOrSell());
    }

    @Test
    public void testEquals() throws Exception {
        ContinuousDelayConfirmCondition delayConfirmCondition1 = new ContinuousDelayConfirmCondition(3,
                new PriceCondition(CompareOperator.GE, new BigDecimal("10.00")));

        ContinuousDelayConfirmCondition delayConfirmCondition2 = new ContinuousDelayConfirmCondition(3,
                new PriceCondition(CompareOperator.GE, new BigDecimal("10.00")));

        assertEquals(delayConfirmCondition1, delayConfirmCondition2);
        assertEquals(delayConfirmCondition1.hashCode(), delayConfirmCondition2.hashCode());
        System.out.println(delayConfirmCondition1);
    }
}
