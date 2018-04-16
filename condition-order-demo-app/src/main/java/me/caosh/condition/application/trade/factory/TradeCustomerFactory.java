package me.caosh.condition.application.trade.factory;

import hbec.intellitrade.conditionorder.domain.TradeCustomerInfo;
import hbec.intellitrade.trade.domain.TradeCustomer;
import me.caosh.condition.application.trade.MockTradeCustomer;
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
