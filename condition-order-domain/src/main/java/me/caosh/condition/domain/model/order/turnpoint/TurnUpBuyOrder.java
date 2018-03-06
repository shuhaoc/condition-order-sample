package me.caosh.condition.domain.model.order.turnpoint;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import hbec.intellitrade.common.security.SecurityInfo;
import me.caosh.condition.domain.model.condition.TurnUpCondition;
import me.caosh.condition.domain.model.order.AbstractSimpleMarketConditionOrder;
import me.caosh.condition.domain.model.order.TradeCustomerInfo;
import me.caosh.condition.domain.model.order.constant.ExchangeType;
import me.caosh.condition.domain.model.order.constant.StrategyState;
import me.caosh.condition.domain.model.order.plan.BasicTradePlan;
import hbec.intellitrade.strategy.domain.condition.Condition;
import hbec.intellitrade.strategy.domain.condition.market.MarketCondition;
import me.caosh.condition.domain.model.strategyinfo.NativeStrategyInfo;
import me.caosh.condition.domain.model.strategyinfo.StrategyInfo;

/**
 * Created by caosh on 2017/8/19.
 */
public class TurnUpBuyOrder extends AbstractSimpleMarketConditionOrder {

    private final TurnUpCondition turnUpCondition;

    public TurnUpBuyOrder(Long orderId, TradeCustomerInfo tradeCustomerInfo, SecurityInfo securityInfo,
                          TurnUpCondition turnUpCondition, BasicTradePlan tradePlan, StrategyState strategyState) {
        super(orderId, tradeCustomerInfo, securityInfo, tradePlan, strategyState);
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
    public StrategyInfo getStrategyInfo() {
        return NativeStrategyInfo.TURN_UP;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .addValue(super.toString())
                .add("turnUpCondition", turnUpCondition)
                .toString();
    }
}
