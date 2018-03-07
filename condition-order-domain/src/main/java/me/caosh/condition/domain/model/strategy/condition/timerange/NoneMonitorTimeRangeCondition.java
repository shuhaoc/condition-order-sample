package me.caosh.condition.domain.model.strategy.condition.timerange;

import hbec.intellitrade.common.market.RealTimeMarket;
import hbec.intellitrade.condorder.domain.ConditionVisitor;
import hbec.intellitrade.strategy.domain.condition.market.MarketCondition;
import hbec.intellitrade.strategy.domain.signal.TradeSignal;

/**
 * 未启用监控时段的包装类
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2018/2/1
 */
public class NoneMonitorTimeRangeCondition implements MarketCondition {
    private final MarketCondition marketCondition;

    public NoneMonitorTimeRangeCondition(MarketCondition marketCondition) {
        this.marketCondition = marketCondition;
    }

    @Override
    public TradeSignal onMarketTick(RealTimeMarket realTimeMarket) {
        return marketCondition.onMarketTick(realTimeMarket);
    }

    @Override
    public void accept(ConditionVisitor visitor) {
        marketCondition.accept(visitor);
    }
}
