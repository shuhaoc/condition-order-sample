package me.caosh.condition.domain.model.signal;

/**
 * Created by caosh on 2017/8/1.
 */
public final class None extends AbstractTradeSignal {
    @Override
    public void accept(TradeSignalVisitor visitor) {
        visitor.visitNone(this);
    }
}
