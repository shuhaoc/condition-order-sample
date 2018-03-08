package hbec.intellitrade.trade.domain;

import hbec.intellitrade.trade.domain.exception.TradeException;

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
     * @throws TradeException 交易异常
     */
    EntrustSuccessResult entrust(EntrustCommand entrustCommand) throws TradeException;
}
