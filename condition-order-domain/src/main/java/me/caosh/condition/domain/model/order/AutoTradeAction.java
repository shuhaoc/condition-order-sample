package me.caosh.condition.domain.model.order;

import me.caosh.condition.domain.model.signal.TradeSignal;
import me.caosh.condition.domain.model.trade.EntrustCommand;
import me.caosh.condition.domain.model.trade.EntrustResult;

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

    void afterEntrustSuccess(TriggerTradingContext triggerTradingContext, EntrustCommand entrustCommand, EntrustResult entrustResult);

    void afterEntrustCommandsExecuted(TriggerTradingContext triggerTradingContext);
}
