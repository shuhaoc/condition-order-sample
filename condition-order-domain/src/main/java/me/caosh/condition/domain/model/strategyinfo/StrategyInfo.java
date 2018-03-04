package me.caosh.condition.domain.model.strategyinfo;

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
}
