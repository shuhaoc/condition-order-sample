package hbec.intellitrade.strategy.domain.timerange;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import org.joda.time.LocalDateTime;

/**
 * 星期、时间组合条件
 *
 * @author caoshuhao@touker.com
 * @date 2018/1/27
 */
public class WeekTimeRange implements MonitorTimeRange {
    private final WeekRange weekRange;
    private final LocalTimeRange localTimeRange;

    /**
     * 构建监控时段条件
     * <p>
     * 星期范围和时间范围至少一个非空
     *
     * @param weekRange      星期范围，可为空
     * @param localTimeRange 时间范围，可为空
     */
    public WeekTimeRange(WeekRange weekRange, LocalTimeRange localTimeRange) {
        Preconditions.checkArgument(weekRange != null && localTimeRange != null,
                                    "Week range and time range are both null");
        this.weekRange = weekRange;
        this.localTimeRange = localTimeRange;
    }

    public WeekRange getWeekRange() {
        return weekRange;
    }

    public LocalTimeRange getLocalTimeRange() {
        return localTimeRange;
    }

    /**
     * 获取对应的监控时段选项，用于POJO装载
     *
     * @return 监控时段选项
     */
    public MonitorTimeRangeOption getOption() {
        return MonitorTimeRangeOption.ENABLED;
    }

    @Override
    public boolean isInRange(LocalDateTime localDateTime) {
        if (weekRange != null && !weekRange.isDateInRange(localDateTime.toLocalDate())) {
            return false;
        }
        if (localTimeRange != null && !localTimeRange.isTimeInRange(localDateTime.toLocalTime())) {
            return false;
        }
        // 默认不作限制
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        WeekTimeRange that = (WeekTimeRange) o;

        if (weekRange != null ? !weekRange.equals(that.weekRange) : that.weekRange != null) {
            return false;
        }
        return localTimeRange != null ? localTimeRange.equals(that.localTimeRange) : that.localTimeRange == null;
    }

    @Override
    public int hashCode() {
        int result = weekRange != null ? weekRange.hashCode() : 0;
        result = 31 * result + (localTimeRange != null ? localTimeRange.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(WeekTimeRange.class).omitNullValues()
                .add("weekRange", weekRange)
                .add("localTimeRange", localTimeRange)
                .toString();
    }
}
