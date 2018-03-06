package me.caosh.condition.domain.model.order;

import hbec.intellitrade.common.security.SecurityInfo;
import hbec.intellitrade.strategy.domain.Strategy;
import me.caosh.condition.domain.model.order.constant.StrategyState;
import me.caosh.condition.domain.model.order.plan.TradePlan;
import hbec.intellitrade.strategy.domain.condition.Condition;
import me.caosh.condition.domain.model.strategyinfo.StrategyInfo;

/**
 * 条件单实体
 * <p>
 * 条件单是具体的策略，其触发行为以交易为主
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2017/8/29
 */
public interface ConditionOrder extends Strategy, AutoTradeAction {
    Long getOrderId();

    TradeCustomerInfo getCustomer();

    SecurityInfo getSecurityInfo();

    StrategyInfo getStrategyInfo();

    Condition getRawCondition();

    TradePlan getTradePlan();

    @Override
    StrategyState getStrategyState();

    void onTradeSignal(TriggerTradingContext triggerTradingContext);
}
