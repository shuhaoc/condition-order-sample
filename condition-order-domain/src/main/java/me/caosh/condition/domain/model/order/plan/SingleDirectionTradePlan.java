package me.caosh.condition.domain.model.order.plan;

import com.google.common.base.MoreObjects;
import me.caosh.condition.domain.model.order.constant.EntrustStrategy;
import me.caosh.condition.domain.model.order.constant.ExchangeType;

/**
 * Created by caosh on 2017/8/23.
 */
public class SingleDirectionTradePlan implements TradePlan {
    private final ExchangeType exchangeType;
    private final EntrustStrategy entrustStrategy;
    private final TradeNumber tradeNumber;

    public SingleDirectionTradePlan(ExchangeType exchangeType, EntrustStrategy entrustStrategy, TradeNumber tradeNumber) {
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

    @Override
    public TradeNumber getTradeNumber() {
        return tradeNumber;
    }

    @Override
    public void accept(TradePlanVisitor visitor) {
        visitor.visitSingleDirectionTradePlan(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SingleDirectionTradePlan)) return false;

        SingleDirectionTradePlan that = (SingleDirectionTradePlan) o;

        if (exchangeType != that.exchangeType) return false;
        if (entrustStrategy != that.entrustStrategy) return false;
        return !(tradeNumber != null ? !tradeNumber.equals(that.tradeNumber) : that.tradeNumber != null);

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
