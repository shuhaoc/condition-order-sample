package hbec.intellitrade.strategy.domain;

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
}
