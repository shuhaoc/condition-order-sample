package me.caosh.condition.domain.model.order.turnpoint;

import com.google.common.base.MoreObjects;
import me.caosh.condition.domain.model.market.SecurityInfo;
import me.caosh.condition.domain.model.order.OnceMarketConditionOrder;
import me.caosh.condition.domain.model.order.RealTimeMarketDriven;
import me.caosh.condition.domain.model.order.TradeCustomerIdentity;
import me.caosh.condition.domain.model.order.constant.OrderState;
import me.caosh.condition.domain.model.order.plan.TradePlan;
import me.caosh.condition.domain.model.strategy.NativeStrategyInfo;

/**
 * Created by caosh on 2017/8/19.
 */
public class TurnUpBuyOrder extends OnceMarketConditionOrder implements RealTimeMarketDriven {

    public TurnUpBuyOrder(Long orderId, TradeCustomerIdentity customerIdentity, boolean deleted, OrderState orderState, SecurityInfo securityInfo,
                          TurnUpCondition condition, TradePlan tradePlan) {
        super(orderId, customerIdentity, deleted, securityInfo, NativeStrategyInfo.TURN_UP, condition, tradePlan, orderState);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .addValue(super.toString())
                .toString();
    }
}
