package me.caosh.condition.domain.model.strategy.condition.time;

import hbec.intellitrade.strategy.domain.factor.TimeFactor;

/**
 * 可转换为等价的时间因子的时间条件
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2018/1/31
 */
public interface BasicTimeCondition extends TimeCondition {
    /**
     * 获取时间因子
     *
     * @return 时间因子
     */
    TimeFactor getTimeFactor();
}
