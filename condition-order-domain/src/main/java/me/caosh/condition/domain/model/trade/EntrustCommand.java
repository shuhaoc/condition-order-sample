package me.caosh.condition.domain.model.trade;

import com.google.common.base.MoreObjects;
import me.caosh.condition.domain.model.market.SecurityInfo;
import me.caosh.condition.domain.model.order.ExchangeType;
import me.caosh.condition.domain.model.order.TradeCustomerIdentity;

import java.math.BigDecimal;

/**
 * Created by caosh on 2017/8/9.
 */
public class EntrustCommand {
    private final TradeCustomerIdentity customerIdentity;
    private final SecurityInfo securityInfo;
    private final ExchangeType exchangeType;
    private final BigDecimal entrustPrice;
    private final int entrustNumber;

    public EntrustCommand(TradeCustomerIdentity customerIdentity, SecurityInfo securityInfo, ExchangeType exchangeType,
                          BigDecimal entrustPrice, int entrustNumber) {
        this.customerIdentity = customerIdentity;
        this.securityInfo = securityInfo;
        this.exchangeType = exchangeType;
        this.entrustPrice = entrustPrice;
        this.entrustNumber = entrustNumber;
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

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(EntrustCommand.class).omitNullValues()
                .addValue(EntrustCommand.class.getSuperclass() != Object.class ? super.toString() : null)
                .add("securityInfo", securityInfo)
                .add("exchangeType", exchangeType)
                .add("entrustPrice", entrustPrice)
                .add("entrustNumber", entrustNumber)
                .toString();
    }
}
