package me.caosh.condition.infrastructure.repository;

import com.google.common.base.Optional;
import me.caosh.condition.domain.model.condition.PriceCondition;
import me.caosh.condition.domain.model.constants.SecurityExchange;
import me.caosh.condition.domain.model.constants.SecurityType;
import me.caosh.condition.domain.model.market.SecurityInfo;
import me.caosh.condition.domain.model.order.ConditionOrder;
import me.caosh.condition.domain.model.order.TradeCustomer;
import me.caosh.condition.domain.model.order.constant.EntrustStrategy;
import me.caosh.condition.domain.model.order.constant.ExchangeType;
import me.caosh.condition.domain.model.order.constant.StrategyState;
import me.caosh.condition.domain.model.order.plan.BasicTradePlan;
import me.caosh.condition.domain.model.order.plan.TradeNumberDirect;
import me.caosh.condition.domain.model.order.price.PriceOrder;
import me.caosh.condition.domain.model.strategy.factor.CompareOperator;
import me.caosh.condition.infrastructure.repository.impl.ConditionOrderIdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

import static junit.framework.TestCase.assertTrue;

/**
 * Created by caosh on 2017/8/6.
 */
//@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class ConditionOrderRepositoryTest {
    private static final Logger logger = LoggerFactory.getLogger(ConditionOrderRepositoryTest.class);

    @Autowired
    private ConditionOrderRepository conditionOrderRepository;
    @Autowired
    private ConditionOrderIdGenerator conditionOrderIdGenerator;

    //    @Test
    public void test() throws Exception {
        TradeCustomer tradeCustomer = new TradeCustomer(303348, "010000061086");
        SecurityInfo securityInfo = new SecurityInfo(SecurityType.STOCK, "600000", SecurityExchange.SH, "PFYH");
        PriceOrder priceOrder = new PriceOrder(conditionOrderIdGenerator.nextId(), tradeCustomer,
                securityInfo,
                new PriceCondition(CompareOperator.LE, new BigDecimal("13.00")),
                new BasicTradePlan(ExchangeType.BUY, EntrustStrategy.CURRENT_PRICE, new TradeNumberDirect(100)),
                StrategyState.ACTIVE);
        conditionOrderRepository.save(priceOrder);

        Optional<ConditionOrder> another = conditionOrderRepository.findOne(priceOrder.getOrderId());
        assertTrue(another.isPresent());
        logger.info("{}", another.get());
    }
}
