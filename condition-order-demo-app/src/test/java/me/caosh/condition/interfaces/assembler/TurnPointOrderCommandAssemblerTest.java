package me.caosh.condition.interfaces.assembler;

import hbec.commons.domain.intellitrade.conditionorder.DelayConfirmDTO;
import hbec.commons.domain.intellitrade.conditionorder.DeviationCtrlDTO;
import hbec.commons.domain.intellitrade.conditionorder.MonitorTimeRangeDTO;
import hbec.commons.domain.intellitrade.conditionorder.TradePlanDTO;
import hbec.commons.domain.intellitrade.market.TrackedIndexDTO;
import hbec.commons.domain.intellitrade.security.SecurityInfoDTO;
import hbec.intellitrade.common.market.MarketSource;
import hbec.intellitrade.common.security.SecurityExchange;
import hbec.intellitrade.common.security.SecurityInfo;
import hbec.intellitrade.common.security.SecurityType;
import hbec.intellitrade.conditionorder.domain.OrderState;
import hbec.intellitrade.conditionorder.domain.TradeCustomerInfo;
import hbec.intellitrade.conditionorder.domain.orders.turnpoint.DecoratedTurnPointCondition;
import hbec.intellitrade.conditionorder.domain.orders.turnpoint.TurnPointCondition;
import hbec.intellitrade.conditionorder.domain.orders.turnpoint.TurnPointOrder;
import hbec.intellitrade.conditionorder.domain.trackindex.TrackedIndexInfo;
import hbec.intellitrade.conditionorder.domain.tradeplan.EntrustStrategy;
import hbec.intellitrade.conditionorder.domain.tradeplan.OfferedPriceTradePlan;
import hbec.intellitrade.conditionorder.domain.tradeplan.TradeNumberByAmount;
import hbec.intellitrade.strategy.domain.condition.delayconfirm.DelayConfirmInfo;
import hbec.intellitrade.strategy.domain.condition.delayconfirm.DelayConfirmOption;
import hbec.intellitrade.strategy.domain.condition.deviation.DeviationCtrlInfo;
import hbec.intellitrade.strategy.domain.factor.BinaryFactorType;
import hbec.intellitrade.strategy.domain.factor.CompareOperator;
import hbec.intellitrade.strategy.domain.shared.Week;
import hbec.intellitrade.strategy.domain.timerange.LocalTimeRange;
import hbec.intellitrade.strategy.domain.timerange.WeekRange;
import hbec.intellitrade.strategy.domain.timerange.WeekTimeRange;
import hbec.intellitrade.trade.domain.ExchangeType;
import me.caosh.condition.interfaces.command.TurnPointConditionCommandDTO;
import me.caosh.condition.interfaces.command.TurnPointOrderCreateCommand;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.testng.annotations.Test;

import java.math.BigDecimal;

import static org.testng.Assert.assertEquals;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/3/15
 */
public class TurnPointOrderCommandAssemblerTest {
    @Test
    public void test() throws Exception {
        TurnPointOrderCreateCommand createCommand = new TurnPointOrderCreateCommand();
        SecurityInfoDTO securityInfo = new SecurityInfoDTO();
        securityInfo.setType(4);
        securityInfo.setCode("600000");
        securityInfo.setName("浦发银行");
        securityInfo.setExchange("SH");
        createCommand.setSecurityInfo(securityInfo);

        TrackedIndexDTO trackedIndexInfo = new TrackedIndexDTO();
        trackedIndexInfo.setOption(1);
        trackedIndexInfo.setSource("SZ");
        trackedIndexInfo.setCode("399001");
        trackedIndexInfo.setName("深证成指");
        createCommand.setTrackedIndex(trackedIndexInfo);

        TurnPointConditionCommandDTO turnPointConditionCommandDTO = new TurnPointConditionCommandDTO();
        turnPointConditionCommandDTO.setCompareOperator(0);
        turnPointConditionCommandDTO.setBreakPrice(new BigDecimal("11.00"));
        turnPointConditionCommandDTO.setBinaryFactorType(0);
        turnPointConditionCommandDTO.setTurnBackPercent(new BigDecimal("1.00"));
        turnPointConditionCommandDTO.setUseGuaranteedPrice(true);
        turnPointConditionCommandDTO.setBaselinePrice(new BigDecimal("9.00"));

        createCommand.setCondition(turnPointConditionCommandDTO);

        createCommand.setExpireTime(LocalDateTime.parse("2018-03-15T15:00:00").toDate());

        TradePlanDTO tradePlan = new TradePlanDTO();
        tradePlan.setExchangeType(1);
        tradePlan.setEntrustStrategy(1);
        tradePlan.setEntrustMethod(1);
        tradePlan.setEntrustAmount(new BigDecimal("10000.00"));
        createCommand.setTradePlan(tradePlan);

        MonitorTimeRangeDTO monitorTimeRange = new MonitorTimeRangeDTO();
        monitorTimeRange.setOption(1);
        monitorTimeRange.setBeginWeek(2);
        monitorTimeRange.setEndWeek(4);
        monitorTimeRange.setBeginTime("10:00:00");
        monitorTimeRange.setEndTime("14:00:00");
        createCommand.setMonitorTimeRange(monitorTimeRange);

        DelayConfirmDTO delayConfirmParam = new DelayConfirmDTO();
        delayConfirmParam.setOption(1);
        delayConfirmParam.setConfirmTimes(3);
        createCommand.setDelayConfirm(delayConfirmParam);

        DeviationCtrlDTO deviationCtrlParam = new DeviationCtrlDTO();
        deviationCtrlParam.setOption(1);
        deviationCtrlParam.setLimitPercent(new BigDecimal(1));
        createCommand.setDeviationCtrl(deviationCtrlParam);

        TradeCustomerInfo tradeCustomerInfo = new TradeCustomerInfo(303348, "010000061086");
        TurnPointOrder turnPointOrder = TurnPointOrderCommandAssembler.assemble(123L, tradeCustomerInfo, createCommand);

        TurnPointOrder expected = new TurnPointOrder(
                123L,
                new TradeCustomerInfo(303348, "010000061086"),
                OrderState.ACTIVE,
                new SecurityInfo(SecurityType.STOCK,
                                 "600000",
                                 SecurityExchange.SH,
                                 "浦发银行"),
                new DecoratedTurnPointCondition(new TurnPointCondition(CompareOperator.LE,
                                                                       new BigDecimal("11.00"),
                                                                       BinaryFactorType.PERCENT,
                                                                       new BigDecimal("1.00"),
                                                                       null,
                                                                       true),
                                                new BigDecimal("9.00"),
                                                new DelayConfirmInfo(DelayConfirmOption.ACCUMULATE, 3),
                                                new DeviationCtrlInfo(new BigDecimal("1")),
                                                0,
                                                0),
                LocalDateTime.parse("2018-03-15T15:00:00"),
                new OfferedPriceTradePlan(ExchangeType.BUY,
                                          EntrustStrategy.CURRENT_PRICE,
                                          new TradeNumberByAmount(new BigDecimal("10000.00"))),
                new TrackedIndexInfo(MarketSource.SZ, "399001", "深证成指"),
                new WeekTimeRange(new WeekRange(Week.TUE, Week.THU),
                                  new LocalTimeRange(LocalTime.parse("10:00:00"),
                                                     LocalTime.parse("14:00:00"))));

        assertEquals(turnPointOrder, expected);
        assertEquals(turnPointOrder.toString(), expected.toString());
    }
}
