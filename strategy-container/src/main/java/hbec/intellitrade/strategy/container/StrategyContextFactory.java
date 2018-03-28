package hbec.intellitrade.strategy.container;

import hbec.intellitrade.common.market.MarketID;
import hbec.intellitrade.strategy.domain.MarketTrackingStrategy;
import hbec.intellitrade.strategy.domain.Strategy;

/**
 * {@link StrategyContext}工厂
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2018/2/7
 */
public class StrategyContextFactory {

    private final StrategyContextConfig strategyContextConfig;

    public StrategyContextFactory(StrategyContextConfig strategyContextConfig) {
        this.strategyContextConfig = strategyContextConfig;
    }

    /**
     * 根据策略类型创建策略运行上下文
     *
     * @param strategy 策略
     * @return 策略上下文
     */
    public StrategyContext create(Strategy strategy) {
        if (strategy instanceof MarketTrackingStrategy) {
            MarketTrackingStrategy marketTrackingStrategy = (MarketTrackingStrategy) strategy;
            MarketID trackMarketID = marketTrackingStrategy.getTrackMarketID();
            return new MarketStrategyContext(trackMarketID, marketTrackingStrategy, strategyContextConfig);
        }
        return new StrategyContext(TimeDrivenBucketKey.INSTANCE, strategy, strategyContextConfig);
    }
}
