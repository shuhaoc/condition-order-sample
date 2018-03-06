package me.caosh.condition.domain.model.strategy.container;

import com.google.common.base.MoreObjects;
import com.google.common.base.Optional;
import hbec.intellitrade.strategy.domain.TimeDrivenStrategy;
import me.caosh.condition.domain.model.condition.TimeReachedCondition;
import me.caosh.condition.domain.model.order.constant.StrategyState;
import me.caosh.condition.domain.model.strategy.condition.time.TimeCondition;
import org.joda.time.LocalDateTime;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/2/7
 */
class TestTimeStrategy implements TimeDrivenStrategy {
    private final int strategyId;
    private final TimeReachedCondition timeReachedCondition;
    private StrategyState strategyState = StrategyState.ACTIVE;

    public TestTimeStrategy(int strategyId, TimeReachedCondition timeReachedCondition) {
        this.strategyId = strategyId;
        this.timeReachedCondition = timeReachedCondition;
    }

    @Override
    public long getStrategyId() {
        return strategyId;
    }

    @Override
    public TimeCondition getCondition() {
        return timeReachedCondition;
    }

    @Override
    public Optional<LocalDateTime> getExpireTime() {
        return Optional.absent();
    }

    @Override
    public StrategyState getStrategyState() {
        return strategyState;
    }

    @Override
    public void setStrategyState(StrategyState strategyState) {
        this.strategyState = strategyState;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TestTimeStrategy that = (TestTimeStrategy) o;

        return strategyId == that.strategyId;
    }

    @Override
    public int hashCode() {
        return strategyId;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(TestTimeStrategy.class).omitNullValues()
                .add("strategyId", strategyId)
                .add("timeReachedCondition", timeReachedCondition)
                .add("strategyState", strategyState)
                .toString();
    }
}
