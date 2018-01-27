package me.caosh.condition.infrastructure.repository.model;

import com.google.common.base.MoreObjects;
import me.caosh.autoasm.FieldMapping;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by caosh on 2017/8/14.
 *
 * @author caoshuhao@touker.com
 */
@Entity
@Table(name = "condition_order", schema = "test", catalog = "")
public class ConditionOrderDO {
    private Long orderId;
    @FieldMapping(mappedProperty = "customer.userId")
    private Integer userId;
    @FieldMapping(mappedProperty = "customer.customerNo")
    private String customerNo;
    @FieldMapping(value = "false")
    private Boolean deleted;
    @FieldMapping(mappedProperty = "strategyState")
    private Integer orderState;
    @FieldMapping(mappedProperty = "securityInfo.type")
    private Integer securityType;
    @FieldMapping(mappedProperty = "securityInfo.code")
    private String securityCode;
    @FieldMapping(mappedProperty = "securityInfo.exchange")
    private String securityExchange;
    @FieldMapping(mappedProperty = "securityInfo.name")
    private String securityName;
    @FieldMapping(mappedProperty = "strategyInfo.strategyTemplateId")
    private Integer strategyId;
    @FieldMapping(mappedProperty = "rawCondition")
    private ConditionDO conditionPropertiesObj;
    @FieldMapping(mappedProperty = "rawCondition")
    private DynamicPropertiesDO dynamicPropertiesObj;
    @FieldMapping(mappedProperty = "tradePlan.exchangeType", defaultValue = "0")
    private Integer exchangeType;
    @FieldMapping(mappedProperty = "tradePlan.entrustStrategy")
    private Integer entrustStrategy;
    @FieldMapping(mappedProperty = "tradePlan.tradeNumber.entrustMethod")
    private Integer entrustMethod;
    @FieldMapping(mappedProperty = "tradePlan.tradeNumber.number")
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
    @Column(name = "is_deleted")
    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
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
    @Column(name = "strategy_id")
    public Integer getStrategyId() {
        return strategyId;
    }

    public void setStrategyId(Integer strategyId) {
        this.strategyId = strategyId;
    }

    @Transient
    public ConditionDO getConditionPropertiesObj() {
        return conditionPropertiesObj;
    }

    public void setConditionPropertiesObj(ConditionDO conditionPropertiesObj) {
        this.conditionPropertiesObj = conditionPropertiesObj;
    }

    @Transient
    public DynamicPropertiesDO getDynamicPropertiesObj() {
        return dynamicPropertiesObj;
    }

    public void setDynamicPropertiesObj(DynamicPropertiesDO dynamicPropertiesObj) {
        this.dynamicPropertiesObj = dynamicPropertiesObj;
    }

    @Basic
    @Column(name = "condition_properties")
    public String getConditionProperties() {
        return ConditionOrderDOGSONUtils.getGSON().toJson(conditionPropertiesObj);
    }

    public void setConditionProperties(String conditionProperties) {
        this.conditionPropertiesObj = ConditionOrderDOGSONUtils.getGSON().fromJson(conditionProperties, ConditionDO.class);
    }

    @Basic
    @Column(name = "dynamic_properties")
    public String getDynamicProperties() {
        return dynamicPropertiesObj != null ? ConditionOrderDOGSONUtils.getGSON().toJson(dynamicPropertiesObj) : null;
    }

    public void setDynamicProperties(String dynamicProperties) {
        this.dynamicPropertiesObj = ConditionOrderDOGSONUtils.getGSON().fromJson(dynamicProperties, DynamicPropertiesDO.class);
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
    @Column(name = "entrust_method")
    public Integer getEntrustMethod() {
        return entrustMethod;
    }

    public void setEntrustMethod(Integer entrustMethod) {
        this.entrustMethod = entrustMethod;
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
        if (o == null || getClass() != o.getClass()) return false;

        ConditionOrderDO that = (ConditionOrderDO) o;

        if (orderId != null ? !orderId.equals(that.orderId) : that.orderId != null) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (customerNo != null ? !customerNo.equals(that.customerNo) : that.customerNo != null) return false;
        if (deleted != null ? !deleted.equals(that.deleted) : that.deleted != null) return false;
        if (orderState != null ? !orderState.equals(that.orderState) : that.orderState != null) return false;
        if (securityType != null ? !securityType.equals(that.securityType) : that.securityType != null) return false;
        if (securityCode != null ? !securityCode.equals(that.securityCode) : that.securityCode != null) return false;
        if (securityExchange != null ? !securityExchange.equals(that.securityExchange) : that.securityExchange != null)
            return false;
        if (securityName != null ? !securityName.equals(that.securityName) : that.securityName != null) return false;
        if (strategyId != null ? !strategyId.equals(that.strategyId) : that.strategyId != null) return false;
        if (conditionPropertiesObj != null ? !conditionPropertiesObj.equals(that.conditionPropertiesObj) : that.conditionPropertiesObj != null)
            return false;
        if (dynamicPropertiesObj != null ? !dynamicPropertiesObj.equals(that.dynamicPropertiesObj) : that.dynamicPropertiesObj != null)
            return false;
        if (exchangeType != null ? !exchangeType.equals(that.exchangeType) : that.exchangeType != null) return false;
        if (entrustStrategy != null ? !entrustStrategy.equals(that.entrustStrategy) : that.entrustStrategy != null)
            return false;
        if (entrustMethod != null ? !entrustMethod.equals(that.entrustMethod) : that.entrustMethod != null)
            return false;
        if (entrustAmount != null ? !entrustAmount.equals(that.entrustAmount) : that.entrustAmount != null)
            return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        return updateTime != null ? updateTime.equals(that.updateTime) : that.updateTime == null;
    }

    @Override
    public int hashCode() {
        int result = orderId != null ? orderId.hashCode() : 0;
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (customerNo != null ? customerNo.hashCode() : 0);
        result = 31 * result + (deleted != null ? deleted.hashCode() : 0);
        result = 31 * result + (orderState != null ? orderState.hashCode() : 0);
        result = 31 * result + (securityType != null ? securityType.hashCode() : 0);
        result = 31 * result + (securityCode != null ? securityCode.hashCode() : 0);
        result = 31 * result + (securityExchange != null ? securityExchange.hashCode() : 0);
        result = 31 * result + (securityName != null ? securityName.hashCode() : 0);
        result = 31 * result + (strategyId != null ? strategyId.hashCode() : 0);
        result = 31 * result + (conditionPropertiesObj != null ? conditionPropertiesObj.hashCode() : 0);
        result = 31 * result + (dynamicPropertiesObj != null ? dynamicPropertiesObj.hashCode() : 0);
        result = 31 * result + (exchangeType != null ? exchangeType.hashCode() : 0);
        result = 31 * result + (entrustStrategy != null ? entrustStrategy.hashCode() : 0);
        result = 31 * result + (entrustMethod != null ? entrustMethod.hashCode() : 0);
        result = 31 * result + (entrustAmount != null ? entrustAmount.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (updateTime != null ? updateTime.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(ConditionOrderDO.class).omitNullValues()
                .add("orderId", orderId)
                .add("userId", userId)
                .add("customerNo", customerNo)
                .add("deleted", deleted)
                .add("orderState", orderState)
                .add("securityType", securityType)
                .add("securityCode", securityCode)
                .add("securityExchange", securityExchange)
                .add("securityName", securityName)
                .add("strategyId", strategyId)
                .add("conditionPropertiesObj", conditionPropertiesObj)
                .add("dynamicPropertiesObj", dynamicPropertiesObj)
                .add("exchangeType", exchangeType)
                .add("entrustStrategy", entrustStrategy)
                .add("entrustMethod", entrustMethod)
                .add("entrustAmount", entrustAmount)
                .add("createTime", createTime)
                .add("updateTime", updateTime)
                .toString();
    }
}
