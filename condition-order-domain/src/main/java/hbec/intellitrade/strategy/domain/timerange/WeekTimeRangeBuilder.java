package hbec.intellitrade.strategy.domain.timerange;

import me.caosh.autoasm.ConvertibleBuilder;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/3/14
 */
public class WeekTimeRangeBuilder implements ConvertibleBuilder<WeekTimeRange> {
    private WeekRangeBuilder weeRange = new WeekRangeBuilder();
    private LocalTimeRangeBuilder localTimeRange = new LocalTimeRangeBuilder();

    public WeekRangeBuilder getWeeRange() {
        return weeRange;
    }

    public LocalTimeRangeBuilder getLocalTimeRange() {
        return localTimeRange;
    }

    public boolean isValid() {
        return weeRange.isValid() || localTimeRange.isValid();
    }

    @Override
    public WeekTimeRange build() {
        return new WeekTimeRange(weeRange.build(), localTimeRange.build());
    }
}
