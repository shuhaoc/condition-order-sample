package me.caosh.condition.domain.model.strategy.container;

import com.google.common.base.MoreObjects;
import hbec.intellitrade.common.market.MarketID;
import hbec.intellitrade.common.market.RealTimeMarket;
import hbec.intellitrade.strategy.domain.MarketDrivenStrategy;
import hbec.intellitrade.strategy.domain.condition.market.MarketCondition;
import hbec.intellitrade.strategy.domain.strategies.condition.PriceCondition;
import me.caosh.condition.domain.model.order.constant.StrategyState;
import hbec.intellitrade.strategy.domain.signal.Signal;
import hbec.intellitrade.strategy.domain.signal.Signals;
import hbec.intellitrade.strategy.domain.signal.TradeSignal;
import org.joda.time.LocalDateTime;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/2/7
 */
class TestPriceStrategy implements MarketDrivenStrategy {
    private final int strategyId;
    private final MarketID marketID;
    private final PriceCondition priceCondition;
    private LocalDateTime expireTime;
    private StrategyState strategyState = StrategyState.ACTIVE;

    public TestPriceStrategy(Integer strategyId, MarketID marketID, PriceCondition priceCondition) {
        this.strategyId = strategyId;
        this.marketID = marketID;
        this.priceCondition = priceCondition;
        expireTime = null;
    }

    public TestPriceStrategy(int strategyId, MarketID marketID, PriceCondition priceCondition, LocalDateTime expireTime) {
        this.strategyId = strategyId;
        this.marketID = marketID;
        this.priceCondition = priceCondition;
        this.expireTime = expireTime;
    }

    public TestPriceStrategy(int strategyId, MarketID marketID, PriceCondition priceCondition, LocalDateTime expireTime,
                             StrategyState strategyState) {
        this.strategyId = strategyId;
        this.marketID = marketID;
        this.priceCondition = priceCondition;
        this.expireTime = expireTime;
        this.strategyState = strategyState;
    }

    @Override
    public long getStrategyId() {
        return strategyId;
    }

    @Override
    public TradeSignal onMarketTick(RealTimeMarket realTimeMarket) {
        if (strategyState != StrategyState.ACTIVE) {
            return Signals.none();
        }
        return priceCondition.onMarketTick(realTimeMarket);
    }

    @Override
    public Signal onTimeTick(LocalDateTime localDateTime) {
        if (isExpired(localDateTime)) {
            return Signals.expire();
        }
        return Signals.none();
    }

    private boolean isExpired(LocalDateTime localDateTime) {
        boolean expireTimeConfigured = expireTime != null;
        if (expireTimeConfigured) {
            return localDateTime.compareTo(expireTime) >= 0;
        }
        return false;
    }

    @Override
    public MarketID getTrackMarketID() {
        return marketID;
    }

    public void setExpireTime(LocalDateTime expireTime) {
        this.expireTime = expireTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TestPriceStrategy that = (TestPriceStrategy) o;

        return strategyId == that.strategyId;
    }

    @Override
    public int hashCode() {
        return strategyId;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(TestPriceStrategy.class).omitNullValues()
                .add("strategyId", strategyId)
                .add("marketID", marketID)
                .add("priceCondition", priceCondition)
                .add("strategyState", strategyState)
                .toString();
    }
}
