package me.caosh.condition.domain.model.order.plan;

import com.google.common.base.MoreObjects;
import me.caosh.condition.domain.model.order.constant.EntrustStrategy;
import me.caosh.condition.domain.model.order.constant.ExchangeType;

/**
 * Created by caosh on 2017/8/2.
 */
public class TradePlan {
    private final ExchangeType exchangeType;
    private final EntrustStrategy entrustStrategy;
    private final TradeNumber tradeNumber;

    public TradePlan(ExchangeType exchangeType, EntrustStrategy entrustStrategy, TradeNumber tradeNumber) {
        this.exchangeType = exchangeType;
        this.entrustStrategy = entrustStrategy;
        this.tradeNumber = tradeNumber;
    }

    public ExchangeType getExchangeType() {
        return exchangeType;
    }

    public EntrustStrategy getEntrustStrategy() {
        return entrustStrategy;
    }

    public TradeNumber getTradeNumber() {
        return tradeNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TradePlan)) return false;

        TradePlan tradePlan = (TradePlan) o;

        if (exchangeType != tradePlan.exchangeType) return false;
        if (entrustStrategy != tradePlan.entrustStrategy) return false;
        return !(tradeNumber != null ? !tradeNumber.equals(tradePlan.tradeNumber) : tradePlan.tradeNumber != null);

    }

    @Override
    public int hashCode() {
        int result = exchangeType != null ? exchangeType.hashCode() : 0;
        result = 31 * result + (entrustStrategy != null ? entrustStrategy.hashCode() : 0);
        result = 31 * result + (tradeNumber != null ? tradeNumber.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("exchangeType", exchangeType)
                .add("entrustStrategy", entrustStrategy)
                .add("tradeNumber", tradeNumber)
                .toString();
    }
}
