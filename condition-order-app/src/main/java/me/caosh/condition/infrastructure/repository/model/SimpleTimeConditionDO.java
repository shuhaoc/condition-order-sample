package me.caosh.condition.infrastructure.repository.model;

import com.google.common.base.MoreObjects;

import java.util.Date;

/**
 * Created by caosh on 2017/8/20.
 */
public class SimpleTimeConditionDO implements ConditionDO {
    private Date targetTime;

    public SimpleTimeConditionDO() {
    }

    public SimpleTimeConditionDO(Date targetTime) {
        this.targetTime = targetTime;
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
