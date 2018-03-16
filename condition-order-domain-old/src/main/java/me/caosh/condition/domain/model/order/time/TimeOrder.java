package me.caosh.condition.domain.model.order.time;

import com.google.common.base.MoreObjects;
import hbec.intellitrade.common.security.SecurityInfo;
import hbec.intellitrade.condorder.domain.AbstractConditionOrder;
import hbec.intellitrade.condorder.domain.OrderState;
import hbec.intellitrade.condorder.domain.TradeCustomerInfo;
import hbec.intellitrade.condorder.domain.strategyinfo.NativeStrategyInfo;
import hbec.intellitrade.condorder.domain.strategyinfo.StrategyInfo;
import hbec.intellitrade.condorder.domain.tradeplan.BasicTradePlan;
import hbec.intellitrade.condorder.domain.tradeplan.TradePlan;
import hbec.intellitrade.strategy.domain.TimeDrivenStrategy;
import hbec.intellitrade.strategy.domain.condition.Condition;
import hbec.intellitrade.strategy.domain.signal.Signal;
import hbec.intellitrade.strategy.domain.signal.Signals;
import me.caosh.condition.domain.model.condition.TimeReachedCondition;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by caosh on 2017/8/20.
 */
public class TimeOrder extends AbstractConditionOrder implements TimeDrivenStrategy {
    private static final Logger logger = LoggerFactory.getLogger(TimeOrder.class);

    private final TimeReachedCondition timeReachedCondition;
    private final BasicTradePlan tradePlan;

    public TimeOrder(Long orderId, TradeCustomerInfo tradeCustomerInfo, SecurityInfo securityInfo,
                     TimeReachedCondition timeCondition, LocalDateTime expireTime,
                     BasicTradePlan tradePlan, OrderState orderState) {
        super(orderId, tradeCustomerInfo, securityInfo, expireTime, orderState);
        this.timeReachedCondition = timeCondition;
        this.tradePlan = tradePlan;
    }

    public Condition getCondition() {
        return timeReachedCondition;
    }

    public Condition getRawCondition() {
        return timeReachedCondition;
    }

    @Override
    public Signal onTimeTick(LocalDateTime localDateTime) {
        if (isMonitoringState() && isExpiredAt(localDateTime)) {
            return Signals.expire();
        }

        if (getOrderState() != OrderState.ACTIVE) {
            return Signals.none();
        }

        return timeReachedCondition.onTimeTick(localDateTime);
    }

    public StrategyInfo getStrategyInfo() {
        return NativeStrategyInfo.TURN_UP;
    }

    @Override
    public TradePlan getTradePlan() {
        return tradePlan;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .addValue(super.toString())
                .add("timeReachedCondition", timeReachedCondition)
                .add("tradePlan", tradePlan)
                .toString();
    }
}
