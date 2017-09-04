package me.caosh.condition.domain.model.order.turnpoint;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import me.caosh.condition.domain.model.market.SecurityInfo;
import me.caosh.condition.domain.model.order.Condition;
import me.caosh.condition.domain.model.order.SimpleMarketCondition;
import me.caosh.condition.domain.model.order.SimpleMarketConditionOrder;
import me.caosh.condition.domain.model.order.TradeCustomerIdentity;
import me.caosh.condition.domain.model.order.constant.ExchangeType;
import me.caosh.condition.domain.model.order.constant.OrderState;
import me.caosh.condition.domain.model.order.plan.SingleDirectionTradePlan;
import me.caosh.condition.domain.model.strategy.NativeStrategyInfo;

/**
 * Created by caosh on 2017/8/19.
 */
public class TurnUpBuyOrder extends SimpleMarketConditionOrder {

    private final TurnUpCondition turnUpCondition;

    public TurnUpBuyOrder(Long orderId, TradeCustomerIdentity customerIdentity, boolean deleted, SecurityInfo securityInfo,
                          TurnUpCondition turnUpCondition, SingleDirectionTradePlan tradePlan, OrderState orderState) {
        super(orderId, customerIdentity, deleted, securityInfo, NativeStrategyInfo.TURN_UP, tradePlan, orderState);
        Preconditions.checkArgument(tradePlan.getExchangeType() == ExchangeType.BUY);
        this.turnUpCondition = turnUpCondition;
    }

    public TurnUpCondition getTurnUpCondition() {
        return turnUpCondition;
    }

    @Override
    public Condition getCondition() {
        return getMarketCondition();
    }

    @Override
    public SimpleMarketCondition getMarketCondition() {
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
