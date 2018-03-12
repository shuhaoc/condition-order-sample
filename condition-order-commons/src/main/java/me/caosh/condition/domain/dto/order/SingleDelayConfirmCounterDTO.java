package me.caosh.condition.domain.dto.order;

import com.google.common.base.MoreObjects;
import hbec.intellitrade.strategy.domain.condition.delayconfirm.DelayConfirmCounter;
import me.caosh.autoasm.MappedClass;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/3/12
 */
@MappedClass(value = DelayConfirmCounter.class)
public class SingleDelayConfirmCounterDTO implements DelayConfirmCounterDTO {
    private Integer confirmedCount;

    public Integer getConfirmedCount() {
        return confirmedCount;
    }

    public void setConfirmedCount(Integer confirmedCount) {
        this.confirmedCount = confirmedCount;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(SingleDelayConfirmCounterDTO.class).omitNullValues()
                .add("confirmedCount", confirmedCount)
                .toString();
    }
}
