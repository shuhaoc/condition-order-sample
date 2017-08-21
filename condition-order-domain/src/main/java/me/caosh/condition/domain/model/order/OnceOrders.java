package me.caosh.condition.domain.model.order;

import me.caosh.condition.domain.model.market.RealTimeMarket;
import me.caosh.condition.domain.model.trade.EntrustCommand;
import me.caosh.condition.domain.model.trade.EntrustPriceSelector;
import me.caosh.condition.domain.model.trade.OrderType;

import java.math.BigDecimal;

/**
 * Created by caosh on 2017/8/20.
 */
public class OnceOrders {
    public static EntrustCommand createEntrustCommand(ConditionOrder conditionOrder, RealTimeMarket realTimeMarket) {
        BigDecimal entrustPrice = EntrustPriceSelector.selectPrice(realTimeMarket, conditionOrder.getTradePlan().getEntrustStrategy());
        return new EntrustCommand(conditionOrder.getCustomerIdentity(), conditionOrder.getSecurityInfo(),
                conditionOrder.getTradePlan().getExchangeType(), entrustPrice,
                conditionOrder.getTradePlan().getTradeNumber().getNumber(entrustPrice), OrderType.LIMITED);
    }

    private OnceOrders() {
    }
}
