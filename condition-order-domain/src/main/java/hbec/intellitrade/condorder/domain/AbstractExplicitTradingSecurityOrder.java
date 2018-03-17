package hbec.intellitrade.condorder.domain;

import com.google.common.base.Preconditions;
import hbec.intellitrade.common.security.SecurityInfo;
import hbec.intellitrade.condorder.domain.tradeplan.SingleEntrustTradePlan;
import hbec.intellitrade.condorder.domain.trigger.TradingMarketSupplier;
import hbec.intellitrade.condorder.domain.trigger.TriggerTradingContext;
import hbec.intellitrade.strategy.domain.signal.TradeSignal;
import hbec.intellitrade.trade.domain.*;
import hbec.intellitrade.trade.domain.exception.InsufficientCapitalException;
import hbec.intellitrade.trade.domain.exception.InsufficientPositionException;
import hbec.intellitrade.trade.domain.exception.TradeException;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * {@link ExplicitTradingSecurityOrder}骨架类
 *
 * @author caoshuhao@touker.com
 * @date 2018/3/17
 */
public abstract class AbstractExplicitTradingSecurityOrder extends AbstractConditionOrder
        implements ExplicitTradingSecurityOrder {
    private static final Logger logger = LoggerFactory.getLogger(AbstractExplicitTradingSecurityOrder.class);

    private final SecurityInfo securityInfo;

    public AbstractExplicitTradingSecurityOrder(Long orderId,
                                                TradeCustomerInfo tradeCustomerInfo,
                                                OrderState orderState,
                                                SecurityInfo securityInfo,
                                                LocalDateTime expireTime) {
        super(orderId, tradeCustomerInfo, orderState, expireTime);
        this.securityInfo = securityInfo;
    }

    @Override
    public SecurityInfo getSecurityInfo() {
        return securityInfo;
    }

    @Override
    public void onTradeSignal(TriggerTradingContext triggerTradingContext) {
        Preconditions.checkArgument(getOrderState() == OrderState.ACTIVE, "Order state should be ACTIVE");

        EntrustCommand entrustCommand = createEntrustCommands((TradeSignal) triggerTradingContext.getSignal(),
                                                              triggerTradingContext);
        executeEntrustCommand(triggerTradingContext, entrustCommand);
        afterEntrustCommandsExecuted(triggerTradingContext);
    }

    private void executeEntrustCommand(TriggerTradingContext triggerTradingContext, EntrustCommand entrustCommand) {
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
     * 交易信号处理行为
     *
     * @param tradeSignal           交易信号
     * @param tradingMarketSupplier 交易标的实时行情supplier
     * @return 交易指令
     */
    protected EntrustCommand createEntrustCommands(TradeSignal tradeSignal,
                                                   TradingMarketSupplier tradingMarketSupplier) {
        SingleEntrustTradePlan singleEntrustTradePlan = (SingleEntrustTradePlan) getTradePlan();
        return singleEntrustTradePlan.createEntrustCommand(tradeSignal, getSecurityInfo(), tradingMarketSupplier);
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
     * 所有委托完成事件
     *
     * @param triggerTradingContext 触发交易上下文
     */
    protected void afterEntrustCommandsExecuted(TriggerTradingContext triggerTradingContext) {
        setOrderState(OrderState.TERMINATED);
    }
}
