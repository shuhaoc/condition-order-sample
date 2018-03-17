package me.caosh.condition.infrastructure.tunnel.model;

import com.google.common.base.MoreObjects;
import me.caosh.autoasm.FieldMapping;
import org.joda.time.LocalTime;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

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
    @FieldMapping(mappedProperty = "orderState")
    private Integer orderState;
    @FieldMapping(mappedProperty = "securityInfo.type")
    private Integer securityType;
    @FieldMapping(mappedProperty = "securityInfo.code")
    private String securityCode;
    @FieldMapping(mappedProperty = "securityInfo.exchange")
    private String securityExchange;
    @FieldMapping(mappedProperty = "securityInfo.name")
    private String securityName;
    private Integer trackIndexOption = 0;
    @FieldMapping(mappedProperty = "trackedIndexInfo.code")
    private String trackedIndexCode;
    @FieldMapping(mappedProperty = "trackedIndexInfo.source")
    private String trackedIndexSource;
    @FieldMapping(mappedProperty = "trackedIndexInfo.name")
    private String trackedIndexName;
    @FieldMapping(mappedProperty = "strategyInfo.strategyType")
    private Integer strategyType;
    @FieldMapping(mappedProperty = "rawCondition")
    private ConditionDO conditionPropertiesObj;
    @FieldMapping(mappedProperty = "rawCondition")
    private DynamicPropertiesDO dynamicPropertiesObj;
    private Date expireTime;
    @FieldMapping(mappedProperty = "tradePlan.exchangeType")
    private Integer exchangeType;
    @FieldMapping(mappedProperty = "tradePlan.entrustStrategy")
    private Integer entrustStrategy;
    @FieldMapping(mappedProperty = "tradePlan.tradeNumber.entrustMethod")
    private Integer entrustMethod;
    @FieldMapping(mappedProperty = "tradePlan.tradeNumber.number")
    private BigDecimal entrustAmount;
    @FieldMapping(mappedProperty = "delayConfirmParam.option")
    private Integer delayConfirmOption = 0;
    @FieldMapping(mappedProperty = "delayConfirmParam.confirmTimes")
    private Integer delayConfirmTimes = 0;
    @FieldMapping(mappedProperty = "monitorTimeRange.option")
    private Integer monitorTimeRangeOption = 0;
    @FieldMapping(mappedProperty = "monitorTimeRange.weekRange.beginWeek")
    private Integer beginWeek = 1;
    @FieldMapping(mappedProperty = "monitorTimeRange.weekRange.endWeek")
    private Integer endWeek = 5;
    @FieldMapping(mappedProperty = "monitorTimeRange.localTimeRange.beginTime")
    private Date beginTime = LocalTime.MIDNIGHT.toDateTimeToday().toDate();
    @FieldMapping(mappedProperty = "monitorTimeRange.localTimeRange.endTime")
    private Date endTime = LocalTime.MIDNIGHT.toDateTimeToday().toDate();
    @FieldMapping(mappedProperty = "deviationCtrlParam.option")
    private Integer deviationCtrlOption = 0;
    @FieldMapping(mappedProperty = "deviationCtrlParam.limitPercent")
    private BigDecimal deviationLimitPercent = BigDecimal.ZERO;
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
    @Column(name = "track_index_option")
    public Integer getTrackIndexOption() {
        return trackIndexOption;
    }

    public void setTrackIndexOption(Integer trackIndexOption) {
        this.trackIndexOption = trackIndexOption;
    }

    @Basic
    @Column(name = "tracked_index_code")
    public String getTrackedIndexCode() {
        return trackedIndexCode;
    }

    public void setTrackedIndexCode(String trackedIndexCode) {
        this.trackedIndexCode = trackedIndexCode;
    }

    @Basic
    @Column(name = "tracked_index_source")
    public String getTrackedIndexSource() {
        return trackedIndexSource;
    }

    public void setTrackedIndexSource(String trackedIndexSource) {
        this.trackedIndexSource = trackedIndexSource;
    }

    @Basic
    @Column(name = "tracked_index_name")
    public String getTrackedIndexName() {
        return trackedIndexName;
    }

    public void setTrackedIndexName(String trackedIndexName) {
        this.trackedIndexName = trackedIndexName;
    }

    @Basic
    @Column(name = "strategy_type")
    public Integer getStrategyType() {
        return strategyType;
    }

    public void setStrategyType(Integer strategyType) {
        this.strategyType = strategyType;
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
        this.conditionPropertiesObj = ConditionOrderDOGSONUtils.getGSON()
                                                               .fromJson(conditionProperties, ConditionDO.class);
    }

    @Basic
    @Column(name = "dynamic_properties")
    public String getDynamicProperties() {
        return dynamicPropertiesObj != null ? ConditionOrderDOGSONUtils.getGSON().toJson(dynamicPropertiesObj) : null;
    }

    public void setDynamicProperties(String dynamicProperties) {
        this.dynamicPropertiesObj = ConditionOrderDOGSONUtils.getGSON()
                                                             .fromJson(dynamicProperties, DynamicPropertiesDO.class);
    }

    @Basic
    @Column(name = "expire_time")
    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
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
    @Column(name = "delay_confirm_option")
    public Integer getDelayConfirmOption() {
        return delayConfirmOption;
    }

    public void setDelayConfirmOption(Integer delayConfirmOption) {
        this.delayConfirmOption = delayConfirmOption;
    }

    @Basic
    @Column(name = "delay_confirm_times")
    public Integer getDelayConfirmTimes() {
        return delayConfirmTimes;
    }

    public void setDelayConfirmTimes(Integer delayConfirmTimes) {
        this.delayConfirmTimes = delayConfirmTimes;
    }

    @Basic
    @Column(name = "monitor_time_range_option")
    public Integer getMonitorTimeRangeOption() {
        return monitorTimeRangeOption;
    }

    public void setMonitorTimeRangeOption(Integer monitorTimeRangeOption) {
        this.monitorTimeRangeOption = monitorTimeRangeOption;
    }

    @Basic
    @Column(name = "begin_week")
    public Integer getBeginWeek() {
        return beginWeek;
    }

    public void setBeginWeek(Integer beginWeek) {
        this.beginWeek = beginWeek;
    }

    @Basic
    @Column(name = "end_week")
    public Integer getEndWeek() {
        return endWeek;
    }

    public void setEndWeek(Integer endWeek) {
        this.endWeek = endWeek;
    }

    @Basic
    @Column(name = "begin_time")
    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    @Basic
    @Column(name = "end_time")
    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Basic
    @Column(name = "deviation_ctrl_option")
    public Integer getDeviationCtrlOption() {
        return deviationCtrlOption;
    }

    public void setDeviationCtrlOption(Integer deviationCtrlOption) {
        this.deviationCtrlOption = deviationCtrlOption;
    }

    @Basic
    @Column(name = "deviation_limit_percent")
    public BigDecimal getDeviationLimitPercent() {
        return deviationLimitPercent;
    }

    public void setDeviationLimitPercent(BigDecimal deviationLimitPercent) {
        this.deviationLimitPercent = deviationLimitPercent;
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
                          .add("trackIndexOption", trackIndexOption)
                          .add("trackedIndexCode", trackedIndexCode)
                          .add("trackedIndexSource", trackedIndexSource)
                          .add("trackedIndexName", trackedIndexName)
                          .add("strategyType", strategyType)
                          .add("conditionPropertiesObj", conditionPropertiesObj)
                          .add("dynamicPropertiesObj", dynamicPropertiesObj)
                          .add("expireTime", expireTime)
                          .add("exchangeType", exchangeType)
                          .add("entrustStrategy", entrustStrategy)
                          .add("entrustMethod", entrustMethod)
                          .add("entrustAmount", entrustAmount)
                          .add("delayConfirmOption", delayConfirmOption)
                          .add("delayConfirmTimes", delayConfirmTimes)
                          .add("monitorTimeRangeOption", monitorTimeRangeOption)
                          .add("beginWeek", beginWeek)
                          .add("endWeek", endWeek)
                          .add("beginTime", beginTime)
                          .add("endTime", endTime)
                          .add("deviationCtrlOption", deviationCtrlOption)
                          .add("deviationLimitPercent", deviationLimitPercent)
                          .add("createTime", createTime)
                          .add("updateTime", updateTime)
                          .add("conditionProperties", getConditionProperties())
                          .add("dynamicProperties", getDynamicProperties())
                          .toString();
    }
}
