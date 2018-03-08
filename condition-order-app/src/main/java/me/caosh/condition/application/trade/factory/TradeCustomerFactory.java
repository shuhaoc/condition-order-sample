package me.caosh.condition.application.trade.factory;

import hbec.intellitrade.condorder.domain.TradeCustomerInfo;
import me.caosh.condition.application.trade.MockTradeCustomer;
import hbec.intellitrade.trade.domain.TradeCustomer;
import org.springframework.stereotype.Component;

/**
 * @author caoshuhao@touker.com
 * @date 2018/3/4
 */
@Component
public class TradeCustomerFactory {
    public TradeCustomer createTradeCustomer(TradeCustomerInfo tradeCustomerInfo) {
        return new MockTradeCustomer(tradeCustomerInfo.getCustomerNo());
    }
}
