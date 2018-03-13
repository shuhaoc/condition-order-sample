package hbec.intellitrade.strategy.domain.timerange;

import org.joda.time.LocalDateTime;

/**
 * 监控时段
 *
 * @author caoshuhao@touker.com
 * @date 2018/2/4
 */
public interface MonitorTimeRange {
    /**
     * 是否在监控时段范围内
     *
     * @param localDateTime 时间点
     * @return 是否在监控时段范围内
     */
    boolean isInRange(LocalDateTime localDateTime);
}
