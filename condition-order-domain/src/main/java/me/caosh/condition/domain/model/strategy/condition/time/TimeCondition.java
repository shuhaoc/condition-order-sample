package me.caosh.condition.domain.model.strategy.condition.time;

import me.caosh.condition.domain.model.signal.TradeSignal;
import me.caosh.condition.domain.model.strategy.condition.Condition;

/**
 * 时间条件，在时间变化时根据具体的条件返回交易信号
 *
 * @author caoshuhao@touker.com
 * @date 2018/1/27
 */
public interface TimeCondition extends Condition {
    /**
     * 条件接受秒级Tick返回交易信号
     * {@link TradeSignal#isValid()}返回false表示无信号
     *
     * @return 交易信号
     */
    TradeSignal onSecondTick();
}
