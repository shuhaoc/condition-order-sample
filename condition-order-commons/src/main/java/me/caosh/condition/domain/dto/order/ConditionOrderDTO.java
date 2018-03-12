package me.caosh.condition.domain.dto.order;

import com.google.common.base.MoreObjects;
import me.caosh.autoasm.Convertible;
import me.caosh.autoasm.FieldMapping;
import me.caosh.condition.domain.dto.market.SecurityInfoDTO;

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
    @FieldMapping(mappedProperty = "strategyInfo.strategyType")
    private Integer strategyType;
    private ConditionDTO rawCondition;
    private String expireTime;
    private TradePlanDTO tradePlan;
    private DelayConfirmParamDTO delayConfirmParam;

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

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(ConditionOrderDTO.class).omitNullValues()
                .add("orderId", orderId)
                .add("customer", customer)
                .add("deleted", deleted)
                .add("orderState", orderState)
                .add("securityInfo", securityInfo)
                .add("strategyType", strategyType)
                .add("rawCondition", rawCondition)
                .add("expireTime", expireTime)
                .add("tradePlan", tradePlan)
                .add("delayConfirmParam", delayConfirmParam)
                .toString();
    }
}
