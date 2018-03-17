package hbec.intellitrade.condorder.domain;

import hbec.intellitrade.common.security.SecurityInfo;
import org.joda.time.LocalDateTime;

/**
 * @author caoshuhao@touker.com
 * @date 2018/3/17
 */
public abstract class AbstractExplicitTradingSecurityOrder extends AbstractConditionOrder {
    public AbstractExplicitTradingSecurityOrder(Long orderId,
                                                TradeCustomerInfo tradeCustomerInfo,
                                                OrderState orderState,
                                                SecurityInfo securityInfo,
                                                LocalDateTime expireTime) {
        super(orderId, tradeCustomerInfo, orderState, securityInfo, expireTime);
    }
}
