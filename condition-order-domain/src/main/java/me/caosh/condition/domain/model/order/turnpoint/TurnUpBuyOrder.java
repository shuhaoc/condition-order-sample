package me.caosh.condition.domain.model.order.turnpoint;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import me.caosh.condition.domain.model.condition.TurnUpCondition;
import me.caosh.condition.domain.model.market.SecurityInfo;
import me.caosh.condition.domain.model.order.AbstractSimpleMarketConditionOrder;
import me.caosh.condition.domain.model.order.TradeCustomer;
import me.caosh.condition.domain.model.order.constant.ExchangeType;
import me.caosh.condition.domain.model.order.constant.StrategyState;
import me.caosh.condition.domain.model.order.plan.BasicTradePlan;
import me.caosh.condition.domain.model.strategy.condition.Condition;
import me.caosh.condition.domain.model.strategy.condition.market.MarketCondition;
import me.caosh.condition.domain.model.strategy.description.NativeStrategyInfo;

/**
 * Created by caosh on 2017/8/19.
 */
public class TurnUpBuyOrder extends AbstractSimpleMarketConditionOrder {

    private final TurnUpCondition turnUpCondition;

    public TurnUpBuyOrder(Long orderId, TradeCustomer tradeCustomer, SecurityInfo securityInfo,
                          TurnUpCondition turnUpCondition, BasicTradePlan tradePlan, StrategyState strategyState) {
        super(orderId, tradeCustomer, securityInfo, NativeStrategyInfo.TURN_UP, tradePlan, strategyState);
        Preconditions.checkArgument(tradePlan.getExchangeType() == ExchangeType.BUY);
        this.turnUpCondition = turnUpCondition;
    }

    public TurnUpCondition getTurnUpCondition() {
        return turnUpCondition;
    }

    @Override
    public MarketCondition getCondition() {
        return turnUpCondition;
    }

    @Override
    public Condition getRawCondition() {
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
