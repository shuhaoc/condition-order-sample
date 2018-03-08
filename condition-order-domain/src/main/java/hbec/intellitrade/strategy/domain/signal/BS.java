package hbec.intellitrade.strategy.domain.signal;

import com.google.common.base.MoreObjects;

/**
 * Created by caosh on 2017/8/1.
 */
public class BS extends AbstractSignal implements TradeSignal {
    private final Boolean deviationExceeded;

    public BS() {
        this.deviationExceeded = null;
    }

    BS(boolean deviationExceeded) {
        this.deviationExceeded = deviationExceeded;
    }

    @Override
    public boolean getDeviationExceeded() {
        return Boolean.TRUE.equals(deviationExceeded);
    }

    @Override
    public TradeSignal withDeviationExceeded() {
        return new BS(true);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BS bs = (BS) o;

        return deviationExceeded != null ? deviationExceeded.equals(bs.deviationExceeded) : bs.deviationExceeded == null;
    }

    @Override
    public int hashCode() {
        return deviationExceeded != null ? deviationExceeded.hashCode() : 0;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(getClass().getSimpleName()).omitNullValues()
                .add("deviationExceeded", deviationExceeded)
                .toString();
    }
}
