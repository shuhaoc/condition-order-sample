package me.caosh.condition.domain.model.strategy;

import me.caosh.condition.domain.model.strategy.condition.time.TimeCondition;

/**
 * 时间驱动策略
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2018/2/1
 */
public interface TimeDrivenStrategy extends Strategy {
    /**
     * 时间驱动策略的条件必然是时间条件
     *
     * @return 行情条件
     */
    @Override
    TimeCondition getCondition();
}
