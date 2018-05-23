package me.caosh.condition.domain.dto.order;

import hbec.commons.domain.intellitrade.condition.GridConditionDTO;
import hbec.commons.domain.intellitrade.condition.NewStockPurchaseConditionDTO;
import hbec.commons.domain.intellitrade.condition.PriceConditionDTO;
import hbec.commons.domain.intellitrade.condition.TimeReachedConditionDTO;
import hbec.commons.domain.intellitrade.condition.TurnPointConditionDTO;
import hbec.commons.domain.intellitrade.conditionorder.ConditionOrderDTO;
import hbec.commons.domain.intellitrade.conditionorder.DelayConfirmDTO;
import hbec.commons.domain.intellitrade.conditionorder.DeviationCtrlDTO;
import hbec.commons.domain.intellitrade.conditionorder.MonitorTimeRangeDTO;
import hbec.commons.domain.intellitrade.conditionorder.TradeCustomerInfoDTO;
import hbec.commons.domain.intellitrade.conditionorder.TradePlanDTO;
import hbec.commons.domain.intellitrade.market.TrackedIndexDTO;
import hbec.commons.domain.intellitrade.security.SecurityInfoDTO;
import hbec.commons.domain.intellitrade.util.ConditionOrderAssemblers;
import hbec.commons.domain.intellitrade.util.ConditionOrderDtoAssembler;
import hbec.intellitrade.common.market.MarketSource;
import hbec.intellitrade.common.security.SecurityExchange;
import hbec.intellitrade.common.security.SecurityInfo;
import hbec.intellitrade.common.security.SecurityType;
import hbec.intellitrade.conditionorder.domain.ConditionOrder;
import hbec.intellitrade.conditionorder.domain.OrderState;
import hbec.intellitrade.conditionorder.domain.TradeCustomerInfo;
import hbec.intellitrade.conditionorder.domain.orders.grid.DecoratedGridCondition;
import hbec.intellitrade.conditionorder.domain.orders.grid.GridTradeOrder;
import hbec.intellitrade.conditionorder.domain.orders.grid.InflexionSubCondition;
import hbec.intellitrade.conditionorder.domain.orders.newstock.NewStockOrder;
import hbec.intellitrade.conditionorder.domain.orders.newstock.NewStockPurchaseCondition;
import hbec.intellitrade.conditionorder.domain.orders.price.DecoratedPriceCondition;
import hbec.intellitrade.conditionorder.domain.orders.price.PriceCondition;
import hbec.intellitrade.conditionorder.domain.orders.price.PriceOrder;
import hbec.intellitrade.conditionorder.domain.orders.time.TimeOrder;
import hbec.intellitrade.conditionorder.domain.orders.time.TimeReachedCondition;
import hbec.intellitrade.conditionorder.domain.orders.turnpoint.DecoratedTurnPointCondition;
import hbec.intellitrade.conditionorder.domain.orders.turnpoint.TurnPointCondition;
import hbec.intellitrade.conditionorder.domain.orders.turnpoint.TurnPointOrder;
import hbec.intellitrade.conditionorder.domain.trackindex.TrackedIndexInfo;
import hbec.intellitrade.conditionorder.domain.tradeplan.EntrustStrategy;
import hbec.intellitrade.conditionorder.domain.tradeplan.OfferedPriceBidirectionalTradePlan;
import hbec.intellitrade.conditionorder.domain.tradeplan.OfferedPriceTradePlan;
import hbec.intellitrade.conditionorder.domain.tradeplan.TradeNumberByAmount;
import hbec.intellitrade.conditionorder.domain.tradeplan.TradeNumberDirect;
import hbec.intellitrade.strategy.domain.condition.delayconfirm.DelayConfirmInfo;
import hbec.intellitrade.strategy.domain.condition.delayconfirm.DelayConfirmOption;
import hbec.intellitrade.strategy.domain.condition.deviation.DeviationCtrlInfo;
import hbec.intellitrade.strategy.domain.factor.BinaryFactorType;
import hbec.intellitrade.strategy.domain.factor.CompareOperator;
import hbec.intellitrade.strategy.domain.factor.PercentBinaryTargetPriceFactor;
import hbec.intellitrade.strategy.domain.shared.Week;
import hbec.intellitrade.strategy.domain.timerange.LocalTimeRange;
import hbec.intellitrade.strategy.domain.timerange.WeekRange;
import hbec.intellitrade.strategy.domain.timerange.WeekTimeRange;
import hbec.intellitrade.trade.domain.ExchangeType;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.testng.annotations.Test;

import java.math.BigDecimal;

import static org.testng.Assert.assertEquals;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/3/15
 */
public class ConditionOrderDtoAssemblerTest {

    private static final long ORDER_ID = 123L;
    private static final int USER_ID = 303348;
    private static final String CUSTOMER_NO = "010000061086";

    @Test
    public void testPriceOrder() throws Exception {
        PriceOrder priceOrder = new PriceOrder(ORDER_ID,
                new TradeCustomerInfo(USER_ID, CUSTOMER_NO),
                                               OrderState.ACTIVE,
                                               new SecurityInfo(SecurityType.STOCK,
                                                                "600000",
                                                                SecurityExchange.SH,
                                                                "浦发银行"),
                                               new DecoratedPriceCondition(
                                                       new PriceCondition(CompareOperator.GE,
                                                                          new BigDecimal("9999.00")),
                                                       new DelayConfirmInfo(DelayConfirmOption.ACCUMULATE, 3),
                                                       new DeviationCtrlInfo(new BigDecimal("1.00")), 2
                                               ),
                                               LocalDateTime.parse("2018-03-15T15:00:00"),
                                               new OfferedPriceTradePlan(ExchangeType.BUY,
                                                                         EntrustStrategy.CURRENT_PRICE,
                                                                         new TradeNumberDirect(1000)),
                                               new TrackedIndexInfo(MarketSource.SZ, "399001", "深证成指"),
                                               new WeekTimeRange(new WeekRange(Week.TUE, Week.THU),
                                                                 new LocalTimeRange(LocalTime.parse("10:00:00"),
                                                                                    LocalTime.parse("14:00:00")))
        );

        ConditionOrderDTO assemble = ConditionOrderAssemblers.dtoSupported()
                                                             .assemble(priceOrder, ConditionOrderDTO.class);

        ConditionOrderDTO conditionOrderDTO = new ConditionOrderDTO();
        conditionOrderDTO.setOrderId(123L);

        TradeCustomerInfoDTO tradeCustomerInfoDTO = new TradeCustomerInfoDTO();
        tradeCustomerInfoDTO.setUserId(303348);
        tradeCustomerInfoDTO.setCustomerNo("010000061086");
        conditionOrderDTO.setCustomer(tradeCustomerInfoDTO);

        conditionOrderDTO.setDeleted(false);
        conditionOrderDTO.setOrderState(1);

        SecurityInfoDTO securityInfo = new SecurityInfoDTO();
        securityInfo.setType(4);
        securityInfo.setCode("600000");
        securityInfo.setName("浦发银行");
        securityInfo.setExchange("SH");
        conditionOrderDTO.setSecurityInfo(securityInfo);

        TrackedIndexDTO trackedIndexInfo = new TrackedIndexDTO();
        trackedIndexInfo.setOption(1);
        trackedIndexInfo.setSource("SZ");
        trackedIndexInfo.setCode("399001");
        trackedIndexInfo.setName("深证成指");
        conditionOrderDTO.setTrackedIndex(trackedIndexInfo);

        conditionOrderDTO.setStrategyType(1);

        PriceConditionDTO priceCondition = new PriceConditionDTO();
        priceCondition.setCompareOperator(1);
        priceCondition.setTargetPrice(new BigDecimal("9999.00"));
        priceCondition.setDelayConfirmedCount(2);

        conditionOrderDTO.setCondition(priceCondition);

        conditionOrderDTO.setExpireTime("2018-03-15 15:00:00");

        TradePlanDTO tradePlan = new TradePlanDTO();
        tradePlan.setExchangeType(1);
        tradePlan.setEntrustStrategy(1);
        tradePlan.setEntrustMethod(0);
        tradePlan.setEntrustNumber(1000);
        tradePlan.setOrderType(0);
        conditionOrderDTO.setTradePlan(tradePlan);

        MonitorTimeRangeDTO monitorTimeRange = new MonitorTimeRangeDTO();
        monitorTimeRange.setOption(1);
        monitorTimeRange.setBeginWeek(2);
        monitorTimeRange.setEndWeek(4);
        monitorTimeRange.setBeginTime("10:00:00");
        monitorTimeRange.setEndTime("14:00:00");
        conditionOrderDTO.setMonitorTimeRange(monitorTimeRange);

        DelayConfirmDTO delayConfirmParam = new DelayConfirmDTO();
        delayConfirmParam.setOption(1);
        delayConfirmParam.setConfirmTimes(3);
        conditionOrderDTO.setDelayConfirm(delayConfirmParam);

        DeviationCtrlDTO deviationCtrlParam = new DeviationCtrlDTO();
        deviationCtrlParam.setOption(1);
        deviationCtrlParam.setLimitPercent(new BigDecimal("1.00"));
        conditionOrderDTO.setDeviationCtrl(deviationCtrlParam);

        assertEquals(assemble.toString(), conditionOrderDTO.toString());

        ConditionOrder disassemble = ConditionOrderAssemblers.dtoSupported()
                                                             .disassemble(conditionOrderDTO, ConditionOrder.class);
        assertEquals(disassemble, priceOrder);
    }

    @Test
    public void testTurnPointOrder() throws Exception {
        TurnPointOrder turnPointOrder = new TurnPointOrder(
                123L,
                new TradeCustomerInfo(303348, "010000061086"),
                OrderState.ACTIVE,
                new SecurityInfo(SecurityType.STOCK,
                                 "600000",
                                 SecurityExchange.SH,
                                 "浦发银行"),
                new DecoratedTurnPointCondition(new TurnPointCondition(
                        CompareOperator.LE,
                        new BigDecimal("11.00"),
                        BinaryFactorType.PERCENT,
                        new BigDecimal("1.00"),
                        null,
                        true,
                        true,
                        new BigDecimal("9.50")),
                                                new BigDecimal("9.00"),
                                                new DelayConfirmInfo(DelayConfirmOption.ACCUMULATE, 3),
                                                new DeviationCtrlInfo(new BigDecimal("1.00")),
                                                1,
                                                2),
                LocalDateTime.parse("2018-03-15T15:00:00"),
                new OfferedPriceTradePlan(ExchangeType.BUY,
                                          EntrustStrategy.CURRENT_PRICE,
                                          new TradeNumberDirect(1000)),
                new TrackedIndexInfo(MarketSource.SZ, "399001", "深证成指"),
                new WeekTimeRange(new WeekRange(Week.TUE, Week.THU),
                                  new LocalTimeRange(LocalTime.parse("10:00:00"),
                                                     LocalTime.parse("14:00:00")))
        );

        ConditionOrderDTO assemble = ConditionOrderDtoAssembler.assemble(turnPointOrder);
        System.out.println(assemble);

        ConditionOrderDTO conditionOrderDTO = new ConditionOrderDTO();
        conditionOrderDTO.setOrderId(123L);

        TradeCustomerInfoDTO tradeCustomerInfoDTO = new TradeCustomerInfoDTO();
        tradeCustomerInfoDTO.setUserId(303348);
        tradeCustomerInfoDTO.setCustomerNo("010000061086");
        conditionOrderDTO.setCustomer(tradeCustomerInfoDTO);

        conditionOrderDTO.setDeleted(false);
        conditionOrderDTO.setOrderState(1);

        SecurityInfoDTO securityInfo = new SecurityInfoDTO();
        securityInfo.setType(4);
        securityInfo.setCode("600000");
        securityInfo.setName("浦发银行");
        securityInfo.setExchange("SH");
        conditionOrderDTO.setSecurityInfo(securityInfo);

        TrackedIndexDTO trackedIndexInfo = new TrackedIndexDTO();
        trackedIndexInfo.setOption(1);
        trackedIndexInfo.setSource("SZ");
        trackedIndexInfo.setCode("399001");
        trackedIndexInfo.setName("深证成指");
        conditionOrderDTO.setTrackedIndex(trackedIndexInfo);

        conditionOrderDTO.setStrategyType(7);

        TurnPointConditionDTO condition = new TurnPointConditionDTO();
        condition.setCompareOperator(0);
        condition.setBreakPrice(new BigDecimal("11.00"));
        condition.setBinaryFactorType(0);
        condition.setTurnBackPercent(new BigDecimal("1.00"));
        condition.setUseGuaranteedPrice(true);
        condition.setBaselinePrice(new BigDecimal("9.00"));
        condition.setBroken(true);
        condition.setExtremePrice(new BigDecimal("9.50"));
        condition.setTurnPointDelayConfirmedCount(1);
        condition.setCrossDelayConfirmedCount(2);

        conditionOrderDTO.setCondition(condition);

        conditionOrderDTO.setExpireTime("2018-03-15 15:00:00");

        TradePlanDTO tradePlan = new TradePlanDTO();
        tradePlan.setExchangeType(1);
        tradePlan.setEntrustStrategy(1);
        tradePlan.setEntrustMethod(0);
        tradePlan.setEntrustNumber(1000);
        tradePlan.setOrderType(0);
        conditionOrderDTO.setTradePlan(tradePlan);

        MonitorTimeRangeDTO monitorTimeRange = new MonitorTimeRangeDTO();
        monitorTimeRange.setOption(1);
        monitorTimeRange.setBeginWeek(2);
        monitorTimeRange.setEndWeek(4);
        monitorTimeRange.setBeginTime("10:00:00");
        monitorTimeRange.setEndTime("14:00:00");
        conditionOrderDTO.setMonitorTimeRange(monitorTimeRange);

        DelayConfirmDTO delayConfirmParam = new DelayConfirmDTO();
        delayConfirmParam.setOption(1);
        delayConfirmParam.setConfirmTimes(3);
        conditionOrderDTO.setDelayConfirm(delayConfirmParam);

        DeviationCtrlDTO deviationCtrlParam = new DeviationCtrlDTO();
        deviationCtrlParam.setOption(1);
        deviationCtrlParam.setLimitPercent(new BigDecimal("1.00"));
        conditionOrderDTO.setDeviationCtrl(deviationCtrlParam);

        assertEquals(assemble.toString(), conditionOrderDTO.toString());

        ConditionOrder disassemble = ConditionOrderDtoAssembler.disassemble(conditionOrderDTO);
        assertEquals(disassemble, turnPointOrder);
    }

    @Test
    public void testTimeOrder() throws Exception {
        TimeOrder timeOrder = new TimeOrder(123L,
                new TradeCustomerInfo(303348, "010000061086"),
                OrderState.ACTIVE,
                new SecurityInfo(SecurityType.STOCK, "600000", SecurityExchange.SH, "浦发银行"),
                new TimeReachedCondition(LocalDateTime.parse("2018-04-29T10:00:00")),
                LocalDateTime.parse("2018-04-29T15:00:00"),
                new OfferedPriceTradePlan(ExchangeType.BUY,
                        EntrustStrategy.CURRENT_PRICE,
                        new TradeNumberByAmount(new BigDecimal("10000.00"))));

        ConditionOrderDTO assemble = ConditionOrderAssemblers.dtoSupported()
                .assemble(timeOrder, ConditionOrderDTO.class);

        ConditionOrderDTO conditionOrderDTO = new ConditionOrderDTO();
        conditionOrderDTO.setOrderId(123L);

        TradeCustomerInfoDTO tradeCustomerInfoDTO = new TradeCustomerInfoDTO();
        tradeCustomerInfoDTO.setUserId(303348);
        tradeCustomerInfoDTO.setCustomerNo("010000061086");
        conditionOrderDTO.setCustomer(tradeCustomerInfoDTO);

        conditionOrderDTO.setDeleted(false);
        conditionOrderDTO.setOrderState(1);

        SecurityInfoDTO securityInfo = new SecurityInfoDTO();
        securityInfo.setType(4);
        securityInfo.setCode("600000");
        securityInfo.setName("浦发银行");
        securityInfo.setExchange("SH");
        conditionOrderDTO.setSecurityInfo(securityInfo);

        conditionOrderDTO.setStrategyType(5);

        TimeReachedConditionDTO condition = new TimeReachedConditionDTO();
        condition.setTargetTime("2018-04-29 10:00:00");

        conditionOrderDTO.setCondition(condition);

        conditionOrderDTO.setExpireTime("2018-04-29 15:00:00");

        TradePlanDTO tradePlan = new TradePlanDTO();
        tradePlan.setExchangeType(1);
        tradePlan.setEntrustStrategy(1);
        tradePlan.setEntrustMethod(1);
        tradePlan.setEntrustAmount(new BigDecimal("10000.00"));
        tradePlan.setOrderType(0);
        conditionOrderDTO.setTradePlan(tradePlan);

        assertEquals(assemble.toString(), conditionOrderDTO.toString());

        ConditionOrder disassemble = ConditionOrderAssemblers.dtoSupported()
                .disassemble(conditionOrderDTO, ConditionOrder.class);
        assertEquals(disassemble, timeOrder);
    }

    @Test
    public void testNewStockOrder() throws Exception {
        NewStockOrder newStockOrder = new NewStockOrder(ORDER_ID,
                new TradeCustomerInfo(USER_ID, CUSTOMER_NO),
                OrderState.ACTIVE,
                new NewStockPurchaseCondition(LocalTime.parse("09:30:00"), true, 1),
                new LocalDateTime("2018-04-29T15:00:00"));

        ConditionOrderDTO assemble = ConditionOrderAssemblers.dtoSupported()
                .assemble(newStockOrder, ConditionOrderDTO.class);

        ConditionOrderDTO conditionOrderDTO = new ConditionOrderDTO();
        conditionOrderDTO.setOrderId(ORDER_ID);

        TradeCustomerInfoDTO tradeCustomerInfoDTO = new TradeCustomerInfoDTO();
        tradeCustomerInfoDTO.setUserId(USER_ID);
        tradeCustomerInfoDTO.setCustomerNo(CUSTOMER_NO);
        conditionOrderDTO.setCustomer(tradeCustomerInfoDTO);

        conditionOrderDTO.setDeleted(false);
        conditionOrderDTO.setOrderState(1);

        conditionOrderDTO.setStrategyType(10);

        NewStockPurchaseConditionDTO condition = new NewStockPurchaseConditionDTO();
        condition.setPurchaseTime("09:30:00");
        condition.setTodayTriggered(true);
        condition.setPurchasedCount(1);

        conditionOrderDTO.setCondition(condition);

        conditionOrderDTO.setExpireTime("2018-04-29 15:00:00");

        assertEquals(assemble.toString(), conditionOrderDTO.toString());

        ConditionOrder disassemble = ConditionOrderAssemblers.dtoSupported()
                .disassemble(conditionOrderDTO, ConditionOrder.class);
        assertEquals(disassemble, newStockOrder);
    }

    @Test
    public void testGridTradeOrder() throws Exception {
        BigDecimal basePrice = new BigDecimal("10.00");
        GridTradeOrder gridTradeOrder = new GridTradeOrder(ORDER_ID,
                new TradeCustomerInfo(USER_ID, CUSTOMER_NO),
                OrderState.ACTIVE,
                new SecurityInfo(SecurityType.STOCK,
                        "600000",
                        SecurityExchange.SH,
                        "浦发银行"),
                new DecoratedGridCondition(basePrice,
                        BinaryFactorType.PERCENT,
                        new InflexionSubCondition(new PercentBinaryTargetPriceFactor(CompareOperator.LE,
                                new BigDecimal("-2.00")),
                                new PercentBinaryTargetPriceFactor(CompareOperator.GE, new BigDecimal("0.75")),
                                basePrice,
                                true),
                        new InflexionSubCondition(new PercentBinaryTargetPriceFactor(CompareOperator.GE,
                                new BigDecimal("1.00")),
                                new PercentBinaryTargetPriceFactor(CompareOperator.LE, new BigDecimal("-0.50")),
                                basePrice,
                                true),
                        true,
                        new DelayConfirmInfo(DelayConfirmOption.ACCUMULATE, 3),
                        new DeviationCtrlInfo(new BigDecimal("1.00")),
                        1,
                        2),
                LocalDateTime.parse("2018-03-15T15:00:00"),
                new OfferedPriceBidirectionalTradePlan(new TradeNumberByAmount(new BigDecimal("10000.00")),
                        EntrustStrategy.SELL3, EntrustStrategy.BUY3),
                new WeekTimeRange(new WeekRange(Week.TUE, Week.THU),
                        new LocalTimeRange(LocalTime.parse("10:00:00"), LocalTime.parse("14:00:00"))));

        ConditionOrderDTO assemble = ConditionOrderDtoAssembler.assemble(gridTradeOrder);
        System.out.println(assemble);

        ConditionOrderDTO conditionOrderDTO = new ConditionOrderDTO();
        conditionOrderDTO.setOrderId(ORDER_ID);

        TradeCustomerInfoDTO tradeCustomerInfoDTO = new TradeCustomerInfoDTO();
        tradeCustomerInfoDTO.setUserId(USER_ID);
        tradeCustomerInfoDTO.setCustomerNo(CUSTOMER_NO);
        conditionOrderDTO.setCustomer(tradeCustomerInfoDTO);

        conditionOrderDTO.setDeleted(false);
        conditionOrderDTO.setOrderState(1);

        SecurityInfoDTO securityInfo = new SecurityInfoDTO();
        securityInfo.setType(4);
        securityInfo.setCode("600000");
        securityInfo.setName("浦发银行");
        securityInfo.setExchange("SH");
        conditionOrderDTO.setSecurityInfo(securityInfo);

        conditionOrderDTO.setStrategyType(9);

        GridConditionDTO condition = new GridConditionDTO();
        condition.setBasePrice(new BigDecimal("10.00"));
        condition.setBinaryFactorType(0);
        condition.setIncreasePercent(new BigDecimal("1.00"));
        condition.setFallPercent(new BigDecimal("-0.50"));
        condition.setDecreasePercent(new BigDecimal("-2.00"));
        condition.setReboundPercent(new BigDecimal("0.75"));
        condition.setUseGuaranteedPrice(true);
        condition.setBuyDelayConfirmedCount(1);
        condition.setSellDelayConfirmedCount(2);
        conditionOrderDTO.setCondition(condition);

        conditionOrderDTO.setExpireTime("2018-03-15 15:00:00");

        TradePlanDTO tradePlan = new TradePlanDTO();
        tradePlan.setBuyStrategy(4);
        tradePlan.setSellStrategy(9);
        tradePlan.setEntrustMethod(1);
        tradePlan.setEntrustAmount(new BigDecimal("10000.00"));
        tradePlan.setOrderType(0);
        conditionOrderDTO.setTradePlan(tradePlan);

        MonitorTimeRangeDTO monitorTimeRange = new MonitorTimeRangeDTO();
        monitorTimeRange.setOption(1);
        monitorTimeRange.setBeginWeek(2);
        monitorTimeRange.setEndWeek(4);
        monitorTimeRange.setBeginTime("10:00:00");
        monitorTimeRange.setEndTime("14:00:00");
        conditionOrderDTO.setMonitorTimeRange(monitorTimeRange);

        DelayConfirmDTO delayConfirmParam = new DelayConfirmDTO();
        delayConfirmParam.setOption(1);
        delayConfirmParam.setConfirmTimes(3);
        conditionOrderDTO.setDelayConfirm(delayConfirmParam);

        DeviationCtrlDTO deviationCtrlParam = new DeviationCtrlDTO();
        deviationCtrlParam.setOption(1);
        deviationCtrlParam.setLimitPercent(new BigDecimal("1.00"));
        conditionOrderDTO.setDeviationCtrl(deviationCtrlParam);

        assertEquals(assemble.toString(), conditionOrderDTO.toString());

        ConditionOrder disassemble = ConditionOrderDtoAssembler.disassemble(conditionOrderDTO);
        assertEquals(disassemble, gridTradeOrder);
    }
}
