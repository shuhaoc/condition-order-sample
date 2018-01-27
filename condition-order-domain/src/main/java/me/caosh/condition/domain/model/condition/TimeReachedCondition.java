package me.caosh.condition.domain.model.condition;

import com.google.common.base.MoreObjects;
import me.caosh.condition.domain.model.order.ConditionVisitor;
import me.caosh.condition.domain.model.strategy.condition.AbstractBasicTimeCondition;
import me.caosh.condition.domain.model.strategy.factor.JustAfterTargetTimeFactor;
import org.joda.time.LocalDateTime;

/**
 * 到时触发条件
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2018/1/29
 */
public class TimeReachedCondition extends AbstractBasicTimeCondition {
    private final JustAfterTargetTimeFactor timeFactor;

    public TimeReachedCondition(LocalDateTime localDateTime) {
        this.timeFactor = new JustAfterTargetTimeFactor(localDateTime);
    }

    public LocalDateTime getTargetTime() {
        return timeFactor.getTargetTime();
    }

    public boolean onTimeGoesBy(LocalDateTime localDateTime) {
        return timeFactor.apply(localDateTime);
    }

    @Override
    public JustAfterTargetTimeFactor getTimeFactor() {
        return timeFactor;
    }

    @Override
    public void accept(ConditionVisitor visitor) {
        visitor.visitSimpleTimeCondition(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TimeReachedCondition that = (TimeReachedCondition) o;

        return timeFactor.equals(that.timeFactor);
    }

    @Override
    public int hashCode() {
        return timeFactor.hashCode();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(TimeReachedCondition.class).omitNullValues()
                .add("timeFactor", timeFactor)
                .toString();
    }
}
