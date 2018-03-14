package hbec.intellitrade.strategy.domain.timerange;

import com.google.common.base.MoreObjects;
import hbec.intellitrade.strategy.domain.factor.CompareOperator;
import hbec.intellitrade.strategy.domain.shared.Range;
import org.joda.time.LocalTime;

/**
 * 时间（仅时分秒）范围
 *
 * @author caoshuhao@touker.com
 * @date 2018/1/27
 */
public class LocalTimeRange {
    // TODO: 统一常量
    private static final LocalTime MARKET_OPEN_TIME = new LocalTime(9, 30, 0);
    private static final LocalTime MARKET_CLOSE_TIME = new LocalTime(15, 0, 0);

    private final Range<LocalTime> timeRange;

    /**
     * 构建时间范围
     *
     * @param beginTime 开始时间，空视为0点
     * @param endTime   结束时间，空视为0点前1毫秒
     */
    public LocalTimeRange(LocalTime beginTime, LocalTime endTime) {
        LocalTime beginTime1 = MoreObjects.firstNonNull(beginTime, MARKET_OPEN_TIME);
        LocalTime endTime1 = MoreObjects.firstNonNull(endTime, MARKET_CLOSE_TIME);
        this.timeRange = new Range<>(beginTime1, endTime1, CompareOperator.GE, CompareOperator.LE);
    }

    public LocalTime getBeginTime() {
        return timeRange.getLeft();
    }

    public LocalTime getEndTime() {
        return timeRange.getRight();
    }

    public boolean isTimeInRange(LocalTime localTime) {
        return timeRange.isInRange(localTime);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LocalTimeRange that = (LocalTimeRange) o;

        return timeRange != null ? timeRange.equals(that.timeRange) : that.timeRange == null;
    }

    @Override
    public int hashCode() {
        return timeRange != null ? timeRange.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "LocalTimeRange" + timeRange;
    }
}
