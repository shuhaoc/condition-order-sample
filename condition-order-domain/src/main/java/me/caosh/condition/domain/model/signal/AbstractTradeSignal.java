package me.caosh.condition.domain.model.signal;

/**
 * Created by caosh on 2017/8/14.
 *
 * @author caoshuhao@touker.com
 */
public abstract class AbstractTradeSignal implements TradeSignal {
    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
