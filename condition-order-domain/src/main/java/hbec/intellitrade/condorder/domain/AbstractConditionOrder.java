package hbec.intellitrade.condorder.domain;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import hbec.intellitrade.common.security.SecurityInfo;
import hbec.intellitrade.condorder.domain.tradeplan.SingleEntrustTradePlan;
import hbec.intellitrade.condorder.domain.trigger.TradingMarketSupplier;
import hbec.intellitrade.condorder.domain.trigger.TriggerTradingContext;
import hbec.intellitrade.strategy.domain.signal.TradeSignal;
import hbec.intellitrade.trade.domain.EntrustCommand;
import hbec.intellitrade.trade.domain.EntrustFailResult;
import hbec.intellitrade.trade.domain.EntrustOrderInfo;
import hbec.intellitrade.trade.domain.EntrustResult;
import hbec.intellitrade.trade.domain.EntrustSuccessResult;
import hbec.intellitrade.trade.domain.TradeCustomer;
import hbec.intellitrade.trade.domain.exception.InsufficientCapitalException;
import hbec.intellitrade.trade.domain.exception.InsufficientPositionException;
import hbec.intellitrade.trade.domain.exception.TradeException;
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
        EntrustResult entrustResult = null;
        try {
            EntrustSuccessResult entrustSuccessResult = tradeCustomer.entrust(entrustCommand);
            logger.info("Entrust result <== {}", entrustSuccessResult);
            afterEntrustSuccess(triggerTradingContext, entrustCommand, entrustSuccessResult);
            entrustResult = entrustSuccessResult;
        } catch (InsufficientCapitalException e) {
            logger.info("Insufficient capital result:  {}", e.getMessage());
            EntrustFailResult entrustFailResult = new EntrustFailResult(e.getMessage());
            onEntrustInsufficientCapitalException(triggerTradingContext, entrustCommand, entrustFailResult);
            entrustResult = entrustFailResult;
        } catch (InsufficientPositionException e) {
            logger.info("Insufficient position result: {}", e.getMessage());
            EntrustFailResult entrustFailResult = new EntrustFailResult(e.getMessage());
            onEntrustInsufficientPositionException(triggerTradingContext, entrustCommand, entrustFailResult);
            entrustResult = new EntrustFailResult(e.getMessage());
        } catch (TradeException e) {
            logger.error("Entrust failed, entrustCommand=" + entrustCommand, e);
            EntrustFailResult entrustFailResult = new EntrustFailResult(e.getMessage());
            afterEntrustFailed(triggerTradingContext, entrustCommand, entrustFailResult);
            entrustResult = entrustFailResult;
        } finally {
            if (entrustResult != null) {
                afterEntrustCommandExecuted(triggerTradingContext, entrustCommand, entrustResult);
            }
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
     * @param entrustCommand        委托指令
     * @param entrustSuccessResult  委托结果
     */
    protected void afterEntrustSuccess(TriggerTradingContext triggerTradingContext,
                                       EntrustCommand entrustCommand,
                                       EntrustSuccessResult entrustSuccessResult) {
    }

    /**
     * 委托出现资金不足事件
     *
     * @param triggerTradingContext 触发交易上下文
     * @param entrustCommand        委托指令
     * @param entrustFailResult     委托结果
     */
    protected void onEntrustInsufficientCapitalException(TriggerTradingContext triggerTradingContext,
                                                         EntrustCommand entrustCommand,
                                                         EntrustFailResult entrustFailResult) {
    }

    /**
     * 委托出现持仓不足事件
     *
     * @param triggerTradingContext 触发交易上下文
     * @param entrustCommand        委托指令
     * @param entrustFailResult     委托结果
     */
    protected void onEntrustInsufficientPositionException(TriggerTradingContext triggerTradingContext,
                                                          EntrustCommand entrustCommand,
                                                          EntrustFailResult entrustFailResult) {
    }

    /**
     * 委托失败事件
     *
     * @param triggerTradingContext 触发交易上下文
     * @param entrustCommand        委托指令
     * @param entrustFailResult     委托结果
     */
    protected void afterEntrustFailed(TriggerTradingContext triggerTradingContext,
                                      EntrustCommand entrustCommand,
                                      EntrustFailResult entrustFailResult) {
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
        triggerTradingContext.saveEntrustOrder(new EntrustOrderInfo(orderId, tradeCustomerInfo, entrustCommand, entrustResult));
    }

    /**
     * 所有委托完成事件
     *
     * @param triggerTradingContext 触发交易上下文
     */
    protected void afterEntrustCommandsExecuted(TriggerTradingContext triggerTradingContext) {
        this.orderState = OrderState.TERMINATED;
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
