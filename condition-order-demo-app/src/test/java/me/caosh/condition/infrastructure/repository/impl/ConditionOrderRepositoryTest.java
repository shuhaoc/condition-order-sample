package me.caosh.condition.infrastructure.repository.impl;

import com.google.common.base.Optional;
import hbec.intellitrade.common.market.MarketSource;
import hbec.intellitrade.common.security.SecurityExchange;
import hbec.intellitrade.common.security.SecurityInfo;
import hbec.intellitrade.common.security.SecurityType;
import hbec.intellitrade.conditionorder.domain.ConditionOrder;
import hbec.intellitrade.conditionorder.domain.ConditionOrderRepository;
import hbec.intellitrade.conditionorder.domain.OrderState;
import hbec.intellitrade.conditionorder.domain.TradeCustomerInfo;
import hbec.intellitrade.conditionorder.domain.orders.price.DecoratedPriceCondition;
import hbec.intellitrade.conditionorder.domain.orders.price.PriceCondition;
import hbec.intellitrade.conditionorder.domain.orders.price.PriceOrder;
import hbec.intellitrade.conditionorder.domain.trackindex.TrackedIndexInfo;
import hbec.intellitrade.conditionorder.domain.tradeplan.EntrustStrategy;
import hbec.intellitrade.conditionorder.domain.tradeplan.OfferedPriceTradePlan;
import hbec.intellitrade.conditionorder.domain.tradeplan.TradeNumberDirect;
import hbec.intellitrade.strategy.domain.condition.delayconfirm.DelayConfirmInfo;
import hbec.intellitrade.strategy.domain.condition.delayconfirm.DelayConfirmOption;
import hbec.intellitrade.strategy.domain.condition.deviation.DeviationCtrlInfo;
import hbec.intellitrade.strategy.domain.factor.CompareOperator;
import hbec.intellitrade.strategy.domain.shared.Week;
import hbec.intellitrade.strategy.domain.timerange.LocalTimeRange;
import hbec.intellitrade.strategy.domain.timerange.WeekRange;
import hbec.intellitrade.strategy.domain.timerange.WeekTimeRange;
import hbec.intellitrade.trade.domain.ExchangeType;
import me.caosh.condition.infrastructure.tunnel.impl.ConditionOrderIdGenerator;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
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
                                               OrderState.ACTIVE,
                                               securityInfo,
                                               new DecoratedPriceCondition(
                                                       new PriceCondition(CompareOperator.LE, new BigDecimal("13.00")),
                                                       new DelayConfirmInfo(DelayConfirmOption.CONTINUOUS, 3),
                                                       new DeviationCtrlInfo(new BigDecimal("1.00")),
                                                       0),
                                               LocalDateTime.parse("2018-03-09T15:00:00"),
                                               new OfferedPriceTradePlan(ExchangeType.BUY,
                                                                         EntrustStrategy.CURRENT_PRICE,
                                                                         new TradeNumberDirect(100)),
                                               new TrackedIndexInfo(MarketSource.SZ, "399001", "深证成指"),
                                               new WeekTimeRange(new WeekRange(Week.TUE, Week.THU),
                                                                 new LocalTimeRange(LocalTime.parse("10:00:00"),
                                                                                    LocalTime.parse("14:00:00"))));
        conditionOrderRepository.save(priceOrder);

        Optional<ConditionOrder> another = conditionOrderRepository.findOne(priceOrder.getOrderId());
        assertTrue(another.isPresent());
        assertEquals(another.get(), priceOrder);
        logger.info("{}", another.get());

        conditionOrderRepository.remove(priceOrder.getOrderId());
    }
}
