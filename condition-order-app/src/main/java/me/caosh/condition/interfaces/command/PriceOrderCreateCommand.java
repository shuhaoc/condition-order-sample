package me.caosh.condition.interfaces.command;

import com.google.common.base.MoreObjects;
import me.caosh.autoasm.FieldMapping;
import me.caosh.condition.domain.dto.market.SecurityInfoDTO;
import me.caosh.condition.domain.dto.order.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2017/8/9
 */
public class PriceOrderCreateCommand implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull
    private SecurityInfoDTO securityInfo;

    @NotNull
    @FieldMapping(mappedProperty = "rawCondition")
    private PriceConditionDTO priceCondition;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Future
    private Date expireTime;

    @NotNull
    private TradePlanDTO tradePlan;

    @Valid
    private DelayConfirmParamDTO delayConfirmParam;

    @Valid
    private MonitorTimeRangeDTO monitorTimeRange;

    @Valid
    private DeviationCtrlParamDTO deviationCtrlParam;

    public SecurityInfoDTO getSecurityInfo() {
        return securityInfo;
    }

    public void setSecurityInfo(SecurityInfoDTO securityInfo) {
        this.securityInfo = securityInfo;
    }

    public PriceConditionDTO getPriceCondition() {
        return priceCondition;
    }

    public void setPriceCondition(PriceConditionDTO priceCondition) {
        this.priceCondition = priceCondition;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
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
        return MoreObjects.toStringHelper(PriceOrderCreateCommand.class).omitNullValues()
                          .add("securityInfo", securityInfo)
                          .add("priceCondition", priceCondition)
                          .add("expireTime", expireTime)
                          .add("tradePlan", tradePlan)
                          .add("delayConfirmParam", delayConfirmParam)
                          .add("monitorTimeRange", monitorTimeRange)
                          .add("deviationCtrlParam", deviationCtrlParam)
                          .toString();
    }
}
