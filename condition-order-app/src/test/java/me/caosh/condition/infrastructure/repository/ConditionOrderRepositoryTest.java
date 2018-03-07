package me.caosh.condition.infrastructure.repository;

import com.google.common.base.Optional;
import hbec.intellitrade.common.security.SecurityExchange;
import hbec.intellitrade.common.security.SecurityInfo;
import hbec.intellitrade.common.security.SecurityType;
import hbec.intellitrade.condorder.domain.ConditionOrder;
import hbec.intellitrade.condorder.domain.OrderState;
import hbec.intellitrade.condorder.domain.TradeCustomerInfo;
import hbec.intellitrade.condorder.domain.orders.PriceOrder;
import hbec.intellitrade.condorder.domain.tradeplan.BasicTradePlan;
import hbec.intellitrade.condorder.domain.tradeplan.EntrustStrategy;
import hbec.intellitrade.condorder.domain.tradeplan.TradeNumberDirect;
import hbec.intellitrade.strategy.domain.factor.CompareOperator;
import hbec.intellitrade.strategy.domain.strategies.condition.PriceCondition;
import hbec.intellitrade.trade.domain.ExchangeType;
import me.caosh.condition.infrastructure.repository.impl.ConditionOrderIdGenerator;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import java.math.BigDecimal;

import static junit.framework.TestCase.assertTrue;
import static org.testng.Assert.assertEquals;

/**
 * Created by caosh on 2017/8/6.
 */
@SpringBootTest
public class ConditionOrderRepositoryTest extends AbstractTestNGSpringContextTests {
    private static final Logger logger = LoggerFactory.getLogger(ConditionOrderRepositoryTest.class);

    @Autowired
    private ConditionOrderRepository conditionOrderRepository;
    @Autowired
    private ConditionOrderIdGenerator conditionOrderIdGenerator;

    @Test
    public void test() throws Exception {
        TradeCustomerInfo tradeCustomerInfo = new TradeCustomerInfo(303348, "010000061086");
        SecurityInfo securityInfo = new SecurityInfo(SecurityType.STOCK, "600000", SecurityExchange.SH, "PFYH");
        PriceOrder priceOrder = new PriceOrder(conditionOrderIdGenerator.nextId(), tradeCustomerInfo,
                securityInfo,
                new PriceCondition(CompareOperator.LE, new BigDecimal("13.00")),
                LocalDateTime.parse("2018-03-09T15:00:00"),
                new BasicTradePlan(ExchangeType.BUY, EntrustStrategy.CURRENT_PRICE, new TradeNumberDirect(100)),
                OrderState.ACTIVE);
        conditionOrderRepository.save(priceOrder);

        Optional<ConditionOrder> another = conditionOrderRepository.findOne(priceOrder.getOrderId());
        assertTrue(another.isPresent());
        assertEquals(another.get(), priceOrder);
        logger.info("{}", another.get());

        conditionOrderRepository.remove(priceOrder.getOrderId());
    }
}
