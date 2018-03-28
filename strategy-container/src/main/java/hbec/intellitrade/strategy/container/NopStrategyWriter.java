package hbec.intellitrade.strategy.container;

import hbec.intellitrade.strategy.container.StrategyWriter;
import hbec.intellitrade.strategy.domain.Strategy;

/**
 * 空操作的{@link StrategyWriter}
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2018/3/12
 */
public enum NopStrategyWriter implements StrategyWriter {
    /**
     * 单例
     */
    INSTANCE;

    @Override
    public void write(Strategy strategy) {
    }
}
