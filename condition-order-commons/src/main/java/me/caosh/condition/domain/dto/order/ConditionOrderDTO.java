package me.caosh.condition.domain.dto.order;

import com.google.common.base.MoreObjects;
import me.caosh.autoasm.Convertible;
import me.caosh.autoasm.FieldMapping;
import me.caosh.condition.domain.dto.market.SecurityInfoDTO;
import me.caosh.condition.domain.dto.market.TrackedIndexDTO;

import java.io.Serializable;

/**
 * Created by caosh on 2017/8/11.
 *
 * @author caoshuhao@touker.com
 */
@Convertible
public class ConditionOrderDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long orderId;
    private TradeCustomerInfoDTO customer;
    private Boolean deleted = false;
    private Integer orderState;
    private SecurityInfoDTO securityInfo;
    private TrackedIndexDTO trackedIndex;
    @FieldMapping(mappedProperty = "strategyInfo.strategyType")
    private Integer strategyType;
    private ConditionDTO rawCondition;
    private String expireTime;
    private TradePlanDTO tradePlan;
    private DelayConfirmParamDTO delayConfirmParam;
    private DelayConfirmCountDTO delayConfirmCount;
    private MonitorTimeRangeDTO monitorTimeRange;
    private DeviationCtrlParamDTO deviationCtrlParam;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public TradeCustomerInfoDTO getCustomer() {
        return customer;
    }

    public void setCustomer(TradeCustomerInfoDTO customer) {
        this.customer = customer;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Integer getOrderState() {
        return orderState;
    }

    public void setOrderState(Integer orderState) {
        this.orderState = orderState;
    }

    public SecurityInfoDTO getSecurityInfo() {
        return securityInfo;
    }

    public void setSecurityInfo(SecurityInfoDTO securityInfo) {
        this.securityInfo = securityInfo;
    }

    public TrackedIndexDTO getTrackedIndex() {
        return trackedIndex;
    }

    public void setTrackedIndex(TrackedIndexDTO trackedIndex) {
        this.trackedIndex = trackedIndex;
    }

    public Integer getStrategyType() {
        return strategyType;
    }

    public void setStrategyType(Integer strategyType) {
        this.strategyType = strategyType;
    }

    public ConditionDTO getRawCondition() {
        return rawCondition;
    }

    public void setRawCondition(ConditionDTO rawCondition) {
        this.rawCondition = rawCondition;
    }

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }

    public TradePlanDTO getTradePlan() {
        return tradePlan;
    }

    public void setTradePlan(TradePlanDTO tradePlan) {
        this.tradePlan = tradePlan;
    }

    public DelayConfirmParamDTO getDelayConfirmParam() {
        return delayConfirmParam;
    }

    public void setDelayConfirmParam(DelayConfirmParamDTO delayConfirmParam) {
        this.delayConfirmParam = delayConfirmParam;
    }

    public DelayConfirmCountDTO getDelayConfirmCount() {
        return delayConfirmCount;
    }

    public void setDelayConfirmCount(DelayConfirmCountDTO delayConfirmCount) {
        this.delayConfirmCount = delayConfirmCount;
    }

    public MonitorTimeRangeDTO getMonitorTimeRange() {
        return monitorTimeRange;
    }

    public void setMonitorTimeRange(MonitorTimeRangeDTO monitorTimeRange) {
        this.monitorTimeRange = monitorTimeRange;
    }

    public DeviationCtrlParamDTO getDeviationCtrlParam() {
        return deviationCtrlParam;
    }

    public void setDeviationCtrlParam(DeviationCtrlParamDTO deviationCtrlParam) {
        this.deviationCtrlParam = deviationCtrlParam;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(ConditionOrderDTO.class).omitNullValues()
                          .add("orderId", orderId)
                          .add("customer", customer)
                          .add("deleted", deleted)
                          .add("orderState", orderState)
                          .add("securityInfo", securityInfo)
                          .add("trackedIndex", trackedIndex)
                          .add("strategyType", strategyType)
                          .add("rawCondition", rawCondition)
                          .add("expireTime", expireTime)
                          .add("tradePlan", tradePlan)
                          .add("delayConfirmParam", delayConfirmParam)
                          .add("delayConfirmCount", delayConfirmCount)
                          .add("monitorTimeRange", monitorTimeRange)
                          .add("deviationCtrlParam", deviationCtrlParam)
                          .toString();
    }
}
