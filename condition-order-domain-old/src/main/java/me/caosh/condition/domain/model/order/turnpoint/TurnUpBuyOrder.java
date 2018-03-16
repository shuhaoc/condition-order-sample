package me.caosh.condition.domain.model.order.turnpoint;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import hbec.intellitrade.common.security.SecurityInfo;
import hbec.intellitrade.condorder.domain.AbstractSimpleMarketConditionOrder;
import hbec.intellitrade.condorder.domain.OrderState;
import hbec.intellitrade.condorder.domain.TradeCustomerInfo;
import hbec.intellitrade.condorder.domain.strategyinfo.NativeStrategyInfo;
import hbec.intellitrade.condorder.domain.strategyinfo.StrategyInfo;
import hbec.intellitrade.condorder.domain.tradeplan.BasicTradePlan;
import hbec.intellitrade.strategy.domain.condition.Condition;
import hbec.intellitrade.strategy.domain.condition.market.MarketCondition;
import hbec.intellitrade.trade.domain.ExchangeType;
import me.caosh.condition.domain.model.condition.TurnUpCondition;
import org.joda.time.LocalDateTime;

/**
 * Created by caosh on 2017/8/19.
 */
public class TurnUpBuyOrder extends AbstractSimpleMarketConditionOrder {

    private final TurnUpCondition turnUpCondition;

    public TurnUpBuyOrder(Long orderId, TradeCustomerInfo tradeCustomerInfo, SecurityInfo securityInfo,
                          TurnUpCondition turnUpCondition, LocalDateTime expireTime, BasicTradePlan tradePlan, OrderState orderState) {
        super(orderId, tradeCustomerInfo, orderState, securityInfo, expireTime, tradePlan);
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

    public Condition getRawCondition() {
        return turnUpCondition;
    }

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
