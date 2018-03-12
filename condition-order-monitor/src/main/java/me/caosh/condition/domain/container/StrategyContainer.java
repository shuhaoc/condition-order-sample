package me.caosh.condition.domain.container;

import com.google.common.base.Function;
import com.google.common.base.MoreObjects;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.MultimapBuilder;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.Sets;
import hbec.intellitrade.common.market.MarketID;
import hbec.intellitrade.common.market.RealTimeMarket;
import hbec.intellitrade.strategy.domain.MutableStrategy;
import hbec.intellitrade.strategy.domain.Strategy;
import hbec.intellitrade.strategy.domain.container.BucketKey;
import hbec.intellitrade.strategy.domain.signal.Signal;
import hbec.intellitrade.strategy.domain.signal.Signals;
import hbec.intellitrade.strategy.domain.signalpayload.MarketSignalPayload;
import hbec.intellitrade.strategy.domain.signalpayload.SignalPayload;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 基于Guava的multi-map实现的策略执行容器，以{@link BucketKey}为键，以{@link java.util.LinkedHashSet}作为策略集合
 * <p>
 * 要求策略实现equals & hashCode方法，hashCode相同的策略视为同一策略
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2018/1/31
 */
public class StrategyContainer {
    private static final Logger logger = LoggerFactory.getLogger(StrategyContainer.class);

    private final SetMultimap<BucketKey, StrategyContext> strategies = MultimapBuilder
            .<BucketKey, StrategyContext>hashKeys()
            .linkedHashSetValues()
            .build();
    private final StrategyContextFactory strategyContextFactory;
    private final StrategyWriter strategyWriter;

    public StrategyContainer() {
        this.strategyContextFactory = new StrategyContextFactory(StrategyContextConfig.DEFAULT);
        this.strategyWriter = NopStrategyWriter.INSTANCE;
    }

    public StrategyContainer(StrategyContextConfig strategyContextConfig, StrategyWriter strategyWriter) {
        this.strategyContextFactory = new StrategyContextFactory(strategyContextConfig);
        this.strategyWriter = strategyWriter;
    }

    /**
     * 添加或更新策略
     *
     * @param strategy 策略
     */
    public void add(Strategy strategy) {
        StrategyContext strategyContext = strategyContextFactory.create(strategy);
        BucketKey bucketKey = strategyContext.getBucketKey();
        Set<StrategyContext> bucket = strategies.get(bucketKey);
        bucket.remove(strategyContext);
        bucket.add(strategyContext);
    }

    /**
     * 删除策略
     *
     * @param strategyId 策略ID
     */
    public void remove(long strategyId) {
        Iterator<Map.Entry<BucketKey, StrategyContext>> iterator = strategies.entries().iterator();
        while (iterator.hasNext()) {
            Map.Entry<BucketKey, StrategyContext> entry = iterator.next();
            StrategyContext strategyContext = entry.getValue();
            Strategy strategy = strategyContext.getStrategy();
            if (strategy.getStrategyId() == strategyId) {
                strategies.remove(entry.getKey(), entry.getValue());
                break;
            }
        }
    }

    /**
     * 获取marketID对应的策略集合
     *
     * @param bucketKey bucket key
     * @return 策略集合
     */
    public Set<Strategy> getBucket(BucketKey bucketKey) {
        return Sets.newHashSet(Iterables.transform(strategies.get(bucketKey), new Function<StrategyContext, Strategy>() {
            @Override
            public Strategy apply(StrategyContext strategyContext) {
                return strategyContext.getStrategy();
            }
        }));
    }

    /**
     * 接受实时行情数据
     *
     * @param realTimeMarkets 实时行情数据
     * @return 触发的信号集合
     */
    public Collection<SignalPayload> onMarketTicks(Iterable<RealTimeMarket> realTimeMarkets) {
        List<SignalPayload> signalPayloads = Lists.newArrayList();
        for (RealTimeMarket realTimeMarket : realTimeMarkets) {
            MarketID marketID = realTimeMarket.getMarketID();
            Set<StrategyContext> bucket = strategies.get(marketID);
            for (StrategyContext strategyContext : bucket) {
                Strategy strategy = strategyContext.getStrategy();
                Signal tradeSignal = checkMarketCondition((MarketStrategyContext) strategyContext, realTimeMarket);
                if (tradeSignal.isValid()) {
                    MarketSignalPayload marketSignalPayload = new MarketSignalPayload(tradeSignal, strategy,
                            realTimeMarket);
                    signalPayloads.add(marketSignalPayload);
                }
            }
        }
        for (Map.Entry<BucketKey, StrategyContext> entry : strategies.entries()) {
            StrategyContext strategyContext = entry.getValue();
            // 未触发有效交易信号的，判断是否需要延迟同步
            if (strategyContext.getStrategy() instanceof MutableStrategy) {
                MutableStrategy mutableStrategy = (MutableStrategy) strategyContext.getStrategy();
                if (mutableStrategy.isDirty()) {
                    strategyWriter.write(strategyContext.getStrategy());
                }
                if (mutableStrategy.isPersistentPropertyDirty()) {
                    strategyContext.markDelaySync();
                    logger.info("Mark delay sync, strategy={}", strategyContext.getStrategy());
                    // 清除脏标志，下次动态属性变更时再标记
                    mutableStrategy.clearDirty();
                }
                mutableStrategy.clearDirty();
            }
        }
        return signalPayloads;
    }

    /**
     * 接受秒级时间变化
     *
     * @param localDateTime 时间点
     * @return 触发的信号集合
     */
    public Collection<SignalPayload> onTimeTick(LocalDateTime localDateTime) {
        List<SignalPayload> signalPayloads = Lists.newArrayList();
        for (StrategyContext strategyContext : strategies.values()) {
            Strategy strategy = strategyContext.getStrategy();
            Signal signal = checkTimeCondition(strategyContext, localDateTime);
            if (signal.isValid()) {
                SignalPayload signalPayload = new SignalPayload(signal, strategy);
                signalPayloads.add(signalPayload);
            }
        }
        return signalPayloads;
    }

    private Signal checkMarketCondition(MarketStrategyContext marketStrategyContext, RealTimeMarket realTimeMarket) {
        // 实现异常安全
        try {
            return marketStrategyContext.onMarketTick(realTimeMarket);
        } catch (Exception e) {
            logger.error("Check error, strategyContext=" + marketStrategyContext, e);
            return Signals.none();
        }
    }

    private Signal checkTimeCondition(StrategyContext strategyContext, LocalDateTime localDateTime) {
        // 实现异常安全
        try {
            return strategyContext.onTimeTick(localDateTime);
        } catch (Exception e) {
            logger.error("Check error, strategyContext=" + strategyContext, e);
            return Signals.none();
        }
    }

    /**
     * 获取策略数量
     *
     * @return 策略数量
     */
    public int size() {
        return strategies.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        StrategyContainer that = (StrategyContainer) o;

        return strategies.equals(that.strategies);
    }

    @Override
    public int hashCode() {
        return strategies.hashCode();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(StrategyContainer.class).omitNullValues()
                .add("strategies", strategies)
                .add("strategyContextFactory", strategyContextFactory)
                .toString();
    }
}
