package hbec.intellitrade.condorder.domain;

import hbec.intellitrade.common.security.SecurityInfo;
import hbec.intellitrade.condorder.domain.trigger.TriggerTradingContext;
import hbec.intellitrade.strategy.domain.Strategy;

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

    OrderState getOrderState();

    boolean isMonitoringState();

    void onTradeSignal(TriggerTradingContext triggerTradingContext);

    void onExpired();
}
