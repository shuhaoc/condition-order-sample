package me.caosh.condition.domain.model.order.time;

import com.google.common.base.MoreObjects;
import hbec.intellitrade.common.security.SecurityInfo;
import hbec.intellitrade.strategy.domain.signal.Signal;
import hbec.intellitrade.strategy.domain.signal.Signals;
import me.caosh.condition.domain.model.condition.TimeReachedCondition;
import me.caosh.condition.domain.model.order.AbstractConditionOrder;
import me.caosh.condition.domain.model.order.TradeCustomerInfo;
import me.caosh.condition.domain.model.order.constant.StrategyState;
import me.caosh.condition.domain.model.order.plan.BasicTradePlan;
import me.caosh.condition.domain.model.order.plan.TradePlan;
import hbec.intellitrade.strategy.domain.condition.Condition;
import me.caosh.condition.domain.model.strategyinfo.NativeStrategyInfo;
import me.caosh.condition.domain.model.strategyinfo.StrategyInfo;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by caosh on 2017/8/20.
 */
public class TimeOrder extends AbstractConditionOrder {
    private static final Logger logger = LoggerFactory.getLogger(TimeOrder.class);

    private final TimeReachedCondition timeReachedCondition;
    private final BasicTradePlan tradePlan;

    public TimeOrder(Long orderId, TradeCustomerInfo tradeCustomerInfo, SecurityInfo securityInfo,
                     TimeReachedCondition timeCondition, BasicTradePlan tradePlan, StrategyState strategyState) {
        super(orderId, tradeCustomerInfo, securityInfo, strategyState);
        this.timeReachedCondition = timeCondition;
        this.tradePlan = tradePlan;
    }

    @Override
    public Condition getCondition() {
        return timeReachedCondition;
    }

    @Override
    public Condition getRawCondition() {
        return timeReachedCondition;
    }

    @Override
    public Signal onTimeTick(LocalDateTime localDateTime) {
        if (isMonitoringState() && isExpiredAt(localDateTime)) {
            return Signals.expire();
        }

        if (getStrategyState() != StrategyState.ACTIVE) {
            return Signals.none();
        }

        return timeReachedCondition.onTimeTick(localDateTime);
    }

    @Override
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
