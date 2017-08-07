package me.caosh.condition.domain.model.market;

import com.google.common.base.MoreObjects;

import java.math.BigDecimal;

/**
 * Created by caosh on 2017/8/7.
 */
public class RealTimeMarket {
    private final SecurityInfo securityInfo;
    private final BigDecimal currentPrice;

    public RealTimeMarket(SecurityInfo securityInfo, BigDecimal currentPrice) {
        this.securityInfo = securityInfo;
        this.currentPrice = currentPrice;
    }

    public SecurityInfo getSecurityInfo() {
        return securityInfo;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("securityInfo", securityInfo)
                .add("currentPrice", currentPrice)
                .toString();
    }
}
