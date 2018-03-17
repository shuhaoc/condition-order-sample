package hbec.intellitrade.condorder.domain.delayconfirm;

import com.google.common.base.Optional;
import hbec.intellitrade.strategy.domain.condition.delayconfirm.AbstractDelayConfirmCondition;
import hbec.intellitrade.strategy.domain.condition.delayconfirm.DelayConfirmCounter;
import hbec.intellitrade.strategy.domain.condition.market.MarketCondition;

/**
 * @author caoshuhao@touker.com
 * @date 2018/3/17
 */
public class DelayConfirmCounterExtractor {
    private final DelayConfirmCounter counter;

    public DelayConfirmCounterExtractor(MarketCondition compositeCondition) {
        if (compositeCondition instanceof AbstractDelayConfirmCondition) {
            counter = ((AbstractDelayConfirmCondition) compositeCondition).getCounter();
        } else {
            counter = null;
        }
    }

    public Optional<DelayConfirmCounter> getCounter() {
        return Optional.fromNullable(counter);
    }

    public boolean isDirty() {
        if (counter != null) {
            return counter.isDirty();
        }
        return false;
    }

    public void clearDirtyIfNeed() {
        if (counter != null) {
            counter.clearDirty();
        }
    }

    public void resetCounterIfNeed() {
        if (counter != null) {
            counter.reset();
        }
    }
}
