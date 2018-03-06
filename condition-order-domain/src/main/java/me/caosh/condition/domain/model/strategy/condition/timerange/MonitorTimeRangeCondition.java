package me.caosh.condition.domain.model.strategy.condition.timerange;

import hbec.intellitrade.common.market.RealTimeMarket;
import me.caosh.condition.domain.model.order.ConditionVisitor;
import me.caosh.condition.domain.model.signal.Signals;
import me.caosh.condition.domain.model.signal.TradeSignal;
import hbec.intellitrade.strategy.domain.condition.market.MarketCondition;

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
    public TradeSignal onMarketUpdate(RealTimeMarket realTimeMarket) {
        if (!monitorTimeRange.isInTimeRangeNow()) {
            return Signals.none();
        }
        return marketCondition.onMarketUpdate(realTimeMarket);
    }

    @Override
    public void accept(ConditionVisitor visitor) {
        marketCondition.accept(visitor);
    }
}
