package me.caosh.condition.infrastructure.repository.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by caosh on 2017/8/9.
 */
@Entity
@Table(name = "condition_order", schema = "", catalog = "test")
public class ConditionOrderDO {
    private Long orderId;
    private Integer orderState;
    private Integer securityType;
    private String securityCode;
    private String securityExchange;
    private String securityName;
    private Integer strategyId;
    private String conditionProperties;
    private String dynamicProperties;
    private Integer exchangeType;
    private Integer entrustStrategy;
    private BigDecimal entrustAmount;
    private Timestamp createTime;
    private Timestamp updateTime;

    @Id
    @Column(name = "order_id")
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
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
    @Column(name = "order_state")
    public Integer getOrderState() {
        return orderState;
    }

    public void setOrderState(Integer orderState) {
        this.orderState = orderState;
    }

    @Basic
    @Column(name = "strategy_id")
    public Integer getStrategyId() {
        return strategyId;
    }

    public void setStrategyId(Integer strategyId) {
        this.strategyId = strategyId;
    }

    @Basic
    @Column(name = "condition_properties")
    public String getConditionProperties() {
        return conditionProperties;
    }

    public void setConditionProperties(String conditionProperties) {
        this.conditionProperties = conditionProperties;
    }

    @Basic
    @Column(name = "dynamic_properties")
    public String getDynamicProperties() {
        return dynamicProperties;
    }

    public void setDynamicProperties(String dynamicProperties) {
        this.dynamicProperties = dynamicProperties;
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
    @Column(name = "entrust_strategy")
    public Integer getEntrustStrategy() {
        return entrustStrategy;
    }

    public void setEntrustStrategy(Integer entrustStrategy) {
        this.entrustStrategy = entrustStrategy;
    }

    @Basic
    @Column(name = "entrust_amount")
    public BigDecimal getEntrustAmount() {
        return entrustAmount;
    }

    public void setEntrustAmount(BigDecimal entrustAmount) {
        this.entrustAmount = entrustAmount;
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
        if (!(o instanceof ConditionOrderDO)) return false;

        ConditionOrderDO that = (ConditionOrderDO) o;

        if (orderId != null ? !orderId.equals(that.orderId) : that.orderId != null) return false;
        if (orderState != null ? !orderState.equals(that.orderState) : that.orderState != null) return false;
        if (securityType != null ? !securityType.equals(that.securityType) : that.securityType != null) return false;
        if (securityCode != null ? !securityCode.equals(that.securityCode) : that.securityCode != null) return false;
        if (securityExchange != null ? !securityExchange.equals(that.securityExchange) : that.securityExchange != null)
            return false;
        if (securityName != null ? !securityName.equals(that.securityName) : that.securityName != null) return false;
        if (strategyId != null ? !strategyId.equals(that.strategyId) : that.strategyId != null) return false;
        if (conditionProperties != null ? !conditionProperties.equals(that.conditionProperties) : that.conditionProperties != null)
            return false;
        if (dynamicProperties != null ? !dynamicProperties.equals(that.dynamicProperties) : that.dynamicProperties != null)
            return false;
        if (exchangeType != null ? !exchangeType.equals(that.exchangeType) : that.exchangeType != null) return false;
        if (entrustStrategy != null ? !entrustStrategy.equals(that.entrustStrategy) : that.entrustStrategy != null)
            return false;
        if (entrustAmount != null ? !entrustAmount.equals(that.entrustAmount) : that.entrustAmount != null)
            return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        return !(updateTime != null ? !updateTime.equals(that.updateTime) : that.updateTime != null);

    }

    @Override
    public int hashCode() {
        int result = orderId != null ? orderId.hashCode() : 0;
        result = 31 * result + (orderState != null ? orderState.hashCode() : 0);
        result = 31 * result + (securityType != null ? securityType.hashCode() : 0);
        result = 31 * result + (securityCode != null ? securityCode.hashCode() : 0);
        result = 31 * result + (securityExchange != null ? securityExchange.hashCode() : 0);
        result = 31 * result + (securityName != null ? securityName.hashCode() : 0);
        result = 31 * result + (strategyId != null ? strategyId.hashCode() : 0);
        result = 31 * result + (conditionProperties != null ? conditionProperties.hashCode() : 0);
        result = 31 * result + (dynamicProperties != null ? dynamicProperties.hashCode() : 0);
        result = 31 * result + (exchangeType != null ? exchangeType.hashCode() : 0);
        result = 31 * result + (entrustStrategy != null ? entrustStrategy.hashCode() : 0);
        result = 31 * result + (entrustAmount != null ? entrustAmount.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (updateTime != null ? updateTime.hashCode() : 0);
        return result;
    }
}
