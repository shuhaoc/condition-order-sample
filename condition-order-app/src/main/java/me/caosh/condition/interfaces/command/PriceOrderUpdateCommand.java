package me.caosh.condition.interfaces.command;

import com.google.common.base.MoreObjects;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by caosh on 2017/8/9.
 */
public class PriceOrderUpdateCommand implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long orderId;
    private Integer compareCondition;
    private BigDecimal targetPrice;
    private Integer exchangeType;
    private Integer entrustStrategy;
    private Integer entrustNumber;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
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
                .add("orderId", orderId)
                .add("compareCondition", compareCondition)
                .add("targetPrice", targetPrice)
                .add("exchangeType", exchangeType)
                .add("entrustStrategy", entrustStrategy)
                .add("entrustNumber", entrustNumber)
                .toString();
    }
}
