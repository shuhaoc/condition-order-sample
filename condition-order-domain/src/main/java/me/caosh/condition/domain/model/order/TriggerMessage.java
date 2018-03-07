package me.caosh.condition.domain.model.order;

import com.google.common.base.MoreObjects;
import com.google.common.base.Optional;
import hbec.intellitrade.common.market.RealTimeMarket;
import hbec.intellitrade.condorder.domain.ConditionOrder;
import hbec.intellitrade.strategy.domain.signal.Signal;


/**
 * Created by caosh on 2017/8/30.
 */
@Deprecated
public class TriggerMessage {
    private final Signal signal;
    private final ConditionOrder conditionOrder;
    private final RealTimeMarket realTimeMarket;

    public TriggerMessage(Signal signal, ConditionOrder conditionOrder, RealTimeMarket realTimeMarket) {
        this.signal = signal;
        this.conditionOrder = conditionOrder;
        this.realTimeMarket = realTimeMarket;
    }

    public TriggerMessage(Signal signal, ConditionOrder conditionOrder) {
        this.signal = signal;
        this.conditionOrder = conditionOrder;
        this.realTimeMarket = null;
    }

    public Signal getSignal() {
        return signal;
    }

    public ConditionOrder getConditionOrder() {
        return conditionOrder;
    }

    public Optional<RealTimeMarket> getRealTimeMarket() {
        return Optional.fromNullable(realTimeMarket);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TriggerMessage)) return false;

        TriggerMessage that = (TriggerMessage) o;

        if (signal != null ? !signal.equals(that.signal) : that.signal != null) return false;
        if (conditionOrder != null ? !conditionOrder.equals(that.conditionOrder) : that.conditionOrder != null)
            return false;
        return !(realTimeMarket != null ? !realTimeMarket.equals(that.realTimeMarket) : that.realTimeMarket != null);

    }

    @Override
    public int hashCode() {
        int result = signal != null ? signal.hashCode() : 0;
        result = 31 * result + (conditionOrder != null ? conditionOrder.hashCode() : 0);
        result = 31 * result + (realTimeMarket != null ? realTimeMarket.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("signal", signal)
                .add("conditionOrder", conditionOrder)
                .add("realTimeMarket", realTimeMarket)
                .toString();
    }
}
