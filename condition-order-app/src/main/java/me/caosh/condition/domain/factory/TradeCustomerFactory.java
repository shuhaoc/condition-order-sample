package me.caosh.condition.domain.factory;

import me.caosh.condition.domain.model.account.TradeCustomer;
import me.caosh.condition.domain.model.order.TradeCustomerInfo;
import org.springframework.stereotype.Component;

/**
 * @author caoshuhao@touker.com
 * @date 2018/3/4
 */
@Component
public class TradeCustomerFactory {
    public TradeCustomer createTradeCustomer(TradeCustomerInfo tradeCustomerInfo) {
        return new TradeCustomer(tradeCustomerInfo.getUserId(), tradeCustomerInfo.getCustomerNo());
    }
}
