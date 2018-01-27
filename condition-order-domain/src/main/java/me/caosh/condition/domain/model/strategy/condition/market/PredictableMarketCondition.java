package me.caosh.condition.domain.model.strategy.condition.market;

import me.caosh.condition.domain.model.strategy.condition.deviation.DeviationCtrlCondition;
import me.caosh.condition.domain.model.strategy.factor.TargetPriceFactor;

/**
 * 可预测的行情条件，即可以求目标价因子的条件
 * <p>
 * 目标价因子可以用于简化条件逻辑、实现偏差控制等功能
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2018/1/29
 * @see TargetPriceFactor
 * @see DeviationCtrlCondition
 */
public interface PredictableMarketCondition extends MarketCondition {
    /**
     * 获取目标价因子
     *
     * @return 目标价因子
     */
    TargetPriceFactor getTargetPriceFactor();
}
