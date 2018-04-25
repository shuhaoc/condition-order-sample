package me.caosh.condition.interfaces.assembler;

import hbec.commons.domain.intellitrade.condition.TimeReachedConditionDTO;
import hbec.commons.domain.intellitrade.conditionorder.TradePlanDTO;
import hbec.commons.domain.intellitrade.security.SecurityInfoDTO;
import hbec.intellitrade.common.security.SecurityExchange;
import hbec.intellitrade.common.security.SecurityInfo;
import hbec.intellitrade.common.security.SecurityType;
import hbec.intellitrade.conditionorder.domain.OrderState;
import hbec.intellitrade.conditionorder.domain.TradeCustomerInfo;
import hbec.intellitrade.conditionorder.domain.orders.time.TimeOrder;
import hbec.intellitrade.conditionorder.domain.orders.time.TimeReachedCondition;
import hbec.intellitrade.conditionorder.domain.tradeplan.EntrustStrategy;
import hbec.intellitrade.conditionorder.domain.tradeplan.OfferedPriceTradePlan;
import hbec.intellitrade.conditionorder.domain.tradeplan.TradeNumberByAmount;
import hbec.intellitrade.trade.domain.ExchangeType;
import me.caosh.condition.interfaces.command.TimeOrderCreateCommand;
import org.joda.time.LocalDateTime;
import org.testng.annotations.Test;

import java.math.BigDecimal;

import static org.testng.Assert.assertEquals;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/3/15
 */
public class TimeOrderCommandAssemblerTest {
    @Test
    public void test() throws Exception {
        TimeOrderCreateCommand createCommand = new TimeOrderCreateCommand();
        SecurityInfoDTO securityInfo = new SecurityInfoDTO();
        securityInfo.setType(4);
        securityInfo.setCode("600000");
        securityInfo.setName("浦发银行");
        securityInfo.setExchange("SH");
        createCommand.setSecurityInfo(securityInfo);

        TimeReachedConditionDTO condition = new TimeReachedConditionDTO();
        condition.setTargetTime("2018-04-29 10:00:00");

        createCommand.setCondition(condition);

        createCommand.setExpireTime(LocalDateTime.parse("2018-04-29T15:00:00").toDate());

        TradePlanDTO tradePlan = new TradePlanDTO();
        tradePlan.setExchangeType(1);
        tradePlan.setEntrustStrategy(1);
        tradePlan.setEntrustMethod(1);
        tradePlan.setEntrustAmount(new BigDecimal("10000.00"));
        createCommand.setTradePlan(tradePlan);

        TradeCustomerInfo tradeCustomerInfo = new TradeCustomerInfo(303348, "010000061086");
        TimeOrder timeOrder = TimeOrderCommandAssembler.assemble(123L, tradeCustomerInfo, createCommand);

        TimeOrder expected = new TimeOrder(123L,
                new TradeCustomerInfo(303348, "010000061086"),
                OrderState.ACTIVE,
                new SecurityInfo(SecurityType.STOCK, "600000", SecurityExchange.SH, "浦发银行"),
                new TimeReachedCondition(LocalDateTime.parse("2018-04-29T10:00:00")),
                LocalDateTime.parse("2018-04-29T15:00:00"),
                new OfferedPriceTradePlan(ExchangeType.BUY,
                        EntrustStrategy.CURRENT_PRICE,
                        new TradeNumberByAmount(new BigDecimal("10000.00"))));
        assertEquals(timeOrder, expected);
    }
}
