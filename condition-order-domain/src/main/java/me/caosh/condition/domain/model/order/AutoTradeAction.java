package me.caosh.condition.domain.model.order;

import hbec.intellitrade.strategy.domain.signal.TradeSignal;
import hbec.intellitrade.trade.domain.EntrustCommand;
import hbec.intellitrade.trade.domain.EntrustResult;

import java.util.List;

/**
 * 通用条件单的自动交易行为，提供细分步骤接口
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2018/3/6
 */
public interface AutoTradeAction {
    /**
     * 交易信号处理行为
     *
     * @param tradeSignal           交易信号
     * @param tradingMarketSupplier 交易标的实时行情supplier
     * @return 交易指令
     */
    List<EntrustCommand> createEntrustCommands(TradeSignal tradeSignal, TradingMarketSupplier tradingMarketSupplier);

    /**
     * 委托成功事件
     *
     * @param triggerTradingContext 触发交易上下文
     * @param entrustCommand 委托指令
     * @param entrustResult 委托结果
     */
    void afterEntrustSuccess(TriggerTradingContext triggerTradingContext, EntrustCommand entrustCommand, EntrustResult entrustResult);

    /**
     * 所有委托完成事件
     *
     * @param triggerTradingContext 触发交易上下文
     */
    void afterEntrustCommandsExecuted(TriggerTradingContext triggerTradingContext);
}
