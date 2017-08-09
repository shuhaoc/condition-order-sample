package me.caosh.condition.domain.model.entrust;

import com.google.common.base.MoreObjects;

import java.math.BigDecimal;

/**
 * Created by caosh on 2017/8/9.
 */
public class EntrustCommand {
    private final String securityCode;
    private final BigDecimal entrustPrice;
    private final int entrustNumber;

    public EntrustCommand(String securityCode, BigDecimal entrustPrice, int entrustNumber) {
        this.securityCode = securityCode;
        this.entrustPrice = entrustPrice;
        this.entrustNumber = entrustNumber;
    }

    public String getSecurityCode() {
        return securityCode;
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
                .add("securityCode", securityCode)
                .add("entrustPrice", entrustPrice)
                .add("entrustNumber", entrustNumber)
                .toString();
    }
}
