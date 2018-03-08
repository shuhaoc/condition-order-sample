package hbec.intellitrade.condorder.domain;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import hbec.intellitrade.common.security.SecurityInfo;
import hbec.intellitrade.condorder.domain.tradeplan.SingleEntrustTradePlan;
import hbec.intellitrade.condorder.domain.trigger.TradingMarketSupplier;
import hbec.intellitrade.condorder.domain.trigger.TriggerTradingContext;
import hbec.intellitrade.strategy.domain.signal.TradeSignal;
import hbec.intellitrade.trade.domain.EntrustCommand;
import hbec.intellitrade.trade.domain.EntrustOrderInfo;
import hbec.intellitrade.trade.domain.EntrustResult;
import hbec.intellitrade.trade.domain.TradeCustomer;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;

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
    private final SecurityInfo securityInfo;
    private final LocalDateTime expireTime;

    public AbstractConditionOrder(Long orderId, TradeCustomerInfo tradeCustomerInfo, SecurityInfo securityInfo,
                                  OrderState orderState) {
        this.orderId = orderId;
        this.tradeCustomerInfo = tradeCustomerInfo;
        this.securityInfo = securityInfo;
        this.expireTime = null;
        this.orderState = orderState;
    }

    public AbstractConditionOrder(Long orderId, TradeCustomerInfo tradeCustomerInfo, SecurityInfo securityInfo,
                                  LocalDateTime expireTime, OrderState orderState) {
        this.orderId = orderId;
        this.tradeCustomerInfo = tradeCustomerInfo;
        this.securityInfo = securityInfo;
        this.expireTime = expireTime;
        this.orderState = orderState;
    }

    @Override
    public Long getOrderId() {
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

    @Override
    public SecurityInfo getSecurityInfo() {
        return securityInfo;
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

    @Override
    public void onTradeSignal(TriggerTradingContext triggerTradingContext) {
        Preconditions.checkArgument(orderState == OrderState.ACTIVE, "Order state should be ACTIVE");

        List<EntrustCommand> entrustCommands = createEntrustCommands((TradeSignal) triggerTradingContext.getSignal(), triggerTradingContext);
        for (EntrustCommand entrustCommand : entrustCommands) {
            executeEntrustCommand(triggerTradingContext, entrustCommand);
        }
        afterEntrustCommandsExecuted(triggerTradingContext);
    }

    private void executeEntrustCommand(TriggerTradingContext triggerTradingContext, EntrustCommand entrustCommand) {
        TradeCustomer tradeCustomer = triggerTradingContext.getTradeCustomer();
        try {
            EntrustResult entrustResult = tradeCustomer.entrust(entrustCommand);
            logger.info("Entrust result <== {}", entrustResult);
            afterEntrustSuccess(triggerTradingContext, entrustCommand, entrustResult);
        } catch (Exception e) {
            logger.error("Entrust error, entrustCommand=" + entrustCommand, e);
        }
    }

    /**
     * 交易信号处理行为
     *
     * @param tradeSignal           交易信号
     * @param tradingMarketSupplier 交易标的实时行情supplier
     * @return 交易指令
     */
    protected List<EntrustCommand> createEntrustCommands(TradeSignal tradeSignal, TradingMarketSupplier tradingMarketSupplier) {
        SingleEntrustTradePlan singleEntrustTradePlan = (SingleEntrustTradePlan) getTradePlan();
        EntrustCommand entrustCommand = singleEntrustTradePlan.createEntrustCommand(tradeSignal, getSecurityInfo(),
                tradingMarketSupplier);
        return Collections.singletonList(entrustCommand);
    }

    /**
     * 委托成功事件
     *
     * @param triggerTradingContext 触发交易上下文
     * @param entrustCommand 委托指令
     * @param entrustResult 委托结果
     */
    protected void afterEntrustSuccess(TriggerTradingContext triggerTradingContext, EntrustCommand entrustCommand, EntrustResult entrustResult) {
        triggerTradingContext.saveEntrustOrder(new EntrustOrderInfo(orderId, tradeCustomerInfo, entrustCommand, entrustResult));
    }

    /**
     * 所有委托完成事件
     *
     * @param triggerTradingContext 触发交易上下文
     */
    protected void afterEntrustCommandsExecuted(TriggerTradingContext triggerTradingContext) {
        setOrderState(OrderState.TERMINATED);
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
        if (!securityInfo.equals(that.securityInfo)) {
            return false;
        }
        if (expireTime != null ? !expireTime.equals(that.expireTime) : that.expireTime != null) {
            return false;
        }
        return orderState == that.orderState;
    }

    @Override
    public int hashCode() {
        int result = orderId.hashCode();
        result = 31 * result + tradeCustomerInfo.hashCode();
        result = 31 * result + securityInfo.hashCode();
        result = 31 * result + (expireTime != null ? expireTime.hashCode() : 0);
        result = 31 * result + orderState.hashCode();
        return result;
    }
}
