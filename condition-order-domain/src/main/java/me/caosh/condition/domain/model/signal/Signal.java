package me.caosh.condition.domain.model.signal;

/**
 * 策略引擎信号
 *
 * Created by caosh on 2017/8/1.
 */
public interface Signal {
    void accept(SignalVisitor visitor);

    /**
     * 信号有否有效，只有无信号（None）返回false，其他为true
     *
     * @return 信号有效
     */
    boolean isValid();
}
