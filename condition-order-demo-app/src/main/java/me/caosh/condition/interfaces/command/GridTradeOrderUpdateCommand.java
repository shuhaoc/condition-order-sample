package me.caosh.condition.interfaces.command;

import com.google.common.base.MoreObjects;
import hbec.commons.domain.intellitrade.conditionorder.DelayConfirmDTO;
import hbec.commons.domain.intellitrade.conditionorder.DeviationCtrlDTO;
import hbec.commons.domain.intellitrade.conditionorder.MonitorTimeRangeDTO;
import me.caosh.autoasm.FieldMapping;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by caosh on 2017/8/9.
 */
public class GridTradeOrderUpdateCommand implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull
    private Long orderId;

    @NotNull
    @Valid
    private GridConditionCommandDTO condition;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Future
    private Date expireTime;

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

    public GridConditionCommandDTO getCondition() {
        return condition;
    }

    public void setCondition(GridConditionCommandDTO condition) {
        this.condition = condition;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
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
        return MoreObjects.toStringHelper(GridTradeOrderUpdateCommand.class).omitNullValues()
                .add("orderId", orderId)
                .add("condition", condition)
                .add("expireTime", expireTime)
                .add("monitorTimeRange", monitorTimeRange)
                .add("delayConfirm", delayConfirm)
                .add("deviationCtrl", deviationCtrl)
                .toString();
    }
}
