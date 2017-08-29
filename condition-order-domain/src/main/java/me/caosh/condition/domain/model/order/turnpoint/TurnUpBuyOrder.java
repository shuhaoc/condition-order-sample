package me.caosh.condition.domain.model.order.turnpoint;

import com.google.common.base.MoreObjects;
import me.caosh.condition.domain.model.market.SecurityInfo;
import me.caosh.condition.domain.model.order.*;
import me.caosh.condition.domain.model.order.constant.OrderState;
import me.caosh.condition.domain.model.order.plan.SingleDirectionTradePlan;
import me.caosh.condition.domain.model.strategy.NativeStrategyInfo;

/**
 * Created by caosh on 2017/8/19.
 */
public class TurnUpBuyOrder extends SimpleMarketConditionOrder implements RealTimeMarketDriven {

    private final TurnUpCondition turnUpCondition;

    public TurnUpBuyOrder(Long orderId, TradeCustomerIdentity customerIdentity, boolean deleted, SecurityInfo securityInfo,
                          TurnUpCondition turnUpCondition, SingleDirectionTradePlan tradePlan, OrderState orderState) {
        super(orderId, customerIdentity, deleted, securityInfo, NativeStrategyInfo.TURN_UP, turnUpCondition, tradePlan, orderState);
        this.turnUpCondition = turnUpCondition;
    }

    @Override
    public Condition getCondition() {
        return getMarketCondition();
    }

    @Override
    public MarketCondition getMarketCondition() {
        return turnUpCondition;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .addValue(super.toString())
                .add("turnUpCondition", turnUpCondition)
                .toString();
    }
}
