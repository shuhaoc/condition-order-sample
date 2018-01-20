package me.caosh.condition.domain.model.order.grid;

import me.caosh.condition.domain.model.constants.EntrustMethod;
import me.caosh.condition.domain.model.constants.SecurityExchange;
import me.caosh.condition.domain.model.constants.SecurityType;
import me.caosh.condition.domain.model.market.RealTimeMarket;
import me.caosh.condition.domain.model.market.SecurityInfo;
import me.caosh.condition.domain.model.order.TradeCustomer;
import me.caosh.condition.domain.model.order.TriggerContext;
import me.caosh.condition.domain.model.order.constant.EntrustStrategy;
import me.caosh.condition.domain.model.order.constant.ExchangeType;
import me.caosh.condition.domain.model.order.constant.OrderState;
import me.caosh.condition.domain.model.order.plan.DoubleDirectionTradePlan;
import me.caosh.condition.domain.model.order.plan.TradePlanFactory;
import me.caosh.condition.domain.model.signal.SignalFactory;
import me.caosh.condition.domain.model.signal.TradeSignal;
import me.caosh.condition.domain.model.trade.EntrustCommand;
import me.caosh.condition.domain.model.trade.EntrustResult;
import me.caosh.condition.domain.model.trade.OrderType;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Collections;

import static org.junit.Assert.assertEquals;

/**
 * Created by caosh on 2017/8/31.
 *
 * @author caoshuhao@touker.com
 */
public class GridOrderTest {

    @Test
    public void test() throws Exception {
        TradeCustomer customerIdentity = new TradeCustomer(303348, "010000061086");
        SecurityInfo pfyh = new SecurityInfo(SecurityType.STOCK, "600000", SecurityExchange.SH, "PFYH");
        GridCondition gridCondition = new GridCondition(new BigDecimal("1.00"), new BigDecimal("13.00"));
        ExchangeType exchangeType = ExchangeType.BUY;
        DoubleDirectionTradePlan tradePlan = TradePlanFactory.getInstance().createDouble(
                EntrustStrategy.CURRENT_PRICE.getValue(), EntrustMethod.AMOUNT.getValue(), 0, new BigDecimal("4500"));
        GridTradeOrder gridTradeOrder = new GridTradeOrder(123L, customerIdentity, pfyh, gridCondition,
                tradePlan, OrderState.ACTIVE);

        assertEquals(SignalFactory.getInstance().none(),
                gridTradeOrder.onRealTimeMarketUpdate(new RealTimeMarket(pfyh.asMarketID(), new BigDecimal("13.01"), Collections.emptyList())));

        RealTimeMarket realTimeMarket = new RealTimeMarket(pfyh.asMarketID(), new BigDecimal("14.00"), Collections.emptyList());
        TradeSignal tradeSignal = gridTradeOrder.onRealTimeMarketUpdate(realTimeMarket);
        assertEquals(SignalFactory.getInstance().sell(), tradeSignal);
        TriggerContext triggerContext = new TriggerContext(tradeSignal, gridTradeOrder, realTimeMarket);

        assertEquals(new EntrustCommand(customerIdentity, pfyh, ExchangeType.SELL, new BigDecimal("14.00"), 300, OrderType.LIMITED),
                gridTradeOrder.onTradeSignal(tradeSignal, realTimeMarket));

        gridTradeOrder.afterEntrustReturned(triggerContext, new EntrustResult(EntrustResult.SUCCESS, "OK", 456));
        assertEquals(OrderState.ACTIVE, gridTradeOrder.getOrderState());
        assertEquals(new BigDecimal("14.00"), gridTradeOrder.getGridCondition().getBasePrice());

        realTimeMarket = new RealTimeMarket(pfyh.asMarketID(), new BigDecimal("13.00"), Collections.emptyList());
        tradeSignal = gridTradeOrder.onRealTimeMarketUpdate(realTimeMarket);
        assertEquals(SignalFactory.getInstance().buy(), tradeSignal);
        triggerContext = new TriggerContext(tradeSignal, gridTradeOrder, realTimeMarket);

        assertEquals(new EntrustCommand(customerIdentity, pfyh, ExchangeType.BUY, new BigDecimal("13.00"), 300, OrderType.LIMITED),
                gridTradeOrder.onTradeSignal(tradeSignal, realTimeMarket));

        gridTradeOrder.afterEntrustReturned(triggerContext, new EntrustResult(EntrustResult.SUCCESS, "OK", 457));
        assertEquals(OrderState.ACTIVE, gridTradeOrder.getOrderState());
        assertEquals(new BigDecimal("13.00"), gridTradeOrder.getGridCondition().getBasePrice());
    }
}
