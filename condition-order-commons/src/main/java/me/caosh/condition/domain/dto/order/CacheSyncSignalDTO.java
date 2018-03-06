package me.caosh.condition.domain.dto.order;

import me.caosh.autoasm.MappedClass;
import hbec.intellitrade.strategy.domain.signal.CacheSync;

/**
 * Created by caosh on 2017/8/19.
 */
@MappedClass(CacheSync.class)
public class CacheSyncSignalDTO implements TradeSignalDTO {
    private static final long serialVersionUID = 1L;

    @Override
    public void accept(TradeSignalDTOVisitor visitor) {
        visitor.visitCacheSyncDTO(this);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
