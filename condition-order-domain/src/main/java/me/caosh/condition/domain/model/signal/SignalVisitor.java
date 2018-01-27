package me.caosh.condition.domain.model.signal;

/**
 * Created by caosh on 2017/8/14.
 *
 * @author caoshuhao@touker.com
 */
public interface SignalVisitor {
    void visitNone(None none);

    void visitBS(BS bs);

    void visitCacheSync(CacheSync cacheSync);

    void visitBuy(Buy buy);

    void visitSell(Sell sell);
}
