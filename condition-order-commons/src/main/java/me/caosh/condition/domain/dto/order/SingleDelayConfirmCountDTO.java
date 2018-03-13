package me.caosh.condition.domain.dto.order;

import com.google.common.base.MoreObjects;
import hbec.intellitrade.condorder.domain.delayconfirm.count.SingleDelayConfirmCount;
import hbec.intellitrade.condorder.domain.delayconfirm.count.SingleDelayConfirmCountBuilder;
import me.caosh.autoasm.MappedClass;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/3/12
 */
@MappedClass(value = SingleDelayConfirmCount.class, builderClass = SingleDelayConfirmCountBuilder.class)
public class SingleDelayConfirmCountDTO implements DelayConfirmCountDTO {
    private Integer confirmedCount;

    public Integer getConfirmedCount() {
        return confirmedCount;
    }

    public void setConfirmedCount(Integer confirmedCount) {
        this.confirmedCount = confirmedCount;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(SingleDelayConfirmCountDTO.class).omitNullValues()
                .add("confirmedCount", confirmedCount)
                .toString();
    }
}
