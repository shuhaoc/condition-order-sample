package me.caosh.condition.domain.model.order.price;

import me.caosh.condition.domain.model.constants.SecurityExchange;
import me.caosh.condition.domain.model.constants.SecurityType;
import me.caosh.condition.domain.model.market.RealTimeMarket;
import me.caosh.condition.domain.model.market.SecurityInfo;
import me.caosh.condition.domain.model.order.TradeCustomerIdentity;
import me.caosh.condition.domain.model.order.constant.CompareCondition;
import me.caosh.condition.domain.model.order.constant.EntrustStrategy;
import me.caosh.condition.domain.model.order.constant.ExchangeType;
import me.caosh.condition.domain.model.order.constant.OrderState;
import me.caosh.condition.domain.model.order.plan.SingleDirectionTradePlan;
import me.caosh.condition.domain.model.order.plan.TradeNumberDirect;
import me.caosh.condition.domain.model.signal.SignalFactory;
import me.caosh.condition.domain.model.signal.TradeSignal;
import me.caosh.condition.domain.model.trade.EntrustCommand;
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
public class PriceOrderTest {

    @Test
    public void test() throws Exception {
        TradeCustomerIdentity customerIdentity = new TradeCustomerIdentity(303348, "010000061086");
        SecurityInfo pfyh = new SecurityInfo(SecurityType.STOCK, "600000", SecurityExchange.SH, "PFYH");
        ExchangeType exchangeType = ExchangeType.BUY;
        PriceOrder priceOrder = new PriceOrder(123L, customerIdentity, pfyh,
                new PriceCondition(CompareCondition.LESS_THAN_OR_EQUALS, new BigDecimal("13.00")),
                new SingleDirectionTradePlan(exchangeType, EntrustStrategy.CURRENT_PRICE, new TradeNumberDirect(100)),
                OrderState.ACTIVE);

        assertEquals(SignalFactory.getInstance().none(),
                priceOrder.onRealTimeMarketUpdate(new RealTimeMarket(pfyh.asMarketID(), new BigDecimal("13.01"), Collections.emptyList())));

        RealTimeMarket realTimeMarket = new RealTimeMarket(pfyh.asMarketID(), new BigDecimal("13.00"), Collections.emptyList());
        TradeSignal tradeSignal = priceOrder.onRealTimeMarketUpdate(realTimeMarket);
        assertEquals(SignalFactory.getInstance().general(), tradeSignal);
//        TriggerContext triggerContext = new TriggerContext(tradeSignal, priceOrder, realTimeMarket);

        assertEquals(new EntrustCommand(customerIdentity, pfyh, exchangeType, new BigDecimal("13.00"), 100, OrderType.LIMITED),
                priceOrder.onTradeSignal(tradeSignal, realTimeMarket));

//        priceOrder.afterEntrustReturned(triggerContext, new EntrustResult(EntrustResult.SUCCESS, "OK", 456));
//        assertEquals(OrderState.TERMINATED, priceOrder.getOrderState());
    }
}
