package hbec.intellitrade.strategy.domain.timerange;

import org.joda.time.LocalDateTime;

/**
 * 未配置监控时段的空对象
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2018/3/13
 */
public enum NoneMonitorTimeRange implements MonitorTimeRange {
    /**
     * 单例
     */
    INSTANCE;

    @Override
    public boolean isInRange(LocalDateTime localDateTime) {
        return true;
    }
}
