package me.caosh.condition.domain.dto.trade;

import com.google.common.base.MoreObjects;

import java.math.BigDecimal;

/**
 * Created by caosh on 2017/8/15.
 */
public class EntrustOrderDTO {
    private Long entrustId;
    private Long orderId;
    private Integer userId;
    private String customerNo;
    private Integer securityType;
    private String securityCode;
    private String securityExchange;
    private String securityName;
    private Integer exchangeType;
    private BigDecimal entrustPrice;
    private Integer entrustNumber;
    private Integer orderType;
    private Integer entrustState;
    private String entrustMessage;
    private Integer entrustCode;

    public EntrustOrderDTO() {
    }

    public EntrustOrderDTO(Long entrustId, Long orderId, Integer userId, String customerNo, Integer securityType,
                           String securityCode, String securityExchange, String securityName, Integer exchangeType,
                           BigDecimal entrustPrice, Integer entrustNumber, Integer orderType, Integer entrustState,
                           String entrustMessage, Integer entrustCode) {
        this.entrustId = entrustId;
        this.orderId = orderId;
        this.userId = userId;
        this.customerNo = customerNo;
        this.securityType = securityType;
        this.securityCode = securityCode;
        this.securityExchange = securityExchange;
        this.securityName = securityName;
        this.exchangeType = exchangeType;
        this.entrustPrice = entrustPrice;
        this.entrustNumber = entrustNumber;
        this.orderType = orderType;
        this.entrustState = entrustState;
        this.entrustMessage = entrustMessage;
        this.entrustCode = entrustCode;
    }

    public Long getEntrustId() {
        return entrustId;
    }

    public void setEntrustId(Long entrustId) {
        this.entrustId = entrustId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

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

    public Integer getExchangeType() {
        return exchangeType;
    }

    public void setExchangeType(Integer exchangeType) {
        this.exchangeType = exchangeType;
    }

    public BigDecimal getEntrustPrice() {
        return entrustPrice;
    }

    public void setEntrustPrice(BigDecimal entrustPrice) {
        this.entrustPrice = entrustPrice;
    }

    public Integer getEntrustNumber() {
        return entrustNumber;
    }

    public void setEntrustNumber(Integer entrustNumber) {
        this.entrustNumber = entrustNumber;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public Integer getEntrustState() {
        return entrustState;
    }

    public void setEntrustState(Integer entrustState) {
        this.entrustState = entrustState;
    }

    public String getEntrustMessage() {
        return entrustMessage;
    }

    public void setEntrustMessage(String entrustMessage) {
        this.entrustMessage = entrustMessage;
    }

    public Integer getEntrustCode() {
        return entrustCode;
    }

    public void setEntrustCode(Integer entrustCode) {
        this.entrustCode = entrustCode;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("entrustId", entrustId)
                .add("orderId", orderId)
                .add("userId", userId)
                .add("customerNo", customerNo)
                .add("securityType", securityType)
                .add("securityCode", securityCode)
                .add("securityExchange", securityExchange)
                .add("securityName", securityName)
                .add("exchangeType", exchangeType)
                .add("entrustPrice", entrustPrice)
                .add("entrustNumber", entrustNumber)
                .add("orderType", orderType)
                .add("entrustState", entrustState)
                .add("entrustMessage", entrustMessage)
                .add("entrustCode", entrustCode)
                .toString();
    }
}
