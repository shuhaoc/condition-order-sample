package me.caosh.condition.domain.model.strategy.condition.timerange;

import hbec.intellitrade.common.market.RealTimeMarket;
import me.caosh.condition.domain.model.order.ConditionVisitor;
import me.caosh.condition.domain.model.signal.TradeSignal;
import hbec.intellitrade.strategy.domain.condition.market.MarketCondition;

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
    public TradeSignal onMarketUpdate(RealTimeMarket realTimeMarket) {
        return marketCondition.onMarketUpdate(realTimeMarket);
    }

    @Override
    public void accept(ConditionVisitor visitor) {
        marketCondition.accept(visitor);
    }
}
