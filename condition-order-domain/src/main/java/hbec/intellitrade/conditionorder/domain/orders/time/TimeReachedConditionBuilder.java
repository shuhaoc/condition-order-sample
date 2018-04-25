package hbec.intellitrade.conditionorder.domain.orders.time;

import me.caosh.autoasm.ConvertibleBuilder;
import org.joda.time.LocalDateTime;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/4/25
 */
public class TimeReachedConditionBuilder implements ConvertibleBuilder<TimeReachedCondition> {
    private LocalDateTime targetTime;

    public LocalDateTime getTargetTime() {
        return targetTime;
    }

    public void setTargetTime(LocalDateTime targetTime) {
        this.targetTime = targetTime;
    }

    @Override
    public TimeReachedCondition build() {
        return new TimeReachedCondition(targetTime);
    }
}
