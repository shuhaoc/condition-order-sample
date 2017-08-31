package me.caosh.condition.domain.model.trade;

import com.google.common.base.MoreObjects;
import me.caosh.condition.domain.model.market.SecurityInfo;
import me.caosh.condition.domain.model.order.TradeCustomerIdentity;
import me.caosh.condition.domain.model.order.constant.ExchangeType;

import java.math.BigDecimal;

/**
 * Created by caosh on 2017/8/9.
 */
public class EntrustCommand {
    private final TradeCustomerIdentity customerIdentity;
    private final SecurityInfo securityInfo;
    private final ExchangeType exchangeType;
    private final BigDecimal entrustPrice; // nullable
    private final int entrustNumber;
    private final OrderType orderType;

    public EntrustCommand(TradeCustomerIdentity customerIdentity, SecurityInfo securityInfo, ExchangeType exchangeType,
                          BigDecimal entrustPrice, int entrustNumber, OrderType orderType) {
        this.customerIdentity = customerIdentity;
        this.securityInfo = securityInfo;
        this.exchangeType = exchangeType;
        this.entrustPrice = entrustPrice;
        this.entrustNumber = entrustNumber;
        this.orderType = orderType;
    }

    public TradeCustomerIdentity getCustomerIdentity() {
        return customerIdentity;
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
        if (!(o instanceof EntrustCommand)) return false;

        EntrustCommand that = (EntrustCommand) o;

        if (entrustNumber != that.entrustNumber) return false;
        if (customerIdentity != null ? !customerIdentity.equals(that.customerIdentity) : that.customerIdentity != null)
            return false;
        if (securityInfo != null ? !securityInfo.equals(that.securityInfo) : that.securityInfo != null) return false;
        if (exchangeType != that.exchangeType) return false;
        if (entrustPrice != null ? !entrustPrice.equals(that.entrustPrice) : that.entrustPrice != null) return false;
        return orderType == that.orderType;
    }

    @Override
    public int hashCode() {
        int result = customerIdentity != null ? customerIdentity.hashCode() : 0;
        result = 31 * result + (securityInfo != null ? securityInfo.hashCode() : 0);
        result = 31 * result + (exchangeType != null ? exchangeType.hashCode() : 0);
        result = 31 * result + (entrustPrice != null ? entrustPrice.hashCode() : 0);
        result = 31 * result + entrustNumber;
        result = 31 * result + (orderType != null ? orderType.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("customerIdentity", customerIdentity)
                .add("securityInfo", securityInfo)
                .add("exchangeType", exchangeType)
                .add("entrustPrice", entrustPrice)
                .add("entrustNumber", entrustNumber)
                .add("orderType", orderType)
                .toString();
    }
}
