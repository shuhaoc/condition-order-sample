package me.caosh.condition.interfaces.command;

import com.google.common.base.MoreObjects;
import me.caosh.autoasm.FieldMapping;
import me.caosh.condition.domain.dto.market.TrackedIndexDTO;
import me.caosh.condition.domain.dto.order.DelayConfirmDTO;
import me.caosh.condition.domain.dto.order.DeviationCtrlDTO;
import me.caosh.condition.domain.dto.order.MonitorTimeRangeDTO;
import me.caosh.condition.domain.dto.order.TradePlanDTO;
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
        return MoreObjects.toStringHelper(PriceOrderUpdateCommand.class).omitNullValues()
                          .add("orderId", orderId)
                          .add("trackedIndexInfo", trackedIndexInfo)
                          .add("condition", condition)
                          .add("expireTime", expireTime)
                          .add("tradePlan", tradePlan)
                          .add("monitorTimeRange", monitorTimeRange)
                          .add("delayConfirm", delayConfirm)
                          .add("deviationCtrl", deviationCtrl)
                          .toString();
    }
}
