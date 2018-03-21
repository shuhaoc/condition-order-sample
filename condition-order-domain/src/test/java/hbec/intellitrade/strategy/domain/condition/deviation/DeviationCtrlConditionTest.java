package hbec.intellitrade.strategy.domain.condition.deviation;

import hbec.intellitrade.condorder.domain.orders.price.PriceCondition;
import hbec.intellitrade.mock.MockMarkets;
import hbec.intellitrade.strategy.domain.factor.BasicTargetPriceFactor;
import hbec.intellitrade.strategy.domain.factor.CompareOperator;
import hbec.intellitrade.strategy.domain.signal.Signals;
import hbec.intellitrade.strategy.domain.signal.TradeSignal;
import org.testng.annotations.Test;

import java.math.BigDecimal;

import static org.testng.Assert.assertEquals;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/1/29
 */
public class DeviationCtrlConditionTest {
    @Test
    public void testPriceCondition() throws Exception {
        DeviationCtrlConditionImpl deviationCtrlCondition = new DeviationCtrlConditionImpl(
                new BigDecimal(1), new PriceCondition(CompareOperator.GE, new BigDecimal("10.00"))
        );

        TradeSignal tradeSignal1 = deviationCtrlCondition.onMarketTick(MockMarkets.withCurrentPrice(new BigDecimal(
                "9.99")));
        assertEquals(tradeSignal1, Signals.none());

        TradeSignal tradeSignal2 = deviationCtrlCondition.onMarketTick(MockMarkets.withCurrentPrice(new BigDecimal(
                "10.00")));
        assertEquals(tradeSignal2, Signals.buyOrSell());

        // 偏差刚好等于1%，视为超出
        TradeSignal tradeSignal3 = deviationCtrlCondition.onMarketTick(MockMarkets.withCurrentPrice(new BigDecimal(
                "10.10")));
        assertEquals(tradeSignal3, Signals.buyOrSell().withDeviationExceeded());

        TradeSignal tradeSignal4 = deviationCtrlCondition.onMarketTick(MockMarkets.withCurrentPrice(new BigDecimal(
                "10.11")));
        assertEquals(tradeSignal4, Signals.buyOrSell().withDeviationExceeded());

        assertEquals(deviationCtrlCondition.getTargetPriceFactor(), new BasicTargetPriceFactor(CompareOperator.GE, new BigDecimal("10.00")));
        assertEquals(deviationCtrlCondition.getLimitPercent(), new BigDecimal("1"));
    }
}
