package hbec.intellitrade.strategy.container;

import hbec.intellitrade.strategy.domain.Strategy;

/**
 * 策略Writer，用于Container实现策略缓存双写
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2018/3/12
 */
public interface StrategyWriter {
    /**
     * 写策略
     *
     * @param strategy 策略
     */
    void write(Strategy strategy);
}
