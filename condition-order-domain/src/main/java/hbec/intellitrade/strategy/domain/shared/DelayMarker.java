package hbec.intellitrade.strategy.domain.shared;


import org.joda.time.DateTime;
import org.joda.time.Duration;

/**
 * 延迟标记
 * <p>
 * 通过构造此对象来标记一个事件的触发时间，并给定一个延迟时间（秒级），在给定延迟时间过去之后isTimesUp方法返回true，在此之前返回false
 * <p>
 * Created by caosh on 2017/8/19.
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2018/2/7
 */
public class DelayMarker {
    private final DateTime occurTime;
    private final int delaySeconds;

    public DelayMarker(int delaySeconds) {
        this.occurTime = DateTime.now();
        this.delaySeconds = delaySeconds;
    }

    public Duration getCurrentDuration() {
        return new Duration(occurTime.toDateTime(), DateTime.now());
    }

    public boolean isTimesUp() {
        return getCurrentDuration().getStandardSeconds() >= delaySeconds;
    }
}
