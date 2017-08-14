package me.caosh.condition.infrastructure.repository.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by caosh on 2017/8/14.
 *
 * @author caoshuhao@touker.com
 */
@Entity
@Table(name = "entrust_order", schema = "test", catalog = "")
public class EntrustOrderDO {
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
    private Timestamp createTime;
    private Timestamp updateTime;

    @Id
    @Column(name = "entrust_id")
    public Long getEntrustId() {
        return entrustId;
    }

    public void setEntrustId(Long entrustId) {
        this.entrustId = entrustId;
    }

    @Basic
    @Column(name = "order_id")
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    @Basic
    @Column(name = "user_id")
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "customer_no")
    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    @Basic
    @Column(name = "security_type")
    public Integer getSecurityType() {
        return securityType;
    }

    public void setSecurityType(Integer securityType) {
        this.securityType = securityType;
    }

    @Basic
    @Column(name = "security_code")
    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    @Basic
    @Column(name = "security_exchange")
    public String getSecurityExchange() {
        return securityExchange;
    }

    public void setSecurityExchange(String securityExchange) {
        this.securityExchange = securityExchange;
    }

    @Basic
    @Column(name = "security_name")
    public String getSecurityName() {
        return securityName;
    }

    public void setSecurityName(String securityName) {
        this.securityName = securityName;
    }

    @Basic
    @Column(name = "exchange_type")
    public Integer getExchangeType() {
        return exchangeType;
    }

    public void setExchangeType(Integer exchangeType) {
        this.exchangeType = exchangeType;
    }

    @Basic
    @Column(name = "entrust_price")
    public BigDecimal getEntrustPrice() {
        return entrustPrice;
    }

    public void setEntrustPrice(BigDecimal entrustPrice) {
        this.entrustPrice = entrustPrice;
    }

    @Basic
    @Column(name = "entrust_number")
    public Integer getEntrustNumber() {
        return entrustNumber;
    }

    public void setEntrustNumber(Integer entrustNumber) {
        this.entrustNumber = entrustNumber;
    }

    @Basic
    @Column(name = "order_type")
    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    @Basic
    @Column(name = "entrust_state")
    public Integer getEntrustState() {
        return entrustState;
    }

    public void setEntrustState(Integer entrustState) {
        this.entrustState = entrustState;
    }

    @Basic
    @Column(name = "entrust_message")
    public String getEntrustMessage() {
        return entrustMessage;
    }

    public void setEntrustMessage(String entrustMessage) {
        this.entrustMessage = entrustMessage;
    }

    @Basic
    @Column(name = "entrust_code")
    public Integer getEntrustCode() {
        return entrustCode;
    }

    public void setEntrustCode(Integer entrustCode) {
        this.entrustCode = entrustCode;
    }

    @Basic
    @Column(name = "create_time")
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "update_time")
    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EntrustOrderDO that = (EntrustOrderDO) o;

        if (entrustId != null ? !entrustId.equals(that.entrustId) : that.entrustId != null) return false;
        if (orderId != null ? !orderId.equals(that.orderId) : that.orderId != null) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (customerNo != null ? !customerNo.equals(that.customerNo) : that.customerNo != null) return false;
        if (securityType != null ? !securityType.equals(that.securityType) : that.securityType != null) return false;
        if (securityCode != null ? !securityCode.equals(that.securityCode) : that.securityCode != null) return false;
        if (securityExchange != null ? !securityExchange.equals(that.securityExchange) : that.securityExchange != null)
            return false;
        if (securityName != null ? !securityName.equals(that.securityName) : that.securityName != null) return false;
        if (exchangeType != null ? !exchangeType.equals(that.exchangeType) : that.exchangeType != null) return false;
        if (entrustPrice != null ? !entrustPrice.equals(that.entrustPrice) : that.entrustPrice != null) return false;
        if (entrustNumber != null ? !entrustNumber.equals(that.entrustNumber) : that.entrustNumber != null)
            return false;
        if (entrustState != null ? !entrustState.equals(that.entrustState) : that.entrustState != null) return false;
        if (entrustMessage != null ? !entrustMessage.equals(that.entrustMessage) : that.entrustMessage != null)
            return false;
        if (entrustCode != null ? !entrustCode.equals(that.entrustCode) : that.entrustCode != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (updateTime != null ? !updateTime.equals(that.updateTime) : that.updateTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = entrustId != null ? entrustId.hashCode() : 0;
        result = 31 * result + (orderId != null ? orderId.hashCode() : 0);
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (customerNo != null ? customerNo.hashCode() : 0);
        result = 31 * result + (securityType != null ? securityType.hashCode() : 0);
        result = 31 * result + (securityCode != null ? securityCode.hashCode() : 0);
        result = 31 * result + (securityExchange != null ? securityExchange.hashCode() : 0);
        result = 31 * result + (securityName != null ? securityName.hashCode() : 0);
        result = 31 * result + (exchangeType != null ? exchangeType.hashCode() : 0);
        result = 31 * result + (entrustPrice != null ? entrustPrice.hashCode() : 0);
        result = 31 * result + (entrustNumber != null ? entrustNumber.hashCode() : 0);
        result = 31 * result + (entrustState != null ? entrustState.hashCode() : 0);
        result = 31 * result + (entrustMessage != null ? entrustMessage.hashCode() : 0);
        result = 31 * result + (entrustCode != null ? entrustCode.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (updateTime != null ? updateTime.hashCode() : 0);
        return result;
    }
}
