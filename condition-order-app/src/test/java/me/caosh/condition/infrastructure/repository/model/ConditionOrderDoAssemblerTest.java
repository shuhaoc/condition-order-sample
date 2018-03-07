package me.caosh.condition.infrastructure.repository.model;

import hbec.intellitrade.common.security.SecurityExchange;
import hbec.intellitrade.common.security.SecurityInfo;
import hbec.intellitrade.common.security.SecurityType;
import hbec.intellitrade.condorder.domain.ConditionOrder;
import hbec.intellitrade.condorder.domain.ConditionOrderBuilder;
import hbec.intellitrade.condorder.domain.OrderState;
import hbec.intellitrade.condorder.domain.TradeCustomerInfo;
import hbec.intellitrade.condorder.domain.orders.PriceOrder;
import hbec.intellitrade.condorder.domain.strategyinfo.NativeStrategyInfo;
import hbec.intellitrade.condorder.domain.tradeplan.BasicTradePlan;
import hbec.intellitrade.condorder.domain.tradeplan.EntrustStrategy;
import hbec.intellitrade.condorder.domain.tradeplan.TradeNumberByAmount;
import hbec.intellitrade.strategy.domain.factor.CompareOperator;
import hbec.intellitrade.strategy.domain.strategies.condition.PriceCondition;
import hbec.intellitrade.trade.domain.ExchangeType;
import me.caosh.autoasm.AutoAssemblers;
import hbec.intellitrade.condorder.domain.tradeplan.EntrustMethod;
import me.caosh.condition.infrastructure.tunnel.model.ConditionOrderDO;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * @author caoshuhao@touker.com
 * @date 2018/2/3
 */
public class ConditionOrderDoAssemblerTest {

    public static final long ORDER_ID = 123L;
    public static final int USER_ID = 34;
    public static final String CUSTOMER_NO = "012345";
    public static final String SECURITY_CODE = "600000";
    public static final String SECURITY_NAME = "浦发银行";

    @Test
    public void testPriceConditionOrder() throws Exception {
        SecurityInfo securityInfo = new SecurityInfo(SecurityType.STOCK, SECURITY_CODE, SecurityExchange.SH, SECURITY_NAME);
        ConditionOrder conditionOrder = new PriceOrder(
                ORDER_ID,
                new TradeCustomerInfo(USER_ID, CUSTOMER_NO),
                OrderState.ACTIVE, securityInfo,
                new PriceCondition(CompareOperator.GE, new BigDecimal("10.00")),
                null,
                new BasicTradePlan(ExchangeType.SELL, EntrustStrategy.BUY1,
                        new TradeNumberByAmount(new BigDecimal("10000.00")))
        );
        ConditionOrderDO conditionOrderDO = AutoAssemblers.getDefault().assemble(conditionOrder, ConditionOrderDO.class);
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
        assertEquals(conditionOrderDO.getConditionProperties(), "{\"type\":\"PriceConditionDO\",\"compareOperator\":1,\"targetPrice\":10.00}");
        assertNull(conditionOrderDO.getDynamicPropertiesObj());
        assertEquals(conditionOrderDO.getExchangeType(), ExchangeType.SELL.getValue());
        assertEquals(conditionOrderDO.getEntrustStrategy(), EntrustStrategy.BUY1.getValue());
        assertEquals(conditionOrderDO.getEntrustMethod(), EntrustMethod.AMOUNT.getValue());
        assertEquals(conditionOrderDO.getEntrustAmount(), new BigDecimal("10000.00"));
        assertNull(conditionOrderDO.getCreateTime());
        assertNull(conditionOrderDO.getUpdateTime());

        ConditionOrder conditionOrder1 = AutoAssemblers.getDefault().disassemble(conditionOrderDO, ConditionOrderBuilder.class)
                .build();
        assertEquals(conditionOrder1, conditionOrder);
    }
}
