package hbec.intellitrade.strategy.domain.signal;

/**
 * Created by caosh on 2017/8/1.
 */
public final class None extends AbstractSignal implements TradeSignal {
    @Override
    public boolean isValid() {
        return false;
    }

    @Override
    public boolean getDeviationExceeded() {
        return false;
    }

    @Override
    public TradeSignal withDeviationExceeded() {
        return this;
    }

}
