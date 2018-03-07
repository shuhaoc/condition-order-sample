package me.caosh.condition.domain.model.strategy.container;

import com.google.common.base.MoreObjects;
import hbec.intellitrade.common.market.MarketID;
import hbec.intellitrade.common.market.RealTimeMarket;
import hbec.intellitrade.strategy.domain.MarketDrivenStrategy;
import hbec.intellitrade.strategy.domain.shared.DirtyFlag;
import me.caosh.condition.domain.model.condition.TurnUpCondition;
import me.caosh.condition.domain.model.order.constant.StrategyState;
import hbec.intellitrade.strategy.domain.signal.Signal;
import hbec.intellitrade.strategy.domain.signal.Signals;
import hbec.intellitrade.strategy.domain.signal.TradeSignal;
import org.joda.time.LocalDateTime;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/2/8
 */
public class TestTurnUpStrategy implements MarketDrivenStrategy, DirtyFlag {
    private final int strategyId;
    private final MarketID marketID;
    private final TurnUpCondition turnUpCondition;
    private StrategyState strategyState = StrategyState.ACTIVE;

    public TestTurnUpStrategy(int strategyId, MarketID marketID, TurnUpCondition turnUpCondition) {
        this.strategyId = strategyId;
        this.marketID = marketID;
        this.turnUpCondition = turnUpCondition;
    }

    public long getStrategyId() {
        return strategyId;
    }

    @Override
    public MarketID getTrackMarketID() {
        return marketID;
    }

    @Override
    public TradeSignal onMarketTick(RealTimeMarket realTimeMarket) {
        return turnUpCondition.onMarketTick(realTimeMarket);
    }

    @Override
    public Signal onTimeTick(LocalDateTime localDateTime) {
        return Signals.none();
    }

    @Override
    public boolean isDirty() {
        return turnUpCondition.isDirty();
    }

    @Override
    public void clearDirty() {
        turnUpCondition.clearDirty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TestTurnUpStrategy that = (TestTurnUpStrategy) o;

        if (strategyId != that.strategyId) return false;
        if (!marketID.equals(that.marketID)) return false;
        return turnUpCondition.equals(that.turnUpCondition);
    }

    @Override
    public int hashCode() {
        int result = strategyId;
        result = 31 * result + marketID.hashCode();
        result = 31 * result + turnUpCondition.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(TestTurnUpStrategy.class).omitNullValues()
                .add("strategyId", strategyId)
                .add("marketID", marketID)
                .add("turnUpCondition", turnUpCondition)
                .add("strategyState", strategyState)
                .toString();
    }
}
