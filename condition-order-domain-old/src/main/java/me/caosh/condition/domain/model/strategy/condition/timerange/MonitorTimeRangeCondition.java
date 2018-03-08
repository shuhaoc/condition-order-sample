package me.caosh.condition.domain.model.strategy.condition.timerange;

import hbec.intellitrade.common.market.RealTimeMarket;
import hbec.intellitrade.strategy.domain.condition.market.MarketCondition;
import hbec.intellitrade.strategy.domain.signal.Signals;
import hbec.intellitrade.strategy.domain.signal.TradeSignal;

/**
 * 包装{@link MarketCondition}实现监控时段控制
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2018/2/1
 */
public class MonitorTimeRangeCondition implements MarketCondition {
    /**
     * 监控时段
     */
    private final MonitorTimeRange monitorTimeRange;
    private final MarketCondition marketCondition;

    public MonitorTimeRangeCondition(MonitorTimeRange monitorTimeRange, MarketCondition marketCondition) {
        this.monitorTimeRange = monitorTimeRange;
        this.marketCondition = marketCondition;
    }

    @Override
    public TradeSignal onMarketTick(RealTimeMarket realTimeMarket) {
        if (!monitorTimeRange.isInTimeRangeNow()) {
            return Signals.none();
        }
        return marketCondition.onMarketTick(realTimeMarket);
    }

}
