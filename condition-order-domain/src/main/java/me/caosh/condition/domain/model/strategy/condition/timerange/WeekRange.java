package me.caosh.condition.domain.model.strategy.condition.timerange;

import com.google.common.base.Function;
import com.google.common.base.MoreObjects;
import me.caosh.condition.domain.model.strategy.factor.CompareOperator;
import me.caosh.condition.domain.model.strategy.shared.Range;
import me.caosh.condition.domain.model.strategy.shared.Ranges;
import me.caosh.condition.domain.model.strategy.shared.Week;
import org.joda.time.LocalDate;

/**
 * 星期范围
 *
 * @author caoshuhao@touker.com
 * @date 2018/1/27
 */
public class WeekRange {
    private final Range<Week> weekRange;

    /**
     * 星期范围
     *
     * @param beginWeek 开始星期，空视为周一
     * @param endWeek   结束星期，空视为周五
     */
    public WeekRange(Week beginWeek, Week endWeek) {
        Week beginWeekNotNull = MoreObjects.firstNonNull(beginWeek, Week.MON);
        Week endWeekNotNull = MoreObjects.firstNonNull(endWeek, Week.FRI);
        this.weekRange = new Range<>(beginWeekNotNull, endWeekNotNull, CompareOperator.GE, CompareOperator.LE);
    }

    public Week getBeginWeek() {
        return weekRange.getLeft();
    }

    public Week getEndWeek() {
        return weekRange.getRight();
    }

    public boolean isDateInRange(LocalDate localDate) {
        Range<Integer> dayOfWeekRange = Ranges.transform(weekRange, new Function<Week, Integer>() {
            @Override
            public Integer apply(Week week) {
                return week.getDayOfWeek();
            }
        });
        int dayOfWeek = localDate.getDayOfWeek();
        return dayOfWeekRange.isInRange(dayOfWeek);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        WeekRange weekRange1 = (WeekRange) o;

        return weekRange != null ? weekRange.equals(weekRange1.weekRange) : weekRange1.weekRange == null;
    }

    @Override
    public int hashCode() {
        return weekRange != null ? weekRange.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "LocalTimeRange" + weekRange;
    }
}
