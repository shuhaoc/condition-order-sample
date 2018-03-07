package hbec.intellitrade.condorder.domain.trigger;

import hbec.intellitrade.common.market.RealTimeMarket;
import hbec.intellitrade.condorder.domain.ConditionOrder;
import hbec.intellitrade.strategy.domain.signal.Signal;
import hbec.intellitrade.trade.domain.EntrustOrderInfo;
import me.caosh.condition.domain.model.account.TradeCustomer;

/**
 * 交易信号触发上下文
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2018/3/6
 */
public interface TriggerTradingContext extends TradingMarketSupplier {
    Signal getSignal();

    ConditionOrder getConditionOrder();

    TradeCustomer getTradeCustomer();

    RealTimeMarket getTriggerMarket();

    void saveEntrustOrder(EntrustOrderInfo entrustOrderInfo);
}
