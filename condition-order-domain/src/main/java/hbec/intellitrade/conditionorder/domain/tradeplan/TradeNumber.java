package hbec.intellitrade.conditionorder.domain.tradeplan;

import hbec.intellitrade.common.security.SecurityInfo;

import java.math.BigDecimal;

/**
 * 委托数量计算功能
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2017/8/17
 */
public interface TradeNumber {
    /**
     * 委托方法
     *
     * @return 委托方法
     */
    EntrustMethod getEntrustMethod();

    /**
     * 基于给定的委托价格计算委托数量
     *
     * @param securityInfo 证券信息
     * @param entrustPrice 委托价格
     * @return 委托数量
     */
    int getNumber(SecurityInfo securityInfo, BigDecimal entrustPrice);
}
