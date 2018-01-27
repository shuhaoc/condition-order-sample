package me.caosh.condition.domain.model.strategy.condition.timerange;

/**
 * 监控时段
 *
 * @author caoshuhao@touker.com
 * @date 2018/2/4
 */
public interface MonitorTimeRange {
    /**
     * 当前时间在监控时段范围内
     *
     * @return 在监控时段范围内
     */
    boolean isInTimeRangeNow();
}
