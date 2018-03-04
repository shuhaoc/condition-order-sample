package hbec.intellitrade.strategy.domain.factor;

import com.google.common.base.MoreObjects;
import com.google.common.base.Optional;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;

/**
 * 每日到时仅触发一次条件
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2018/2/1
 */
public class DailyTargetTimeFactor implements TimeFactor {
    private final LocalTime targetTime;
    private LocalDate lastTriggerDate;

    public DailyTargetTimeFactor(LocalTime targetTime) {
        this.targetTime = targetTime;
    }

    public DailyTargetTimeFactor(LocalTime targetTime, LocalDate lastTriggerDate) {
        this.targetTime = targetTime;
        this.lastTriggerDate = lastTriggerDate;
    }

    public LocalTime getTargetTime() {
        return targetTime;
    }

    public Optional<LocalDate> getLastTriggerDate() {
        return Optional.fromNullable(lastTriggerDate);
    }

    public DailyTargetTimeFactor setTodayTriggered() {
        return new DailyTargetTimeFactor(targetTime, LocalDate.now());
    }

    @Override
    public boolean apply(LocalDateTime localDateTime) {
        if (localDateTime.toLocalDate().equals(lastTriggerDate)) {
            return false;
        }
        return localDateTime.toLocalTime().compareTo(targetTime) >= 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DailyTargetTimeFactor that = (DailyTargetTimeFactor) o;

        if (!targetTime.equals(that.targetTime)) {
            return false;
        }
        return lastTriggerDate != null ? lastTriggerDate.equals(that.lastTriggerDate) : that.lastTriggerDate == null;
    }

    @Override
    public int hashCode() {
        int result = targetTime.hashCode();
        result = 31 * result + (lastTriggerDate != null ? lastTriggerDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(DailyTargetTimeFactor.class).omitNullValues()
                .add("targetTime", targetTime)
                .add("lastTriggerDate", lastTriggerDate)
                .toString();
    }
}
