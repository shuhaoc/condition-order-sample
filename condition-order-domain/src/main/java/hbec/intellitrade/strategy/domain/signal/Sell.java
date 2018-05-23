package hbec.intellitrade.strategy.domain.signal;

/**
 * Created by caosh on 2017/8/1.
 */
public class Sell extends BS {
    Sell() {
    }

    Sell(boolean deviationExceeded) {
        super(deviationExceeded);
    }

    @Override
    public TradeSignal withDeviationExceeded() {
        return new Sell(true);
    }

}
