package me.caosh.condition.domain.dto.order;

import com.google.common.base.MoreObjects;

import java.util.Date;

/**
 * Created by caosh on 2017/8/20.
 */
public class SimpleTimeConditionDTO implements ConditionDTO {
    private static final long serialVersionUID = 1L;

    private Date targetTime;

    public SimpleTimeConditionDTO() {
    }

    public SimpleTimeConditionDTO(Date targetTime) {
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

    @Override
    public void accept(ConditionDTOVisitor visitor) {
        visitor.visitSimpleTimeConditionDTO(this);
    }
}
