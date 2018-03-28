package hbec.commons.domain.intellitrade.signal;

import me.caosh.autoasm.RuntimeType;

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
}
