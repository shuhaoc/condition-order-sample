package hbec.intellitrade.condorder.domain;

import hbec.intellitrade.common.security.SecurityInfo;
import hbec.intellitrade.condorder.domain.strategyinfo.StrategyInfo;
import hbec.intellitrade.condorder.domain.tradeplan.TradePlan;
import hbec.intellitrade.condorder.domain.trigger.TriggerTradingContext;
import hbec.intellitrade.strategy.domain.Strategy;
import hbec.intellitrade.strategy.domain.condition.Condition;

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

    /**
     * 获取策略条件
     *
     * @return 策略条件
     */
    Condition getCondition();

    Condition getRawCondition();

    TradePlan getTradePlan();

    OrderState getOrderState();

    void onTradeSignal(TriggerTradingContext triggerTradingContext);
}
