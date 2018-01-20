package me.caosh.condition.domain.model.order.time;

import com.google.common.base.MoreObjects;
import me.caosh.condition.domain.model.market.RealTimeMarket;
import me.caosh.condition.domain.model.market.SecurityInfo;
import me.caosh.condition.domain.model.order.AbstractConditionOrder;
import me.caosh.condition.domain.model.order.Condition;
import me.caosh.condition.domain.model.order.OnceOrders;
import me.caosh.condition.domain.model.order.TimeCondition;
import me.caosh.condition.domain.model.order.TimeDriven;
import me.caosh.condition.domain.model.order.TradeCustomer;
import me.caosh.condition.domain.model.order.constant.OrderState;
import me.caosh.condition.domain.model.order.plan.SingleDirectionTradePlan;
import me.caosh.condition.domain.model.order.plan.TradePlan;
import me.caosh.condition.domain.model.signal.SignalFactory;
import me.caosh.condition.domain.model.signal.TradeSignal;
import me.caosh.condition.domain.model.strategy.NativeStrategyInfo;
import me.caosh.condition.domain.model.trade.EntrustCommand;
import me.caosh.condition.domain.model.trade.SingleEntrustOnTrigger;

/**
 * Created by caosh on 2017/8/20.
 */
public class TimeOrder extends AbstractConditionOrder implements TimeDriven, SingleEntrustOnTrigger {

    private final TimeCondition timeCondition;
    private final SingleDirectionTradePlan tradePlan;

    public TimeOrder(Long orderId, TradeCustomer customerIdentity, SecurityInfo securityInfo,
                     SimpleTimeCondition timeCondition, SingleDirectionTradePlan tradePlan, OrderState orderState) {
        super(orderId, customerIdentity, securityInfo, NativeStrategyInfo.TIME, orderState);
        this.timeCondition = timeCondition;
        this.tradePlan = tradePlan;
    }

    @Override
    public Condition getCondition() {
        return timeCondition;
    }

    @Override
    public TradePlan getTradePlan() {
        return tradePlan;
    }

    @Override
    public TradeSignal onSecondTick() {
        boolean satisfiedNow = timeCondition.isSatisfiedNow();
        if (satisfiedNow) {
            return SignalFactory.getInstance().general();
        }
        return SignalFactory.getInstance().none();
    }

    @Override
    public EntrustCommand onTradeSignal(TradeSignal signal, RealTimeMarket realTimeMarket) {
        return OnceOrders.createEntrustCommand(this, realTimeMarket);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .addValue(super.toString())
                .add("timeCondition", timeCondition)
                .add("tradePlan", tradePlan)
                .toString();
    }
}
