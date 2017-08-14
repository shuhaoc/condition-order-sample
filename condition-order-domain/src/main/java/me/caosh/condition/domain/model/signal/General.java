package me.caosh.condition.domain.model.signal;

/**
 * Created by caosh on 2017/8/1.
 */
public class General extends AbstractTradeSignal {
    @Override
    public void accept(TradeSignalVisitor visitor) {
        visitor.visitGeneral(this);
    }
}
