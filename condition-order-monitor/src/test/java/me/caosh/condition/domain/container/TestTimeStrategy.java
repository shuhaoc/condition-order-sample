package me.caosh.condition.domain.container;

import com.google.common.base.MoreObjects;
import hbec.intellitrade.condorder.domain.OrderState;
import hbec.intellitrade.strategy.domain.TimeDrivenStrategy;
import hbec.intellitrade.strategy.domain.signal.Signal;
import me.caosh.condition.domain.model.condition.TimeReachedCondition;
import org.joda.time.LocalDateTime;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/2/7
 */
class TestTimeStrategy implements TimeDrivenStrategy {
    private final int strategyId;
    private final TimeReachedCondition timeReachedCondition;
    private OrderState orderState = OrderState.ACTIVE;

    public TestTimeStrategy(int strategyId, TimeReachedCondition timeReachedCondition) {
        this.strategyId = strategyId;
        this.timeReachedCondition = timeReachedCondition;
    }

    @Override
    public long getStrategyId() {
        return strategyId;
    }

    @Override
    public Signal onTimeTick(LocalDateTime localDateTime) {
        return timeReachedCondition.onTimeTick(localDateTime);
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
                .add("strategyState", orderState)
                .toString();
    }
}
