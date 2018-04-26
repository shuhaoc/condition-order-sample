package hbec.intellitrade.strategy.domain.factor;

import com.google.common.base.MoreObjects;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;

import java.util.Objects;

/**
 * 每日到时仅触发一次条件
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2018/2/1
 */
public class DailyTargetTimeFactor implements TimeFactor {
    private final LocalTime targetTime;
    private final boolean todayTriggered;

    public DailyTargetTimeFactor(LocalTime targetTime) {
        this.targetTime = targetTime;
        this.todayTriggered = false;
    }

    public DailyTargetTimeFactor(LocalTime targetTime, boolean todayTriggered) {
        this.targetTime = targetTime;
        this.todayTriggered = todayTriggered;
    }

    public LocalTime getTargetTime() {
        return targetTime;
    }

    public boolean isTodayTriggered() {
        return todayTriggered;
    }

    public DailyTargetTimeFactor setTodayTriggered() {
        return new DailyTargetTimeFactor(targetTime, true);
    }

    @Override
    public boolean apply(LocalDateTime localDateTime) {
        return !todayTriggered && localDateTime.toLocalTime().compareTo(targetTime) >= 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }
        DailyTargetTimeFactor that = (DailyTargetTimeFactor) o;
        return todayTriggered == that.todayTriggered &&
                Objects.equals(targetTime, that.targetTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(targetTime, todayTriggered);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(DailyTargetTimeFactor.class).omitNullValues()
                .add("targetTime", targetTime)
                .add("todayTriggered", todayTriggered)
                .toString();
    }
}
