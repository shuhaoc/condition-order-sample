package me.caosh.condition.domain.model.strategy.condition.timerange;

import me.caosh.condition.domain.model.condition.PriceCondition;
import me.caosh.condition.domain.model.constants.SecurityType;
import me.caosh.condition.domain.model.market.MarketID;
import me.caosh.condition.domain.model.market.RealTimeMarket;
import me.caosh.condition.domain.model.share.ValuedEnumUtil;
import me.caosh.condition.domain.model.signal.Signals;
import me.caosh.condition.domain.model.strategy.factor.CompareOperator;
import me.caosh.condition.domain.model.strategy.shared.Week;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.Collections;

import static org.testng.Assert.assertEquals;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/2/7
 */
public class MonitorTimeRangeConditionTest {
    @Test
    public void testNone() throws Exception {
        Week weekToday = ValuedEnumUtil.valueOf(LocalDate.now().getDayOfWeek(), Week.class);
        MonitorTimeRangeCondition condition = new MonitorTimeRangeCondition(
                new WeekTimeRange(new WeekRange(weekToday, weekToday),
                        new LocalTimeRange(LocalTime.now().minusHours(2), LocalTime.now().minusHours(1))),
                new PriceCondition(CompareOperator.GE, new BigDecimal("10.00")));
        assertEquals(condition.onMarketUpdate(getMockRealTimeMarket(new BigDecimal("10.01"))), Signals.none());
    }

    @Test
    public void testTrigger() throws Exception {
        Week weekToday = ValuedEnumUtil.valueOf(LocalDate.now().getDayOfWeek(), Week.class);
        MonitorTimeRangeCondition condition = new MonitorTimeRangeCondition(
                new WeekTimeRange(new WeekRange(weekToday, weekToday),
                        new LocalTimeRange(LocalTime.now().minusHours(1), LocalTime.now().plusHours(1))),
                new PriceCondition(CompareOperator.GE, new BigDecimal("10.00")));
        assertEquals(condition.onMarketUpdate(getMockRealTimeMarket(new BigDecimal("10.01"))), Signals.buyOrSell());
    }

    private RealTimeMarket getMockRealTimeMarket(BigDecimal currentPrice) {
        return new RealTimeMarket(new MarketID(SecurityType.STOCK, "600000"),
                currentPrice,
                new BigDecimal("9.90"),
                Collections.<BigDecimal>emptyList(),
                LocalDateTime.now());
    }
}
