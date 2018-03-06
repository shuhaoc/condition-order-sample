package me.caosh.condition.domain.model.order.grid;

import hbec.intellitrade.common.market.RealTimeMarket;
import hbec.intellitrade.common.security.SecurityExchange;
import hbec.intellitrade.common.security.SecurityInfo;
import hbec.intellitrade.common.security.SecurityType;
import me.caosh.condition.domain.model.account.TradeCustomer;
import me.caosh.condition.domain.model.constants.EntrustMethod;
import me.caosh.condition.domain.model.order.TradeCustomerInfo;
import me.caosh.condition.domain.model.order.TriggerTradingContext;
import me.caosh.condition.domain.model.order.WrapperTradingMarketSupplier;
import me.caosh.condition.domain.model.order.constant.EntrustStrategy;
import me.caosh.condition.domain.model.order.constant.StrategyState;
import me.caosh.condition.domain.model.order.plan.DoubleDirectionTradePlan;
import me.caosh.condition.domain.model.order.plan.TradePlanFactory;
import me.caosh.condition.domain.model.signal.Signal;
import me.caosh.condition.domain.model.signal.Signals;
import me.caosh.condition.domain.model.signal.TradeSignal;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.Collections;

import static org.testng.Assert.assertEquals;

/**
 * Created by caosh on 2017/8/31.
 *
 * @author caoshuhao@touker.com
 */
public class GridOrderTest {

    @Test
    public void test() throws Exception {
        TradeCustomerInfo tradeCustomerInfo = new TradeCustomerInfo(303348, "010000061086");
        SecurityInfo pfyh = new SecurityInfo(SecurityType.STOCK, "600000", SecurityExchange.SH, "PFYH");
        GridCondition gridCondition = new GridCondition(new BigDecimal("1.00"), new BigDecimal("13.00"));
        DoubleDirectionTradePlan tradePlan = TradePlanFactory.getInstance().createDouble(
                pfyh, EntrustStrategy.CURRENT_PRICE.getValue(), EntrustMethod.AMOUNT.getValue(), 0, new BigDecimal("4500"));
        GridTradeOrder gridTradeOrder = new GridTradeOrder(123L, tradeCustomerInfo, pfyh, gridCondition,
                tradePlan, StrategyState.ACTIVE);

        assertEquals(Signals.none(),
                gridTradeOrder.getCondition().onMarketUpdate(new RealTimeMarket(pfyh.getMarketID(), new BigDecimal("13.01"),
                        Collections.<BigDecimal>emptyList())));

        RealTimeMarket realTimeMarket = new RealTimeMarket(pfyh.getMarketID(), new BigDecimal("14.00"),
                Collections.<BigDecimal>emptyList());
        Signal signal = gridTradeOrder.getCondition().onMarketUpdate(realTimeMarket);
        assertEquals(Signals.sell(), signal);
        TradeCustomer tradeCustomer = new TradeCustomer(303348, "010000061086");
        TriggerTradingContext triggerTradingContext = new TriggerTradingContext(signal, gridTradeOrder, tradeCustomer,
                null, realTimeMarket);

//        assertEquals(new EntrustCommand(pfyh, ExchangeType.SELL, new BigDecimal("14.00"), 300, OrderType.LIMITED),
//                gridTradeOrder.onTradeSignal2((TradeSignal) signal, realTimeMarket));
        gridTradeOrder.onTradeSignal((TradeSignal) signal, tradeCustomer, new WrapperTradingMarketSupplier(realTimeMarket));

//        gridTradeOrder.afterEntrustReturned(triggerTradingContext, new EntrustResult(EntrustResult.SUCCESS, "OK", 456));
        assertEquals(StrategyState.ACTIVE, gridTradeOrder.getStrategyState());
        assertEquals(new BigDecimal("14.00"), gridTradeOrder.getGridCondition().getBasePrice());

        realTimeMarket = new RealTimeMarket(pfyh.getMarketID(), new BigDecimal("13.00"),
                Collections.<BigDecimal>emptyList());
        signal = gridTradeOrder.getCondition().onMarketUpdate(realTimeMarket);
        assertEquals(Signals.buy(), signal);
        triggerTradingContext = new TriggerTradingContext(signal, gridTradeOrder, tradeCustomer,
                null, realTimeMarket);

//        assertEquals(new EntrustCommand(pfyh, ExchangeType.BUY, new BigDecimal("13.00"), 300, OrderType.LIMITED),
//                gridTradeOrder.onTradeSignal(signal, realTimeMarket));
        gridTradeOrder.onTradeSignal((TradeSignal) signal, tradeCustomer, new WrapperTradingMarketSupplier(realTimeMarket));

//        gridTradeOrder.afterEntrustReturned(triggerTradingContext, new EntrustResult(EntrustResult.SUCCESS, "OK", 457));
        assertEquals(StrategyState.ACTIVE, gridTradeOrder.getStrategyState());
        assertEquals(new BigDecimal("13.00"), gridTradeOrder.getGridCondition().getBasePrice());
    }
}
