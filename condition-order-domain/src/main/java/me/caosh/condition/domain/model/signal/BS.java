package me.caosh.condition.domain.model.signal;

import com.google.common.base.MoreObjects;

/**
 * Created by caosh on 2017/8/1.
 */
public class BS extends AbstractSignal implements TradeSignal {
    private final boolean deviationExceeded;

    public BS() {
        deviationExceeded = false;
    }

    public BS(boolean deviationExceeded) {
        this.deviationExceeded = deviationExceeded;
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public boolean getDeviationExceeded() {
        return deviationExceeded;
    }

    @Override
    public TradeSignal withDeviationExceeded() {
        return new BS(true);
    }

    @Override
    public void accept(SignalVisitor visitor) {
        visitor.visitBS(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BS bs = (BS) o;

        return deviationExceeded == bs.deviationExceeded;
    }

    @Override
    public int hashCode() {
        return (deviationExceeded ? 1 : 0);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(BS.class).omitNullValues()
                .add("deviationExceeded", deviationExceeded)
                .toString();
    }
}
