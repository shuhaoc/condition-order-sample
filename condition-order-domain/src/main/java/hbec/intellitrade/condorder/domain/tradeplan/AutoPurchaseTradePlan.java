package hbec.intellitrade.condorder.domain.tradeplan;

import hbec.intellitrade.trade.domain.ExchangeType;

/**
 * Created by caosh on 2017/8/31.
 *
 * @author caoshuhao@touker.com
 */
public class AutoPurchaseTradePlan implements TradePlan {
    private final TradeNumberDirect placeholder = new TradeNumberDirect(0);

    @Override
    public TradeNumber getTradeNumber() {
        return placeholder;
    }

    public int getExchangeTypeValue() {
        return ExchangeType.QUOTA_PURCHASE.getValue();
    }

    public int getEntrustStrategyValue() {
        return EntrustStrategy.ASSIGNED_PRICE.getValue();
    }

    @Override
    public void accept(TradePlanVisitor visitor) {
        visitor.visitAutoPurchaseTradePlan(this);
    }
}
