package me.caosh.condition.domain.model.trade;

import com.google.common.base.MoreObjects;

import java.math.BigDecimal;

/**
 * Created by caosh on 2017/8/9.
 */
public class EntrustCommand {
    private final SecurityID securityID;
    private final BigDecimal entrustPrice;
    private final int entrustNumber;

    public EntrustCommand(SecurityID securityID, BigDecimal entrustPrice, int entrustNumber) {
        this.securityID = securityID;
        this.entrustPrice = entrustPrice;
        this.entrustNumber = entrustNumber;
    }

    public SecurityID getSecurityID() {
        return securityID;
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
                .add("entrustPrice", entrustPrice)
                .add("entrustNumber", entrustNumber)
                .toString();
    }
}
