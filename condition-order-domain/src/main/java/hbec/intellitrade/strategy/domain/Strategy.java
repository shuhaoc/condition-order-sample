package hbec.intellitrade.strategy.domain;

import hbec.intellitrade.strategy.domain.signal.Signal;
import hbec.intellitrade.strategy.domain.signal.TradeSignal;
import org.joda.time.LocalDateTime;

/**
 * 策略实体接口
 * <p>
 * 策略接受行情、时间等输入，在符合条件时，产生相应的信号
 *
 * @author caoshuhao@touker.com
 * @date 2018/1/27
 */
public interface Strategy {
    /**
     * 获取策略ID
     *
     * @return 策略ID
     */
    long getStrategyId();

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
