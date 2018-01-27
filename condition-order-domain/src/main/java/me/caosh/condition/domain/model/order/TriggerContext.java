package me.caosh.condition.domain.model.order;

import com.google.common.base.MoreObjects;
import com.google.common.base.Optional;
import me.caosh.condition.domain.model.market.RealTimeMarket;
import me.caosh.condition.domain.model.signal.Signal;


/**
 * Created by caosh on 2017/8/14.
 *
 * @author caoshuhao@touker.com
 */
public class TriggerContext {
    private final Signal signal;
    private final ConditionOrder conditionOrder;
    private RealTimeMarket triggerRealTimeMarket;

    public TriggerContext(Signal signal, ConditionOrder conditionOrder, RealTimeMarket triggerRealTimeMarket) {
        this.signal = signal;
        this.conditionOrder = conditionOrder;
        this.triggerRealTimeMarket = triggerRealTimeMarket;
    }

    public Signal getSignal() {
        return signal;
    }

    public ConditionOrder getConditionOrder() {
        return conditionOrder;
    }

    public Optional<RealTimeMarket> getTriggerRealTimeMarket() {
        return Optional.fromNullable(triggerRealTimeMarket);
    }

    public void setTriggerRealTimeMarket(RealTimeMarket triggerRealTimeMarket) {
        this.triggerRealTimeMarket = triggerRealTimeMarket;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("signal", signal)
                .add("conditionOrder", conditionOrder)
                .add("triggerRealTimeMarket", triggerRealTimeMarket)
                .toString();
    }
}
