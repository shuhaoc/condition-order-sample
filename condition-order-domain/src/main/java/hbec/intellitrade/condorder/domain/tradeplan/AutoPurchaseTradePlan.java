package hbec.intellitrade.condorder.domain.tradeplan;

import hbec.intellitrade.trade.domain.ExchangeType;

/**
 * Created by caosh on 2017/8/31.
 *
 * @author caoshuhao@touker.com
 */
public class AutoPurchaseTradePlan implements TradePlan {
    private final TradeNumberDirect placeholder = new TradeNumberDirect(0);

    public TradeNumber getTradeNumber() {
        return placeholder;
    }

    public int getExchangeTypeValue() {
        return ExchangeType.QUOTA_PURCHASE.getValue();
    }

    public int getEntrustStrategyValue() {
        // 使用默认值0占位
        return EntrustStrategy.CUSTOMIZED_PRICE.getValue();
    }

}
