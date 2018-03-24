package hbec.intellitrade.condorder.domain.tradeplan;

import hbec.intellitrade.common.security.SecurityInfo;
import hbec.intellitrade.condorder.domain.trigger.TradingMarketSupplier;
import hbec.intellitrade.strategy.domain.signal.TradeSignal;
import hbec.intellitrade.trade.domain.EntrustCommand;
import hbec.intellitrade.trade.domain.OrderType;

/**
 * 单一委托交易计划，每次触发只产生一笔委托
 *
 * @author caoshuhao@touker.com
 * @date 2018/3/1
 */
public interface SingleEntrustTradePlan extends TradePlan {
    /**
     * 获取交易数量
     *
     * @return 交易数量
     */
    TradeNumber getTradeNumber();

    /**
     * 获取订单类别
     *
     * @return 订单类别
     */
    OrderType getOrderType();

    /**
     * 创建委托指令
     *
     * @param tradeSignal           交易信号
     * @param securityInfo          交易证券
     * @param tradingMarketSupplier 交易证券行情提供者
     * @return 交易指令，不可为空
     */
    EntrustCommand createEntrustCommand(TradeSignal tradeSignal,
                                        SecurityInfo securityInfo,
                                        TradingMarketSupplier tradingMarketSupplier);
}
