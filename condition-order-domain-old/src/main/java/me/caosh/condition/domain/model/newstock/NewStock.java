package me.caosh.condition.domain.model.newstock;

import com.google.common.base.MoreObjects;
import hbec.intellitrade.common.security.SecurityExchange;

import java.math.BigDecimal;

/**
 * Created by caosh on 2017/9/4.
 *
 * @author caoshuhao@touker.com
 */
public class NewStock {
    private final String purchaseCode;
    private final String purchaseName;
    private final SecurityExchange securityExchange;
    private final BigDecimal issuePrice;
    private final int purchaseUpperLimit;

    public NewStock(String purchaseCode, String purchaseName, SecurityExchange securityExchange, BigDecimal issuePrice, int purchaseUpperLimit) {
        this.purchaseCode = purchaseCode;
        this.purchaseName = purchaseName;
        this.securityExchange = securityExchange;
        this.issuePrice = issuePrice;
        this.purchaseUpperLimit = purchaseUpperLimit;
    }

    public String getPurchaseCode() {
        return purchaseCode;
    }

    public String getPurchaseName() {
        return purchaseName;
    }

    public SecurityExchange getSecurityExchange() {
        return securityExchange;
    }

    public BigDecimal getIssuePrice() {
        return issuePrice;
    }

    public int getPurchaseUpperLimit() {
        return purchaseUpperLimit;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(NewStock.class).omitNullValues()
                .addValue(NewStock.class.getSuperclass() != Object.class ? super.toString() : null)
                .add("purchaseCode", purchaseCode)
                .add("purchaseName", purchaseName)
                .add("securityExchange", securityExchange)
                .add("issuePrice", issuePrice)
                .add("purchaseUpperLimit", purchaseUpperLimit)
                .toString();
    }
}
