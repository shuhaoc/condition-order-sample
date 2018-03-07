package me.caosh.condition.domain.dto.order;

import hbec.intellitrade.strategy.domain.signal.CacheSync;
import me.caosh.autoasm.RuntimeType;
import hbec.intellitrade.strategy.domain.signal.Signal;

import java.io.Serializable;

/**
 * Created by caosh on 2017/8/14.
 *
 * @author caoshuhao@touker.com
 */
@RuntimeType({
        BsSignalDTO.class,
        BuySignalDTO.class,
        SellSignalDTO.class,
        ExpireSignalDTO.class,
        CacheSyncSignalDTO.class
})
public interface SignalDTO extends Serializable {
    void accept(TradeSignalDTOVisitor visitor);
}
