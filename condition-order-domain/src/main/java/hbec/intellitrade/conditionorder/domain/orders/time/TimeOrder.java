package hbec.intellitrade.conditionorder.domain.orders.time;

import com.google.common.base.MoreObjects;
import hbec.intellitrade.common.security.SecurityInfo;
import hbec.intellitrade.conditionorder.domain.AbstractExplicitTradingSecurityOrder;
import hbec.intellitrade.conditionorder.domain.OrderState;
import hbec.intellitrade.conditionorder.domain.TradeCustomerInfo;
import hbec.intellitrade.conditionorder.domain.strategyinfo.NativeStrategyInfo;
import hbec.intellitrade.conditionorder.domain.strategyinfo.StrategyInfo;
import hbec.intellitrade.conditionorder.domain.tradeplan.BaseTradePlan;
import hbec.intellitrade.conditionorder.domain.tradeplan.TradePlan;
import hbec.intellitrade.conditionorder.domain.trigger.TriggerTradingContext;
import hbec.intellitrade.strategy.domain.TimeDrivenStrategy;
import hbec.intellitrade.strategy.domain.signal.Signal;
import hbec.intellitrade.strategy.domain.signal.Signals;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * 时间条件单
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2017/8/20
 */
public class TimeOrder extends AbstractExplicitTradingSecurityOrder implements TimeDrivenStrategy {
    private static final Logger logger = LoggerFactory.getLogger(TimeOrder.class);

    private final TimeReachedCondition timeReachedCondition;
    private final BaseTradePlan tradePlan;

    /**
     * 构造时间条件单
     *
     * @param orderId           条件单ID
     * @param tradeCustomerInfo 客户标识信息
     * @param orderState        条件单状态
     * @param securityInfo      交易证券信息
     * @param timeCondition     时间条件
     * @param expireTime        过期时间，空视为永久有效
     * @param tradePlan         交易计划
     */
    public TimeOrder(Long orderId,
            TradeCustomerInfo tradeCustomerInfo,
            OrderState orderState,
            SecurityInfo securityInfo,
            TimeReachedCondition timeCondition,
            LocalDateTime expireTime,
            BaseTradePlan tradePlan) {
        super(orderId, tradeCustomerInfo, orderState, securityInfo, expireTime);
        this.timeReachedCondition = timeCondition;
        this.tradePlan = tradePlan;
    }

    @Override
    public TimeReachedCondition getCondition() {
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

    @Override
    public StrategyInfo getStrategyInfo() {
        return NativeStrategyInfo.TIME;
    }

    @Override
    public TradePlan getTradePlan() {
        return tradePlan;
    }

    @Override
    protected void afterEntrustCommandsExecuted(TriggerTradingContext triggerTradingContext) {
        setOrderState(OrderState.TERMINATED);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }
        if (!super.equals(o)) { return false; }
        TimeOrder timeOrder = (TimeOrder) o;
        return Objects.equals(timeReachedCondition, timeOrder.timeReachedCondition) &&
                Objects.equals(tradePlan, timeOrder.tradePlan);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), timeReachedCondition, tradePlan);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(TimeOrder.class).omitNullValues()
                .add("orderId", getOrderId())
                .add("customer", getCustomer())
                .add("orderState", getOrderState())
                .add("securityInfo", getSecurityInfo())
                .add("expireTime", getExpireTime())
                .add("timeReachedCondition", timeReachedCondition)
                .add("tradePlan", tradePlan)
                .toString();
    }
}
