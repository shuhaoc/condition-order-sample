package hbec.intellitrade.strategy.domain.condition.time;

import hbec.intellitrade.strategy.domain.signal.TradeSignal;
import hbec.intellitrade.strategy.domain.condition.Condition;

/**
 * 时间条件，在时间变化时根据具体的条件返回交易信号
 *
 * @author caoshuhao@touker.com
 * @date 2018/1/27
 */
public interface TimeCondition extends Condition {
    /**
     * 条件时间Tick返回交易信号
     * {@link TradeSignal#isValid()}返回false表示无信号
     *
     * @return 交易信号
     */
    TradeSignal onTimeTick();
}
