package me.caosh.condition.infrastructure.tunnel.model;

import com.google.common.base.MoreObjects;
import hbec.intellitrade.conditionorder.domain.orders.time.TimeReachedCondition;
import hbec.intellitrade.conditionorder.domain.orders.time.TimeReachedConditionBuilder;
import me.caosh.autoasm.MappedClass;

import java.util.Date;

/**
 * Created by caosh on 2017/8/20.
 */
@MappedClass(value = TimeReachedCondition.class, builderClass = TimeReachedConditionBuilder.class)
public class TimeReachedConditionDO implements ConditionDO {
    private Date targetTime;

    public TimeReachedConditionDO() {
    }

    public Date getTargetTime() {
        return targetTime;
    }

    public void setTargetTime(Date targetTime) {
        this.targetTime = targetTime;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("targetTime", targetTime)
                .toString();
    }

}
