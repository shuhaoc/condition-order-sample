package hbec.intellitrade.strategy.domain;

import hbec.intellitrade.strategy.domain.signal.Signal;
import org.joda.time.LocalDateTime;

/**
 * 时间驱动策略
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2018/2/1
 */
public interface TimeDrivenStrategy extends Strategy {
    /**
     * 接受时间Tick返回交易信号，所有策略都响应时间变化
     * <p>
     * {@link Signal#isValid()}返回false表示无信号
     *
     * @param localDateTime 时间点
     * @return 交易信号
     */
    Signal onTimeTick(LocalDateTime localDateTime);
}
