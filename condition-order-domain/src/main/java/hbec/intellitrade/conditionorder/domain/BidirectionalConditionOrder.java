package hbec.intellitrade.conditionorder.domain;

import hbec.intellitrade.conditionorder.domain.tradeplan.BidirectionalTradePlan;

/**
 * 双向交易条件单
 *
 * @author caoshuhao@touker.com
 * @date 2018/5/2
 */
public interface BidirectionalConditionOrder extends ExplicitTradingSecurityOrder {
    /**
     * 获取双向交易计划
     *
     * @return 双向交易计划
     */
    BidirectionalTradePlan getTradePlan();
}
