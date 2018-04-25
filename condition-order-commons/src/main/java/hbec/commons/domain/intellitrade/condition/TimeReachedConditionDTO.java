package hbec.commons.domain.intellitrade.condition;

import com.google.common.base.MoreObjects;
import hbec.intellitrade.conditionorder.domain.orders.time.TimeReachedCondition;
import hbec.intellitrade.conditionorder.domain.orders.time.TimeReachedConditionBuilder;
import me.caosh.autoasm.MappedClass;

/**
 * Created by caosh on 2017/8/20.
 */
@MappedClass(value = TimeReachedCondition.class, builderClass = TimeReachedConditionBuilder.class)
public class TimeReachedConditionDTO implements ConditionDTO {
    private static final long serialVersionUID = 1L;

    private String targetTime;

    public TimeReachedConditionDTO() {
    }

    public String getTargetTime() {
        return targetTime;
    }

    public void setTargetTime(String targetTime) {
        this.targetTime = targetTime;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(TimeReachedConditionDTO.class).omitNullValues()
                .add("targetTime", targetTime)
                .toString();
    }
}
