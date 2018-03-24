package hbec.intellitrade.condorder.domain.tradeplan;

import hbec.intellitrade.trade.domain.ExchangeType;

/**
 * 自动申购新股交易计划，仅用于DB字段占位
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2017/8/31
 */
public class AutoPurchaseTradePlan implements TradePlan {
    private static final TradeNumberDirect TRADE_NUMBER = new TradeNumberDirect(0);

    public TradeNumber getTradeNumber() {
        return TRADE_NUMBER;
    }

    public int getExchangeType() {
        return ExchangeType.QUOTA_PURCHASE.getValue();
    }

    public int getEntrustStrategy() {
        return 0;
    }

}
