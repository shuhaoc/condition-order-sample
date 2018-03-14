package hbec.intellitrade.strategy.domain.timerange;

import me.caosh.autoasm.ConvertibleBuilder;
import org.joda.time.LocalTime;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/3/14
 */
public class LocalTimeRangeBuilder implements ConvertibleBuilder<LocalTimeRange> {
    private LocalTime beginTime;
    private LocalTime endTime;

    public void setBeginTime(LocalTime beginTime) {
        this.beginTime = beginTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public boolean isValid() {
        return beginTime != null || endTime != null;
    }

    @Override
    public LocalTimeRange build() {
        return new LocalTimeRange(beginTime, endTime);
    }
}
