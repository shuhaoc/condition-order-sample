package hbec.intellitrade.trade.domain;

/**
 * 交易客户
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2017/8/14
 */
public interface TradeCustomer {
    /**
     * 执行证券委托
     *
     * @param entrustCommand 委托命令
     * @return 委托结果
     */
    EntrustResult entrust(EntrustCommand entrustCommand);
}
