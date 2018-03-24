package me.caosh.condition.infrastructure.repository.model;

import hbec.intellitrade.common.market.index.IndexSource;
import hbec.intellitrade.common.security.SecurityExchange;
import hbec.intellitrade.common.security.SecurityInfo;
import hbec.intellitrade.common.security.SecurityType;
import hbec.intellitrade.condorder.domain.ConditionOrder;
import hbec.intellitrade.condorder.domain.OrderState;
import hbec.intellitrade.condorder.domain.TradeCustomerInfo;
import hbec.intellitrade.condorder.domain.orders.price.DecoratedPriceCondition;
import hbec.intellitrade.condorder.domain.orders.price.PriceCondition;
import hbec.intellitrade.condorder.domain.orders.price.PriceOrder;
import hbec.intellitrade.condorder.domain.strategyinfo.NativeStrategyInfo;
import hbec.intellitrade.condorder.domain.trackindex.TrackIndexOption;
import hbec.intellitrade.condorder.domain.trackindex.TrackedIndexInfo;
import hbec.intellitrade.condorder.domain.tradeplan.EntrustMethod;
import hbec.intellitrade.condorder.domain.tradeplan.EntrustStrategy;
import hbec.intellitrade.condorder.domain.tradeplan.OfferedPriceTradePlan;
import hbec.intellitrade.condorder.domain.tradeplan.TradeNumberByAmount;
import hbec.intellitrade.strategy.domain.condition.delayconfirm.DelayConfirmInfo;
import hbec.intellitrade.strategy.domain.condition.delayconfirm.DelayConfirmOption;
import hbec.intellitrade.strategy.domain.condition.deviation.DeviationCtrlInfo;
import hbec.intellitrade.strategy.domain.condition.deviation.DeviationCtrlOption;
import hbec.intellitrade.strategy.domain.factor.CompareOperator;
import hbec.intellitrade.strategy.domain.shared.Week;
import hbec.intellitrade.strategy.domain.timerange.LocalTimeRange;
import hbec.intellitrade.strategy.domain.timerange.MonitorTimeRangeOption;
import hbec.intellitrade.strategy.domain.timerange.WeekRange;
import hbec.intellitrade.strategy.domain.timerange.WeekTimeRange;
import hbec.intellitrade.trade.domain.ExchangeType;
import me.caosh.condition.infrastructure.repository.assembler.ConditionOrderDoAssembler;
import me.caosh.condition.infrastructure.tunnel.model.ConditionOrderDO;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.testng.annotations.Test;

import java.math.BigDecimal;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;


/**
 * @author caoshuhao@touker.com
 * @date 2018/2/3
 */
public class ConditionOrderDoAssemblerTest {

    private static final long ORDER_ID = 123L;
    private static final int USER_ID = 34;
    private static final String CUSTOMER_NO = "012345";
    private static final String SECURITY_CODE = "600000";
    private static final String SECURITY_NAME = "浦发银行";

    @Test
    public void testPriceConditionOrder() throws Exception {
        ConditionOrder conditionOrder = new PriceOrder(
                ORDER_ID,
                new TradeCustomerInfo(USER_ID, CUSTOMER_NO),
                OrderState.ACTIVE,
                new SecurityInfo(SecurityType.STOCK, SECURITY_CODE, SecurityExchange.SH, SECURITY_NAME),
                new DecoratedPriceCondition(
                        new PriceCondition(CompareOperator.GE, new BigDecimal("9999.00")),
                        new DelayConfirmInfo(DelayConfirmOption.ACCUMULATE, 3),
                        new DeviationCtrlInfo(new BigDecimal("1")),
                        0),
                LocalDateTime.parse("2018-03-12T15:00:00"),
                new OfferedPriceTradePlan(ExchangeType.SELL, EntrustStrategy.BUY1,
                                          new TradeNumberByAmount(new BigDecimal("10000.00"))),
                new TrackedIndexInfo(IndexSource.SZ, "399001", "深证成指"),
                new WeekTimeRange(new WeekRange(Week.TUE, Week.THU),
                                  new LocalTimeRange(LocalTime.parse("10:00:00"), LocalTime.parse("10:30:00"))));
        ConditionOrderDO conditionOrderDO = ConditionOrderDoAssembler.assemble(conditionOrder);

        assertEquals(conditionOrderDO.getOrderId().longValue(), ORDER_ID);
        assertEquals(conditionOrderDO.getUserId().intValue(), USER_ID);
        assertEquals(conditionOrderDO.getCustomerNo(), CUSTOMER_NO);
        assertEquals(conditionOrderDO.getDeleted(), Boolean.FALSE);
        assertEquals(conditionOrderDO.getOrderState(), OrderState.ACTIVE.getValue());
        assertEquals(conditionOrderDO.getSecurityType(), SecurityType.STOCK.getValue());
        assertEquals(conditionOrderDO.getSecurityCode(), SECURITY_CODE);
        assertEquals(conditionOrderDO.getSecurityExchange(), SecurityExchange.SH.name());
        assertEquals(conditionOrderDO.getSecurityName(), SECURITY_NAME);
        assertEquals(conditionOrderDO.getStrategyType().intValue(), NativeStrategyInfo.PRICE.getStrategyType());
        assertEquals(conditionOrderDO.getTrackedIndexOption(), TrackIndexOption.ENABLED.getValue());
        assertEquals(conditionOrderDO.getTrackedIndexSource(), IndexSource.SZ.name());
        assertEquals(conditionOrderDO.getTrackedIndexCode(), "399001");
        assertEquals(conditionOrderDO.getTrackedIndexName(), "深证成指");
        assertEquals(conditionOrderDO.getConditionProperties(),
                     "{\"type\":\"PriceConditionDO\",\"compareOperator\":1,\"targetPrice\":9999.00}");
        assertNull(conditionOrderDO.getDynamicPropertiesObj());
        assertEquals(conditionOrderDO.getExpireTime(), LocalDateTime.parse("2018-03-12T15:00:00").toDate());
        assertEquals(conditionOrderDO.getExchangeType(), ExchangeType.SELL.getValue());
        assertEquals(conditionOrderDO.getEntrustStrategy(), EntrustStrategy.BUY1.getValue());
        assertEquals(conditionOrderDO.getEntrustMethod(), EntrustMethod.AMOUNT.getValue());
        assertEquals(conditionOrderDO.getEntrustAmount(), new BigDecimal("10000.00"));
        assertEquals(conditionOrderDO.getDelayConfirmOption(), DelayConfirmOption.ACCUMULATE.getValue());
        assertEquals(conditionOrderDO.getDelayConfirmTimes().intValue(), 3);
        assertEquals(conditionOrderDO.getMonitorTimeRangeOption(), MonitorTimeRangeOption.ENABLED.getValue());
        assertEquals(conditionOrderDO.getBeginWeek(), Week.TUE.getValue());
        assertEquals(conditionOrderDO.getEndWeek(), Week.THU.getValue());
        assertEquals(conditionOrderDO.getBeginTime(), LocalTime.parse("10:00:00").toDateTimeToday().toDate());
        assertEquals(conditionOrderDO.getEndTime(), LocalTime.parse("10:30:00").toDateTimeToday().toDate());
        assertEquals(conditionOrderDO.getDeviationCtrlOption(), DeviationCtrlOption.ENABLED.getValue());
        assertEquals(conditionOrderDO.getDeviationLimitPercent(), new BigDecimal("1"));
        assertNull(conditionOrderDO.getCreateTime());
        assertNull(conditionOrderDO.getUpdateTime());

        ConditionOrder conditionOrder1 = ConditionOrderDoAssembler.disassemble(conditionOrderDO);
        assertEquals(conditionOrder1, conditionOrder);
    }

}
