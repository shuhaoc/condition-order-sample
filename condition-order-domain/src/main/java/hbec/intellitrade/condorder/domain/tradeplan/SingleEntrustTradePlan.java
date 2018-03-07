package hbec.intellitrade.condorder.domain.tradeplan;

import hbec.intellitrade.common.security.SecurityInfo;
import hbec.intellitrade.condorder.domain.trigger.TradingMarketSupplier;
import hbec.intellitrade.strategy.domain.signal.TradeSignal;
import hbec.intellitrade.trade.domain.EntrustCommand;

/**
 * @author caoshuhao@touker.com
 * @date 2018/3/1
 */
public interface SingleEntrustTradePlan extends TradePlan {
    EntrustCommand createEntrustCommand(TradeSignal tradeSignal, SecurityInfo securityInfo, TradingMarketSupplier tradingMarketSupplier);
}
