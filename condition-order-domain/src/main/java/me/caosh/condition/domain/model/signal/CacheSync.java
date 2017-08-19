package me.caosh.condition.domain.model.signal;

/**
 * Created by caosh on 2017/8/19.
 */
public class CacheSync extends AbstractTradeSignal {
    @Override
    public void accept(TradeSignalVisitor visitor) {
        visitor.visitCacheSync(this);
    }
}
