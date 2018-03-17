package hbec.intellitrade.condorder.domain;

import com.google.common.base.Optional;
import hbec.intellitrade.condorder.domain.trigger.TriggerTradingContext;
import hbec.intellitrade.trade.domain.EntrustCommand;
import hbec.intellitrade.trade.domain.EntrustOrderInfo;
import hbec.intellitrade.trade.domain.EntrustResult;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 条件单骨架，并实现最常见的单委托、单次条件单逻辑
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2017/8/2
 */
public abstract class AbstractConditionOrder implements ConditionOrder {
    private static final Logger logger = LoggerFactory.getLogger(AbstractConditionOrder.class);

    private final Long orderId;
    private final TradeCustomerInfo tradeCustomerInfo;
    private OrderState orderState;
    private final LocalDateTime expireTime;

    public AbstractConditionOrder(Long orderId,
                                  TradeCustomerInfo tradeCustomerInfo,
                                  OrderState orderState,
                                  LocalDateTime expireTime) {
        this.orderId = orderId;
        this.tradeCustomerInfo = tradeCustomerInfo;
        this.expireTime = expireTime;
        this.orderState = orderState;
    }

    @Override
    public long getOrderId() {
        return orderId;
    }

    @Override
    public long getStrategyId() {
        return getOrderId();
    }

    @Override
    public TradeCustomerInfo getCustomer() {
        return tradeCustomerInfo;
    }

    @Override
    public OrderState getOrderState() {
        return orderState;
    }

    protected void setOrderState(OrderState orderState) {
        this.orderState = orderState;
    }

    /**
     * 获取过期时间
     *
     * @return 过期时间
     */
    public Optional<LocalDateTime> getExpireTime() {
        return Optional.fromNullable(expireTime);
    }

    @Override
    public boolean isMonitoringState() {
        return orderState == OrderState.ACTIVE || orderState == OrderState.PAUSED;
    }

    protected boolean isExpiredAt(LocalDateTime localDateTime) {
        boolean expireTimeConfigured = expireTime != null;
        if (expireTimeConfigured) {
            return localDateTime.compareTo(expireTime) >= 0;
        }
        return false;
    }



    /**
     * 委托完成事件
     *
     * @param triggerTradingContext 触发交易上下文
     * @param entrustCommand        委托指令
     * @param entrustResult         委托结果
     */
    protected void afterEntrustCommandExecuted(TriggerTradingContext triggerTradingContext,
                                               EntrustCommand entrustCommand,
                                               EntrustResult entrustResult) {
        triggerTradingContext.saveEntrustOrder(new EntrustOrderInfo(orderId,
                                                                    tradeCustomerInfo,
                                                                    entrustCommand,
                                                                    entrustResult));
    }

    @Override
    public void onExpired() {
        this.orderState = OrderState.EXPIRED;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AbstractConditionOrder that = (AbstractConditionOrder) o;

        if (!orderId.equals(that.orderId)) {
            return false;
        }
        if (!tradeCustomerInfo.equals(that.tradeCustomerInfo)) {
            return false;
        }
        if (orderState != that.orderState) {
            return false;
        }
        return expireTime != null ? expireTime.equals(that.expireTime) : that.expireTime == null;
    }

    @Override
    public int hashCode() {
        int result = orderId.hashCode();
        result = 31 * result + tradeCustomerInfo.hashCode();
        result = 31 * result + orderState.hashCode();
        result = 31 * result + (expireTime != null ? expireTime.hashCode() : 0);
        return result;
    }
}
