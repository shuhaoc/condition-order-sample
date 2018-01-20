package me.caosh.condition.domain.model.order;

import me.caosh.condition.domain.model.market.RealTimeMarket;
import me.caosh.condition.domain.model.order.plan.SingleDirectionTradePlan;
import me.caosh.condition.domain.model.trade.EntrustCommand;

/**
 * Created by caosh on 2017/8/20.
 */
public class OnceOrders {
    public static EntrustCommand createEntrustCommand(ConditionOrder conditionOrder, RealTimeMarket realTimeMarket) {
        SingleDirectionTradePlan singleDirectionTradePlan = (SingleDirectionTradePlan) conditionOrder.getTradePlan();
        return EntrustCommands.createEntrustCommand(conditionOrder.getCustomer(),
                conditionOrder.getSecurityInfo(),
                singleDirectionTradePlan,
                realTimeMarket);
    }

    private OnceOrders() {
    }
}
