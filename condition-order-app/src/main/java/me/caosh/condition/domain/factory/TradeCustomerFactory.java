package me.caosh.condition.domain.factory;

import hbec.intellitrade.condorder.domain.TradeCustomerInfo;
import me.caosh.condition.domain.model.account.TradeCustomer;
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
