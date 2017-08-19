package me.caosh.condition.domain.model.signal;

/**
 * Created by caosh on 2017/8/14.
 *
 * @author caoshuhao@touker.com
 */
public interface TradeSignalVisitor {
    void visitNone(None none);
    void visitGeneral(General general);
    void visitCacheSync(CacheSync cacheSync);
}
