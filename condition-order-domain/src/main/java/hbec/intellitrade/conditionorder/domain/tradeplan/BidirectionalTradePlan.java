package hbec.intellitrade.conditionorder.domain.tradeplan;

/**
 * 双向交易计划
 *
 * @author caoshuhao@touker.com
 * @date 2018/5/2
 */
public interface BidirectionalTradePlan extends SingleEntrustTradePlan {
    /**
     * 获取买入计划
     *
     * @return 买入计划
     */
    SingleEntrustTradePlan getBuyPlan();

    /**
     * 获取卖出计划
     *
     * @return 卖出计划
     */
    SingleEntrustTradePlan getSellPlan();
}
