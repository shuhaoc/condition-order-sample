package hbec.intellitrade.strategy.domain.factor;

import com.google.common.base.Preconditions;
import org.joda.time.Duration;
import org.joda.time.LocalDateTime;

/**
 * 刚过目标时间因子
 * <p>
 * 时间达到或刚过目标时间时（一分钟内）条件达成，过时不候
 *
 * @author caoshuhao@touker.com
 * @date 2018/1/29
 */
public class JustAfterTargetTimeFactor implements TimeFactor {
    private static final int TIME_WINDOW = 60000;

    private final LocalDateTime targetTime;

    public JustAfterTargetTimeFactor(LocalDateTime targetTime) {
        Preconditions.checkNotNull(targetTime, "targetTime cannot be null");
        this.targetTime = targetTime;
    }

    public LocalDateTime getTargetTime() {
        return targetTime;
    }

    @Override
    public boolean apply(LocalDateTime localDateTime) {
        Duration duration = new Duration(targetTime.toDateTime(), localDateTime.toDateTime());
        long millis = duration.getMillis();
        return millis >= 0 && millis <= TIME_WINDOW;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        JustAfterTargetTimeFactor that = (JustAfterTargetTimeFactor) o;

        return targetTime != null ? targetTime.equals(that.targetTime) : that.targetTime == null;
    }

    @Override
    public int hashCode() {
        return targetTime != null ? targetTime.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "JustAfter(" + targetTime + ")";
    }
}
