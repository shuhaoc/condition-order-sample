package hbec.commons.domain.intellitrade.signal;

import com.google.common.base.MoreObjects;
import hbec.intellitrade.strategy.domain.signal.Buy;
import hbec.intellitrade.strategy.domain.signal.BuyBuilder;
import me.caosh.autoasm.MappedClass;

/**
 * Created by caosh on 2017/8/26.
 */
@MappedClass(value = Buy.class, builderClass = BuyBuilder.class)
public class BuySignalDTO implements SignalDTO {
    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(BuySignalDTO.class).omitNullValues()
                .toString();
    }
}
