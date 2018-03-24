package hbec.intellitrade.condorder.domain.tradeplan;

import hbec.intellitrade.common.security.SecurityInfo;
import hbec.intellitrade.condorder.domain.trigger.TradingMarketSupplier;
import hbec.intellitrade.strategy.domain.signal.TradeSignal;
import hbec.intellitrade.trade.domain.EntrustCommand;

/**
 * 单次委托交易计划
 *
 * @author caoshuhao@touker.com
 * @date 2018/3/1
 */
public interface SingleEntrustTradePlan extends TradePlan {
    /**
     * 创建委托指令
     *
     * @param tradeSignal
     * @param securityInfo
     * @param tradingMarketSupplier
     * @return
     */
    EntrustCommand createEntrustCommand(TradeSignal tradeSignal,
                                        SecurityInfo securityInfo,
                                        TradingMarketSupplier tradingMarketSupplier);
}
