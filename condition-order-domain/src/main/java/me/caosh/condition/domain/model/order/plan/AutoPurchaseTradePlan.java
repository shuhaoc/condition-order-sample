package me.caosh.condition.domain.model.order.plan;

import me.caosh.condition.domain.model.order.constant.ExchangeType;

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
        return 0;
    }

    @Override
    public void accept(TradePlanVisitor visitor) {
        visitor.visitAutoPurchaseTradePlan(this);
    }
}
