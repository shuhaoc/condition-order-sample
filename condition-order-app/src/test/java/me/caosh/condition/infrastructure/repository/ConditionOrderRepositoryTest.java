package me.caosh.condition.infrastructure.repository;

import me.caosh.condition.domain.model.market.SecurityExchange;
import me.caosh.condition.domain.model.market.SecurityInfo;
import me.caosh.condition.domain.model.market.SecurityType;
import me.caosh.condition.domain.model.order.CompareCondition;
import me.caosh.condition.domain.model.order.ConditionOrder;
import me.caosh.condition.domain.model.order.EntrustStrategy;
import me.caosh.condition.domain.model.order.ExchangeType;
import me.caosh.condition.domain.model.order.OrderState;
import me.caosh.condition.domain.model.order.TradeCustomerIdentity;
import me.caosh.condition.domain.model.order.price.PriceOrder;
import me.caosh.condition.domain.model.order.price.PriceCondition;
import me.caosh.condition.domain.model.order.TradePlan;
import me.caosh.condition.infrastructure.repository.impl.ConditionOrderIdGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Optional;

import static junit.framework.TestCase.assertTrue;

/**
 * Created by caosh on 2017/8/6.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class ConditionOrderRepositoryTest {
    private static final Logger logger = LoggerFactory.getLogger(ConditionOrderRepositoryTest.class);

    @Autowired
    private ConditionOrderRepository conditionOrderRepository;
    @Autowired
    private ConditionOrderIdGenerator conditionOrderIdGenerator;

    @Test
    public void test() throws Exception {
        TradeCustomerIdentity customerIdentity = new TradeCustomerIdentity(303348, "010000061086");
        PriceOrder priceOrder = new PriceOrder(conditionOrderIdGenerator.nextId(), customerIdentity, false, OrderState.ACTIVE,
                new SecurityInfo(SecurityType.STOCK, "600000", SecurityExchange.SH, "PFYH"),
                new PriceCondition(CompareCondition.LESS_THAN_OR_EQUALS, new BigDecimal("13.00")),
                new TradePlan(ExchangeType.BUY, EntrustStrategy.CURRENT_PRICE, 100)
        );
        conditionOrderRepository.save(priceOrder);

        Optional<ConditionOrder> another = conditionOrderRepository.findOne(priceOrder.getOrderId());
        assertTrue(another.isPresent());
        logger.info("{}", another.get());
    }
}
