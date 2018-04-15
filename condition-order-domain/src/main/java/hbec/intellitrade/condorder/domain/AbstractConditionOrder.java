package hbec.intellitrade.condorder.domain;

import com.google.common.base.Optional;
import hbec.intellitrade.condorder.domain.trigger.TriggerTradingContext;
import hbec.intellitrade.trade.domain.*;
import hbec.intellitrade.trade.domain.exception.InsufficientCapitalException;
import hbec.intellitrade.trade.domain.exception.InsufficientPositionException;
import hbec.intellitrade.trade.domain.exception.TradeException;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 条件单骨架
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2017/8/2
 */
public abstract class AbstractConditionOrder implements ConditionOrder {
    private static final Logger logger = LoggerFactory.getLogger(AbstractConditionOrder.class);

    private final Long orderId;
    private final TradeCustomerInfo tradeCustomerInfo;
    private OrderState orderState;
    /**
     * 过期时间，空视为永久有效
     */
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

    protected void executeEntrustCommand(TriggerTradingContext triggerTradingContext, EntrustCommand entrustCommand) {
        TradeCustomer tradeCustomer = triggerTradingContext.getTradeCustomer();
        logger.info("Executing entrust command ==> {}", entrustCommand);
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
     * 委托失败事件（除缺钱缺股以外）
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
