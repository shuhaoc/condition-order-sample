package me.caosh.condition.domain.model.order;

import com.google.common.base.MoreObjects;

/**
 * Created by caosh on 2017/8/2.
 */
public class TradePlan {
    private final ExchangeType exchangeType;
    private final EntrustPriceStrategy entrustPriceStrategy;
    private final int number;

    public TradePlan(ExchangeType exchangeType, EntrustPriceStrategy entrustPriceStrategy, int number) {
        this.exchangeType = exchangeType;
        this.entrustPriceStrategy = entrustPriceStrategy;
        this.number = number;
    }

    public ExchangeType getExchangeType() {
        return exchangeType;
    }

    public EntrustPriceStrategy getEntrustPriceStrategy() {
        return entrustPriceStrategy;
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
        return entrustPriceStrategy == tradePlan.entrustPriceStrategy;

    }

    @Override
    public int hashCode() {
        int result = exchangeType != null ? exchangeType.hashCode() : 0;
        result = 31 * result + (entrustPriceStrategy != null ? entrustPriceStrategy.hashCode() : 0);
        result = 31 * result + number;
        return result;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("exchangeType", exchangeType)
                .add("entrustPriceStrategy", entrustPriceStrategy)
                .add("number", number)
                .toString();
    }
}
