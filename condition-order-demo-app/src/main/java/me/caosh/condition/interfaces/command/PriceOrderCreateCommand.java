package me.caosh.condition.interfaces.command;

import com.google.common.base.MoreObjects;
import hbec.commons.domain.intellitrade.conditionorder.DelayConfirmDTO;
import hbec.commons.domain.intellitrade.conditionorder.DeviationCtrlDTO;
import hbec.commons.domain.intellitrade.conditionorder.MonitorTimeRangeDTO;
import hbec.commons.domain.intellitrade.conditionorder.TradePlanDTO;
import hbec.commons.domain.intellitrade.market.TrackedIndexDTO;
import hbec.commons.domain.intellitrade.security.SecurityInfoDTO;
import me.caosh.autoasm.FieldMapping;
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

    @Valid
    private TrackedIndexDTO trackedIndex;

    @NotNull
    @Valid
    private PriceConditionCommandDTO condition;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Future
    private Date expireTime;

    @NotNull
    @Valid
    private TradePlanDTO tradePlan;

    @Valid
    private MonitorTimeRangeDTO monitorTimeRange;

    @Valid
    @FieldMapping(mappedProperty = "condition.delayConfirm")
    private DelayConfirmDTO delayConfirm;

    @Valid
    @FieldMapping(mappedProperty = "condition.deviationCtrl")
    private DeviationCtrlDTO deviationCtrl;

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

    public PriceConditionCommandDTO getCondition() {
        return condition;
    }

    public void setCondition(PriceConditionCommandDTO condition) {
        this.condition = condition;
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

    public MonitorTimeRangeDTO getMonitorTimeRange() {
        return monitorTimeRange;
    }

    public void setMonitorTimeRange(MonitorTimeRangeDTO monitorTimeRange) {
        this.monitorTimeRange = monitorTimeRange;
    }

    public DelayConfirmDTO getDelayConfirm() {
        return delayConfirm;
    }

    public void setDelayConfirm(DelayConfirmDTO delayConfirm) {
        this.delayConfirm = delayConfirm;
    }

    public DeviationCtrlDTO getDeviationCtrl() {
        return deviationCtrl;
    }

    public void setDeviationCtrl(DeviationCtrlDTO deviationCtrl) {
        this.deviationCtrl = deviationCtrl;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(PriceOrderCreateCommand.class).omitNullValues()
                          .add("securityInfo", securityInfo)
                          .add("trackedIndex", trackedIndex)
                          .add("condition", condition)
                          .add("expireTime", expireTime)
                          .add("tradePlan", tradePlan)
                          .add("monitorTimeRange", monitorTimeRange)
                          .add("delayConfirm", delayConfirm)
                          .add("deviationCtrl", deviationCtrl)
                          .toString();
    }
}
