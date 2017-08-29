package me.caosh.condition.domain.model.order.time;

import me.caosh.condition.domain.model.market.RealTimeMarket;
import me.caosh.condition.domain.model.market.SecurityInfo;
import me.caosh.condition.domain.model.order.AbstractConditionOrder;
import me.caosh.condition.domain.model.order.OnceOrders;
import me.caosh.condition.domain.model.order.TimeDriven;
import me.caosh.condition.domain.model.order.TradeCustomerIdentity;
import me.caosh.condition.domain.model.order.constant.OrderState;
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
    public TimeOrder(Long orderId, TradeCustomerIdentity customerIdentity, boolean deleted, SecurityInfo securityInfo,
                     SimpleTimeCondition condition, TradePlan tradePlan, OrderState orderState) {
        super(orderId, customerIdentity, deleted, securityInfo, NativeStrategyInfo.TIME, condition, tradePlan, orderState);
    }

    @Override
    public TradeSignal onSecondTick() {
        boolean satisfiedNow = ((SimpleTimeCondition) getCondition()).isSatisfiedNow();
        if (satisfiedNow) {
            return SignalFactory.getInstance().general();
        }
        return SignalFactory.getInstance().none();
    }

    @Override
    public EntrustCommand onTradeSignal(TradeSignal signal, RealTimeMarket realTimeMarket) {
        return OnceOrders.createEntrustCommand(this, realTimeMarket);
    }
}
