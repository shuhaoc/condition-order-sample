package me.caosh.condition.domain.model.trade;

import com.google.common.base.MoreObjects;
import me.caosh.condition.domain.model.order.ExchangeType;

import java.math.BigDecimal;

/**
 * Created by caosh on 2017/8/9.
 */
public class EntrustCommand {
    private final SecurityID securityID;
    private final ExchangeType exchangeType;
    private final BigDecimal entrustPrice;
    private final int entrustNumber;

    public EntrustCommand(SecurityID securityID, ExchangeType exchangeType, BigDecimal entrustPrice, int entrustNumber) {
        this.securityID = securityID;
        this.exchangeType = exchangeType;
        this.entrustPrice = entrustPrice;
        this.entrustNumber = entrustNumber;
    }

    public SecurityID getSecurityID() {
        return securityID;
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
        return MoreObjects.toStringHelper(this)
                .add("securityID", securityID)
                .add("exchangeType", exchangeType)
                .add("entrustPrice", entrustPrice)
                .add("entrustNumber", entrustNumber)
                .toString();
    }
}
