package me.caosh.condition.infrastructure.repository.model;

import com.google.common.base.MoreObjects;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by caosh on 2017/8/3.
 */
@Entity
@Table(name = "condition_order")
public class ConditionOrderDO {
    private Integer orderId;
    private Integer securityType;
    private String securityCode;
    private String securityExchange;
    private String securityName;
    private Integer orderState;
    private String inputArguments;
    private String dynamicProperties;
    private Timestamp gmtCreate;
    private Timestamp gmtModify;

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
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
    @Column(name = "input_arguments")
    public String getInputArguments() {
        return inputArguments;
    }

    public void setInputArguments(String inputArguments) {
        this.inputArguments = inputArguments;
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
    @Column(name = "gmt_create")
    public Timestamp getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Timestamp gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    @Basic
    @Column(name = "gmt_modify")
    public Timestamp getGmtModify() {
        return gmtModify;
    }

    public void setGmtModify(Timestamp gmtModify) {
        this.gmtModify = gmtModify;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ConditionOrderDO that = (ConditionOrderDO) o;

        if (orderId != null ? !orderId.equals(that.orderId) : that.orderId != null) return false;
        if (securityType != null ? !securityType.equals(that.securityType) : that.securityType != null) return false;
        if (securityCode != null ? !securityCode.equals(that.securityCode) : that.securityCode != null) return false;
        if (securityExchange != null ? !securityExchange.equals(that.securityExchange) : that.securityExchange != null)
            return false;
        if (securityName != null ? !securityName.equals(that.securityName) : that.securityName != null) return false;
        if (orderState != null ? !orderState.equals(that.orderState) : that.orderState != null) return false;
        if (inputArguments != null ? !inputArguments.equals(that.inputArguments) : that.inputArguments != null)
            return false;
        if (dynamicProperties != null ? !dynamicProperties.equals(that.dynamicProperties) : that.dynamicProperties != null)
            return false;
        if (gmtCreate != null ? !gmtCreate.equals(that.gmtCreate) : that.gmtCreate != null) return false;
        if (gmtModify != null ? !gmtModify.equals(that.gmtModify) : that.gmtModify != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = orderId != null ? orderId.hashCode() : 0;
        result = 31 * result + (securityType != null ? securityType.hashCode() : 0);
        result = 31 * result + (securityCode != null ? securityCode.hashCode() : 0);
        result = 31 * result + (securityExchange != null ? securityExchange.hashCode() : 0);
        result = 31 * result + (securityName != null ? securityName.hashCode() : 0);
        result = 31 * result + (orderState != null ? orderState.hashCode() : 0);
        result = 31 * result + (inputArguments != null ? inputArguments.hashCode() : 0);
        result = 31 * result + (dynamicProperties != null ? dynamicProperties.hashCode() : 0);
        result = 31 * result + (gmtCreate != null ? gmtCreate.hashCode() : 0);
        result = 31 * result + (gmtModify != null ? gmtModify.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("orderId", orderId)
                .add("securityType", securityType)
                .add("securityCode", securityCode)
                .add("securityExchange", securityExchange)
                .add("securityName", securityName)
                .add("orderState", orderState)
                .add("inputArguments", inputArguments)
                .add("dynamicProperties", dynamicProperties)
                .add("gmtCreate", gmtCreate)
                .add("gmtModify", gmtModify)
                .toString();
    }
}
