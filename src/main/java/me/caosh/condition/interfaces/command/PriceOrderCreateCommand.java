package me.caosh.condition.interfaces.command;

import com.google.common.base.MoreObjects;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by caosh on 2017/8/9.
 */
public class PriceOrderCreateCommand implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer securityType;
    private String securityCode;
    private String securityExchange;
    private String securityName;
    private Integer compareCondition;
    private BigDecimal targetPrice;
    private Integer exchangeType;
    private Integer entrustStrategy;
    private Integer entrustNumber;

    public Integer getSecurityType() {
        return securityType;
    }

    public void setSecurityType(Integer securityType) {
        this.securityType = securityType;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public String getSecurityExchange() {
        return securityExchange;
    }

    public void setSecurityExchange(String securityExchange) {
        this.securityExchange = securityExchange;
    }

    public String getSecurityName() {
        return securityName;
    }

    public void setSecurityName(String securityName) {
        this.securityName = securityName;
    }

    public Integer getCompareCondition() {
        return compareCondition;
    }

    public void setCompareCondition(Integer compareCondition) {
        this.compareCondition = compareCondition;
    }

    public BigDecimal getTargetPrice() {
        return targetPrice;
    }

    public void setTargetPrice(BigDecimal targetPrice) {
        this.targetPrice = targetPrice;
    }

    public Integer getExchangeType() {
        return exchangeType;
    }

    public void setExchangeType(Integer exchangeType) {
        this.exchangeType = exchangeType;
    }

    public Integer getEntrustStrategy() {
        return entrustStrategy;
    }

    public void setEntrustStrategy(Integer entrustStrategy) {
        this.entrustStrategy = entrustStrategy;
    }

    public Integer getEntrustNumber() {
        return entrustNumber;
    }

    public void setEntrustNumber(Integer entrustNumber) {
        this.entrustNumber = entrustNumber;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("securityType", securityType)
                .add("securityCode", securityCode)
                .add("securityExchange", securityExchange)
                .add("securityName", securityName)
                .add("compareCondition", compareCondition)
                .add("targetPrice", targetPrice)
                .add("exchangeType", exchangeType)
                .add("entrustStrategy", entrustStrategy)
                .add("entrustNumber", entrustNumber)
                .toString();
    }
}
