package hbec.commons.domain.intellitrade.signal;

import com.google.common.base.MoreObjects;
import hbec.intellitrade.strategy.domain.signal.Sell;
import hbec.intellitrade.strategy.domain.signal.SellBuilder;
import me.caosh.autoasm.MappedClass;

/**
 * Created by caosh on 2017/8/26.
 */
@MappedClass(value = Sell.class, builderClass = SellBuilder.class)
public class SellSignalDTO implements SignalDTO {
    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(SellSignalDTO.class).omitNullValues()
                .toString();
    }
}
