package me.caosh.condition.domain.model.trade;

import com.google.common.base.MoreObjects;
import me.caosh.condition.domain.model.market.SecurityInfo;
import me.caosh.condition.domain.model.order.constant.ExchangeType;

import java.math.BigDecimal;

/**
 * Created by caosh on 2017/8/9.
 */
public class EntrustCommand {
    private final SecurityInfo securityInfo;
    private final ExchangeType exchangeType;
    /**
     * nullable
     */
    private final BigDecimal entrustPrice;
    private final int entrustNumber;
    private final OrderType orderType;

    public EntrustCommand(SecurityInfo securityInfo, ExchangeType exchangeType, BigDecimal entrustPrice,
                          int entrustNumber, OrderType orderType) {
        this.securityInfo = securityInfo;
        this.exchangeType = exchangeType;
        this.entrustPrice = entrustPrice;
        this.entrustNumber = entrustNumber;
        this.orderType = orderType;
    }

    public SecurityInfo getSecurityInfo() {
        return securityInfo;
    }

    public ExchangeType getExchangeType() {
        return exchangeType;
    }

    public BigDecimal getEntrustPrice() {
        return entrustPrice;
    }

    public int getEntrustNumber() {
        return entrustNumber;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EntrustCommand that = (EntrustCommand) o;

        if (entrustNumber != that.entrustNumber) return false;
        if (!securityInfo.equals(that.securityInfo)) return false;
        if (exchangeType != that.exchangeType) return false;
        if (entrustPrice != null ? !entrustPrice.equals(that.entrustPrice) : that.entrustPrice != null) return false;
        return orderType == that.orderType;
    }

    @Override
    public int hashCode() {
        int result = securityInfo.hashCode();
        result = 31 * result + exchangeType.hashCode();
        result = 31 * result + (entrustPrice != null ? entrustPrice.hashCode() : 0);
        result = 31 * result + entrustNumber;
        result = 31 * result + orderType.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(EntrustCommand.class).omitNullValues()
                .add("securityInfo", securityInfo)
                .add("exchangeType", exchangeType)
                .add("entrustPrice", entrustPrice)
                .add("entrustNumber", entrustNumber)
                .add("orderType", orderType)
                .toString();
    }
}
