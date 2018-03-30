package hbec.intellitrade.strategy.domain.signal;

/**
 * 突破底线价信号
 *
 * @author caoshuhao@touker.com
 * @date 2018/3/18
 */
public class CrossBaseline extends AbstractSignal implements TradeSignal {
    @Override
    public boolean getDeviationExceeded() {
        return false;
    }

    @Override
    public TradeSignal withDeviationExceeded() {
        throw new UnsupportedOperationException("Cross baseline signal does not support deviation control");
    }
}
