package me.caosh.condition.domain.dto.order;

import hbec.intellitrade.common.market.index.IndexSource;
import hbec.intellitrade.common.security.SecurityExchange;
import hbec.intellitrade.common.security.SecurityInfo;
import hbec.intellitrade.common.security.SecurityType;
import hbec.intellitrade.condorder.domain.OrderState;
import hbec.intellitrade.condorder.domain.TradeCustomerInfo;
import hbec.intellitrade.condorder.domain.orders.ConditionOrderBuilder;
import hbec.intellitrade.condorder.domain.orders.price.PriceCondition;
import hbec.intellitrade.condorder.domain.orders.price.PriceConditionFacade;
import hbec.intellitrade.condorder.domain.orders.price.PriceOrder;
import hbec.intellitrade.condorder.domain.trackindex.TrackedIndexInfo;
import hbec.intellitrade.condorder.domain.tradeplan.BasicTradePlan;
import hbec.intellitrade.condorder.domain.tradeplan.EntrustStrategy;
import hbec.intellitrade.condorder.domain.tradeplan.TradeNumberDirect;
import hbec.intellitrade.strategy.domain.condition.Condition;
import hbec.intellitrade.strategy.domain.condition.delayconfirm.DelayConfirmInfo;
import hbec.intellitrade.strategy.domain.condition.delayconfirm.DelayConfirmOption;
import hbec.intellitrade.strategy.domain.condition.deviation.DeviationCtrlInfo;
import hbec.intellitrade.strategy.domain.factor.CompareOperator;
import hbec.intellitrade.strategy.domain.shared.Week;
import hbec.intellitrade.strategy.domain.timerange.LocalTimeRange;
import hbec.intellitrade.strategy.domain.timerange.WeekRange;
import hbec.intellitrade.strategy.domain.timerange.WeekTimeRange;
import hbec.intellitrade.trade.domain.ExchangeType;
import me.caosh.autoasm.AutoAssemblers;
import me.caosh.autoasm.ConvertibleBuilder;
import me.caosh.autoasm.MappedClass;
import me.caosh.condition.domain.dto.market.SecurityInfoDTO;
import me.caosh.condition.domain.dto.market.TrackedIndexDTO;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.springframework.beans.BeanUtils;
import org.testng.annotations.Test;

import java.math.BigDecimal;

import static org.testng.Assert.assertEquals;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/3/15
 */
public class ConditionOrderDtoAssemblerTest {
    @Test
    public void testPriceOrder() throws Exception {
        PriceOrder priceOrder = new PriceOrder(123L,
                                               new TradeCustomerInfo(303348, "010000061086"),
                                               OrderState.ACTIVE,
                                               new SecurityInfo(SecurityType.STOCK,
                                                                "600000",
                                                                SecurityExchange.SH,
                                                                "浦发银行"),
                                               new PriceConditionFacade(
                                                       new PriceCondition(CompareOperator.GE,
                                                                          new BigDecimal("9999.00")),
                                                       new DelayConfirmInfo(DelayConfirmOption.ACCUMULATE, 3),
                                                       new DeviationCtrlInfo(new BigDecimal("1.00")), 2
                                               ),
                                               LocalDateTime.parse("2018-03-15T15:00:00"),
                                               new BasicTradePlan(ExchangeType.BUY,
                                                                  EntrustStrategy.CURRENT_PRICE,
                                                                  new TradeNumberDirect(1000)),
                                               new TrackedIndexInfo(IndexSource.SZ, "399001", "深证成指"),
                                               new WeekTimeRange(new WeekRange(Week.TUE, Week.THU),
                                                                 new LocalTimeRange(LocalTime.parse("10:00:00"),
                                                                                    LocalTime.parse("14:00:00")))
        );

        ConditionOrderDTO assemble = AutoAssemblers.getDefault().assemble(priceOrder, ConditionOrderDTO.class);

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
        tradePlan.setNumber(BigDecimal.valueOf(1000));
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

        Class<? extends ConvertibleBuilder> conditionBuilderClass = conditionOrderDTO.getCondition()
                                                                                     .getClass()
                                                                                     .getAnnotation(MappedClass.class)
                                                                                     .builderClass();


        ConvertibleBuilder<? extends Condition> conditionBuilder = BeanUtils.instantiate(conditionBuilderClass);
        ConditionOrderBuilder conditionOrderBuilder = new ConditionOrderBuilder().setCondition(conditionBuilder);
        PriceOrder disassemble = (PriceOrder) AutoAssemblers.getDefault()
                                                            .disassemble(conditionOrderDTO, conditionOrderBuilder)
                                                            .build();
        assertEquals(disassemble, priceOrder);
    }
}
