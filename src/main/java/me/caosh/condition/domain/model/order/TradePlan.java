package me.caosh.condition.domain.model.order;

import com.google.common.base.MoreObjects;

/**
 * Created by caosh on 2017/8/2.
 */
public class TradePlan {
    private final ExchangeType exchangeType;
    private final EntrustStrategy entrustStrategy;
    private final int number;

    public TradePlan(ExchangeType exchangeType, EntrustStrategy entrustStrategy, int number) {
        this.exchangeType = exchangeType;
        this.entrustStrategy = entrustStrategy;
        this.number = number;
    }

    public ExchangeType getExchangeType() {
        return exchangeType;
    }

    public EntrustStrategy getEntrustStrategy() {
        return entrustStrategy;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TradePlan)) return false;

        TradePlan tradePlan = (TradePlan) o;

        if (number != tradePlan.number) return false;
        if (exchangeType != tradePlan.exchangeType) return false;
        return entrustStrategy == tradePlan.entrustStrategy;

    }

    @Override
    public int hashCode() {
        int result = exchangeType != null ? exchangeType.hashCode() : 0;
        result = 31 * result + (entrustStrategy != null ? entrustStrategy.hashCode() : 0);
        result = 31 * result + number;
        return result;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("exchangeType", exchangeType)
                .add("entrustPriceStrategy", entrustStrategy)
                .add("number", number)
                .toString();
    }
}
