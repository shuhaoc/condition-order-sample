package hbec.intellitrade.condorder.domain;

import hbec.intellitrade.condorder.domain.strategyinfo.StrategyInfo;
import hbec.intellitrade.condorder.domain.tradeplan.TradePlan;
import hbec.intellitrade.condorder.domain.trigger.TriggerTradingContext;
import hbec.intellitrade.strategy.domain.Strategy;
import hbec.intellitrade.strategy.domain.condition.Condition;

/**
 * 条件单实体
 * <p>
 * 条件单是交易信号触发交易行为的策略
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2017/8/29
 */
public interface ConditionOrder extends Strategy {
    /**
     * 获取条件单ID
     *
     * @return 条件单ID
     */
    long getOrderId();

    /**
     * 获取客户标识信息
     *
     * @return 客户标识信息
     */
    TradeCustomerInfo getCustomer();

    /**
     * 获取条件单状态
     *
     * @return 条件单状态
     */
    OrderState getOrderState();

    /**
     * 判断条件单状态是否为监控中的
     *
     * @return 是否为监控中的
     */
    boolean isMonitoringState();

    /**
     * 获取策略描述信息
     *
     * @return 策略描述信息
     */
    StrategyInfo getStrategyInfo();

    /**
     * 获取原始触发条件，即不包含高级选项因子的条件
     *
     * @return 原始条件
     */
    Condition getRawCondition();

    /**
     * 获取交易计划
     *
     * @return 交易计划
     */
    TradePlan getTradePlan();

    /**
     * 响应交易信号
     *
     * @param triggerTradingContext 触发交易上下文
     */
    void onTradeSignal(TriggerTradingContext triggerTradingContext);

    /**
     * 响应过期信号
     */
    void onExpired();
}
