package me.caosh.condition.interfaces.command;

import com.google.common.base.MoreObjects;
import me.caosh.condition.domain.dto.market.TrackedIndexDTO;
import me.caosh.condition.domain.dto.order.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by caosh on 2017/8/9.
 */
public class PriceOrderUpdateCommand implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull
    private Long orderId;

    @Valid
    private TrackedIndexDTO trackedIndexInfo;

    @NotNull
    private PriceConditionDTO priceCondition;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Future
    private Date expireTime;

    @NotNull
    private TradePlanDTO tradePlan;

    @Valid
    private DelayConfirmDTO delayConfirm;

    @Valid
    private MonitorTimeRangeDTO monitorTimeRange;

    @Valid
    private DeviationCtrlDTO deviationCtrl;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public TrackedIndexDTO getTrackedIndexInfo() {
        return trackedIndexInfo;
    }

    public void setTrackedIndexInfo(TrackedIndexDTO trackedIndexInfo) {
        this.trackedIndexInfo = trackedIndexInfo;
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

    public DelayConfirmDTO getDelayConfirm() {
        return delayConfirm;
    }

    public void setDelayConfirm(DelayConfirmDTO delayConfirm) {
        this.delayConfirm = delayConfirm;
    }

    public MonitorTimeRangeDTO getMonitorTimeRange() {
        return monitorTimeRange;
    }

    public void setMonitorTimeRange(MonitorTimeRangeDTO monitorTimeRange) {
        this.monitorTimeRange = monitorTimeRange;
    }

    public DeviationCtrlDTO getDeviationCtrl() {
        return deviationCtrl;
    }

    public void setDeviationCtrl(DeviationCtrlDTO deviationCtrl) {
        this.deviationCtrl = deviationCtrl;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(PriceOrderUpdateCommand.class).omitNullValues()
                          .add("orderId", orderId)
                          .add("trackedIndexInfo", trackedIndexInfo)
                          .add("priceCondition", priceCondition)
                          .add("expireTime", expireTime)
                          .add("tradePlan", tradePlan)
                          .add("delayConfirm", delayConfirm)
                          .add("monitorTimeRange", monitorTimeRange)
                          .add("deviationCtrl", deviationCtrl)
                          .toString();
    }
}
