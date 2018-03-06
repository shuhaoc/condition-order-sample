package me.caosh.condition.domain.model.order;

import hbec.intellitrade.common.security.SecurityInfo;
import hbec.intellitrade.strategy.domain.Strategy;
import me.caosh.condition.domain.model.account.TradeCustomer;
import me.caosh.condition.domain.model.order.constant.StrategyState;
import me.caosh.condition.domain.model.order.plan.TradePlan;
import me.caosh.condition.domain.model.signal.TradeSignal;
import me.caosh.condition.domain.model.strategy.condition.Condition;
import me.caosh.condition.domain.model.strategyinfo.StrategyInfo;
import me.caosh.condition.domain.model.trade.EntrustCommand;
import me.caosh.condition.domain.model.trade.EntrustResult;

import java.util.List;

/**
 * 条件单实体
 * <p>
 * 条件单是具体的策略，其触发行为以交易为主
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2017/8/29
 */
public interface ConditionOrder extends Strategy {
    Long getOrderId();

    TradeCustomerInfo getCustomer();

    SecurityInfo getSecurityInfo();

    StrategyInfo getStrategyInfo();

    Condition getRawCondition();

    TradePlan getTradePlan();

    @Override
    StrategyState getStrategyState();

    /**
     * 交易信号处理行为
     *
     * @param tradeSignal           交易信号
     * @param tradeCustomer         客户实体
     * @param tradingMarketSupplier 交易标的实时行情supplier
     * @return 交易指令
     */
    List<EntrustCommand> onTradeSignal(TradeSignal tradeSignal, TradeCustomer tradeCustomer, TradingMarketSupplier tradingMarketSupplier);

    void afterEntrustSuccess(TriggerTradingContext triggerTradingContext, EntrustResult entrustResult);

    void afterEntrustCommandsExecuted(TriggerTradingContext triggerTradingContext);
}
