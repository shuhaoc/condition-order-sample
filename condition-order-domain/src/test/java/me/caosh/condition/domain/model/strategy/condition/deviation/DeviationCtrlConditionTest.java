package me.caosh.condition.domain.model.strategy.condition.deviation;

import hbec.intellitrade.strategy.domain.factor.BasicTargetPriceFactor;
import hbec.intellitrade.strategy.domain.factor.CompareOperator;
import me.caosh.condition.domain.model.condition.PriceCondition;
import me.caosh.condition.domain.model.constants.SecurityType;
import me.caosh.condition.domain.model.market.MarketID;
import me.caosh.condition.domain.model.market.RealTimeMarket;
import me.caosh.condition.domain.model.signal.Signals;
import me.caosh.condition.domain.model.signal.TradeSignal;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.Collections;

import static org.testng.Assert.assertEquals;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/1/29
 */
public class DeviationCtrlConditionTest {
    @Test
    public void testDisabled() throws Exception {
        DisabledDeviationCtrlCondition deviationCtrlCondition = new DisabledDeviationCtrlCondition(
                new PriceCondition(CompareOperator.GE, new BigDecimal("10.00")));

        assertEquals(deviationCtrlCondition.getTargetPriceFactor(), new BasicTargetPriceFactor(CompareOperator.GE, new BigDecimal("10.00")));

        TradeSignal tradeSignal1 = deviationCtrlCondition.onMarketUpdate(getMockRealTimeMarket(new BigDecimal("9.99")));
        assertEquals(tradeSignal1, Signals.none());

        TradeSignal tradeSignal2 = deviationCtrlCondition.onMarketUpdate(getMockRealTimeMarket(new BigDecimal("10.00")));
        assertEquals(tradeSignal2, Signals.buyOrSell());

        TradeSignal tradeSignal3 = deviationCtrlCondition.onMarketUpdate(getMockRealTimeMarket(new BigDecimal("10.10")));
        assertEquals(tradeSignal3, Signals.buyOrSell());

        TradeSignal tradeSignal4 = deviationCtrlCondition.onMarketUpdate(getMockRealTimeMarket(new BigDecimal("10.11")));
        assertEquals(tradeSignal4, Signals.buyOrSell());
    }

    @Test
    public void testPriceCondition() throws Exception {
        EnabledDeviationCtrlCondition deviationCtrlCondition = new EnabledDeviationCtrlCondition(
                new PriceCondition(CompareOperator.GE, new BigDecimal("10.00")),
                new BigDecimal(1));

        TradeSignal tradeSignal1 = deviationCtrlCondition.onMarketUpdate(getMockRealTimeMarket(new BigDecimal("9.99")));
        assertEquals(tradeSignal1, Signals.none());

        TradeSignal tradeSignal2 = deviationCtrlCondition.onMarketUpdate(getMockRealTimeMarket(new BigDecimal("10.00")));
        assertEquals(tradeSignal2, Signals.buyOrSell());

        // 偏差刚好等于1%，视为超出
        TradeSignal tradeSignal3 = deviationCtrlCondition.onMarketUpdate(getMockRealTimeMarket(new BigDecimal("10.10")));
        assertEquals(tradeSignal3, Signals.buyOrSell().withDeviationExceeded());

        TradeSignal tradeSignal4 = deviationCtrlCondition.onMarketUpdate(getMockRealTimeMarket(new BigDecimal("10.11")));
        assertEquals(tradeSignal4, Signals.buyOrSell().withDeviationExceeded());

        assertEquals(deviationCtrlCondition.getTargetPriceFactor(), new BasicTargetPriceFactor(CompareOperator.GE, new BigDecimal("10.00")));
        assertEquals(deviationCtrlCondition.getDeviationLimitPercent(), new BigDecimal("1"));
    }

    private RealTimeMarket getMockRealTimeMarket(BigDecimal currentPrice) {
        return new RealTimeMarket(new MarketID(SecurityType.STOCK, "600000"),
                currentPrice,
                new BigDecimal("9.90"),
                Collections.<BigDecimal>emptyList());
    }
}
