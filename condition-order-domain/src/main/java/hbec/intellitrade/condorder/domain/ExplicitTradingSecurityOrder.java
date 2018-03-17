package hbec.intellitrade.condorder.domain;

import hbec.intellitrade.common.security.SecurityInfo;

/**
 * 交易证券已经明确的条件单
 *
 * @author caoshuhao@touker.com
 * @date 2018/3/17
 */
public interface ExplicitTradingSecurityOrder extends ConditionOrder {
    /**
     * 获取交易证券信息
     *
     * @return 交易证券信息
     */
    SecurityInfo getSecurityInfo();
}
