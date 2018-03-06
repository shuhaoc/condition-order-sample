package me.caosh.condition.domain.model.order.plan;

import hbec.intellitrade.common.security.SecurityInfo;
import me.caosh.condition.domain.model.order.TradingMarketSupplier;
import me.caosh.condition.domain.model.signal.TradeSignal;
import me.caosh.condition.domain.model.trade.EntrustCommand;

/**
 * @author caoshuhao@touker.com
 * @date 2018/3/1
 */
public interface SingleEntrustTradePlan extends TradePlan {
    EntrustCommand createEntrustCommand(TradeSignal tradeSignal, SecurityInfo securityInfo, TradingMarketSupplier tradingMarketSupplier);
}
