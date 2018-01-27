package me.caosh.condition.domain.model.strategy.description;

/**
 * 策略信息
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2018/1/31
 */
public interface StrategyInfo {
    /**
     * 策略实现系统类别
     *
     * @return 策略实现系统类别
     */
    StrategySystemType getSystemType();

    /**
     * 策略模板ID
     *
     * @return 策略模板ID
     */
    int getStrategyTemplateId();

    /**
     * 策略生命周期类别
     * TODO: 应该在条件单自己实现
     *
     * @return 策略生命周期类别
     */
    LifeCircle getLifeCircle();
}
