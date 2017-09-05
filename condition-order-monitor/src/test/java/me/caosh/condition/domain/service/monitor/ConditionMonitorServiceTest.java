package me.caosh.condition.domain.service.monitor;

import me.caosh.condition.domain.model.constants.SecurityExchange;
import me.caosh.condition.domain.model.constants.SecurityType;
import me.caosh.condition.domain.model.market.RealTimeMarket;
import me.caosh.condition.domain.model.market.SecurityInfo;
import me.caosh.condition.domain.model.monitor.MonitorContext;
import me.caosh.condition.domain.model.order.TradeCustomerIdentity;
import me.caosh.condition.domain.model.order.TriggerMessage;
import me.caosh.condition.domain.model.order.constant.EntrustStrategy;
import me.caosh.condition.domain.model.order.constant.ExchangeType;
import me.caosh.condition.domain.model.order.constant.OrderState;
import me.caosh.condition.domain.model.order.plan.SingleDirectionTradePlan;
import me.caosh.condition.domain.model.order.plan.TradeNumberDirect;
import me.caosh.condition.domain.model.order.turnpoint.TurnUpBuyOrder;
import me.caosh.condition.domain.model.order.turnpoint.TurnUpCondition;
import me.caosh.condition.domain.model.signal.SignalFactory;
import me.caosh.condition.domain.service.monitor.impl.ConditionMonitorServiceImpl;
import me.caosh.condition.infrastructure.rabbitmq.TriggerMessageTriggerProducer;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Collections;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

/**
 * Created by caosh on 2017/9/4.
 */
public class ConditionMonitorServiceTest {
    @Mock
    private TriggerMessageTriggerProducer triggerMessageTriggerProducer;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    private SecurityInfo getSecurityInfo() {
        return new SecurityInfo(SecurityType.STOCK, "600000", SecurityExchange.SH, "PFYH");
    }

    private TurnUpBuyOrder newTurnUpBuyOrder() {
        TradeCustomerIdentity customerIdentity = new TradeCustomerIdentity(303348, "010000061086");
        SecurityInfo pfyh = getSecurityInfo();
        TurnUpCondition turnUpCondition = new TurnUpCondition(new BigDecimal("13.00"), new BigDecimal("1.00"));
        SingleDirectionTradePlan tradePlan = new SingleDirectionTradePlan(ExchangeType.BUY, EntrustStrategy.CURRENT_PRICE, new TradeNumberDirect(100));
        return new TurnUpBuyOrder(123L, customerIdentity, pfyh, turnUpCondition, tradePlan, OrderState.ACTIVE);
    }

    private RealTimeMarket newRealTimeMarket(BigDecimal currentPrice) {
        return new RealTimeMarket(getSecurityInfo().asMarketID(), currentPrice, Collections.<BigDecimal>emptyList());
    }

    @Test
    public void testMarket() throws Exception {
        TurnUpBuyOrder turnUpBuyOrder = newTurnUpBuyOrder();
        MonitorContext monitorContext = new MonitorContext(turnUpBuyOrder, 10, 1);

        assertTrue(monitorContext.isRealTimeMarketDriven());
        assertFalse(monitorContext.isTimeDriven());

        ConditionMonitorService conditionMonitorService = new ConditionMonitorServiceImpl(triggerMessageTriggerProducer);
        conditionMonitorService.checkWithRealTimeMarket(monitorContext, newRealTimeMarket(new BigDecimal("13.01")));

        assertFalse(turnUpBuyOrder.getTurnUpCondition().getBroken());
        assertFalse(turnUpBuyOrder.getTurnUpCondition().isDirty());
        verify(triggerMessageTriggerProducer, never()).send(any());

        conditionMonitorService.checkWithRealTimeMarket(monitorContext, newRealTimeMarket(new BigDecimal("13.00")));
        assertTrue(turnUpBuyOrder.getTurnUpCondition().getBroken());
        assertFalse(turnUpBuyOrder.getTurnUpCondition().isDirty());
        assertTrue(monitorContext.isDelaySyncMarked());
        assertFalse(monitorContext.isDelaySyncTimesUp());

        conditionMonitorService.checkWithTime(monitorContext);
        assertTrue(monitorContext.isDelaySyncMarked());
        assertFalse(monitorContext.isDelaySyncTimesUp());

        Thread.sleep(1001);

        conditionMonitorService.checkWithTime(monitorContext);
        assertFalse(monitorContext.isDelaySyncMarked());
        assertFalse(monitorContext.isDelaySyncTimesUp());
        TriggerMessage cacheSyncMessage = new TriggerMessage(SignalFactory.getInstance().cacheSync(), turnUpBuyOrder);
        verify(triggerMessageTriggerProducer, times(1)).send(eq(cacheSyncMessage));
        reset(triggerMessageTriggerProducer);

        conditionMonitorService.checkWithRealTimeMarket(monitorContext, newRealTimeMarket(new BigDecimal("12.00")));
        assertTrue(monitorContext.isDelaySyncMarked());
        assertFalse(monitorContext.isDelaySyncTimesUp());

        RealTimeMarket triggerMarket = newRealTimeMarket(new BigDecimal("12.12"));
        conditionMonitorService.checkWithRealTimeMarket(monitorContext, triggerMarket);
        assertTrue(monitorContext.isTriggerLocked());
        TriggerMessage triggerMessage = new TriggerMessage(SignalFactory.getInstance().general(), turnUpBuyOrder, triggerMarket);
        verify(triggerMessageTriggerProducer, times(1)).send(eq(triggerMessage));
        reset(triggerMessageTriggerProducer);

        conditionMonitorService.checkWithRealTimeMarket(monitorContext, newRealTimeMarket(new BigDecimal("12.12")));
        assertTrue(monitorContext.isTriggerLocked());
        verify(triggerMessageTriggerProducer, never()).send(any());
        reset(triggerMessageTriggerProducer);
    }
}
