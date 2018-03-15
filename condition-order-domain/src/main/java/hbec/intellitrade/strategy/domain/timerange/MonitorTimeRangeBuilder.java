package hbec.intellitrade.strategy.domain.timerange;

import me.caosh.autoasm.ConvertibleBuilder;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/3/14
 */
public class MonitorTimeRangeBuilder implements ConvertibleBuilder<MonitorTimeRange> {
    private MonitorTimeRangeOption option = MonitorTimeRangeOption.DISABLED;
    private WeekRangeBuilder weeRange = new WeekRangeBuilder();
    private LocalTimeRangeBuilder localTimeRange = new LocalTimeRangeBuilder();

    public void setOption(MonitorTimeRangeOption option) {
        this.option = option;
    }

    public WeekRangeBuilder getWeekRange() {
        return weeRange;
    }

    public LocalTimeRangeBuilder getLocalTimeRange() {
        return localTimeRange;
    }

    @Override
    public MonitorTimeRange build() {
        if (option == MonitorTimeRangeOption.DISABLED) {
            return NoneMonitorTimeRange.INSTANCE;
        }
        return new WeekTimeRange(weeRange.build(), localTimeRange.build());
    }
}
