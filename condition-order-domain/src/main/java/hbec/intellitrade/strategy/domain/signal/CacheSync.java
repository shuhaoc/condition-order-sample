package hbec.intellitrade.strategy.domain.signal;

/**
 * Created by caosh on 2017/8/19.
 */
public class CacheSync extends AbstractSignal {
    @Override
    public void accept(SignalVisitor visitor) {
        visitor.visitCacheSync(this);
    }
}
