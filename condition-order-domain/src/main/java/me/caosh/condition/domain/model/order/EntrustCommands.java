package me.caosh.condition.domain.model.order;

import me.caosh.condition.domain.model.market.RealTimeMarket;
import me.caosh.condition.domain.model.market.SecurityInfo;
import me.caosh.condition.domain.model.order.plan.SingleDirectionTradePlan;
import me.caosh.condition.domain.model.trade.EntrustCommand;
import me.caosh.condition.domain.model.trade.EntrustPriceSelector;
import me.caosh.condition.domain.model.trade.OrderType;

import java.math.BigDecimal;

/**
 * Created by caosh on 2017/8/26.
 */
public class EntrustCommands {
    public static EntrustCommand createEntrustCommand(TradeCustomer customerIdentity, SecurityInfo securityInfo, SingleDirectionTradePlan singleDirectionTradePlan, RealTimeMarket realTimeMarket) {
        BigDecimal entrustPrice = EntrustPriceSelector.selectPrice(realTimeMarket, singleDirectionTradePlan.getEntrustStrategy());
        return new EntrustCommand(customerIdentity, securityInfo, singleDirectionTradePlan.getExchangeType(), entrustPrice,
                singleDirectionTradePlan.getTradeNumber().getNumber(entrustPrice), OrderType.LIMITED);
    }

    private EntrustCommands() {
    }
}
