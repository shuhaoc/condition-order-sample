package me.caosh.condition.domain.dto.order;

import com.google.common.base.MoreObjects;
import me.caosh.autoasm.MappedClass;
import hbec.intellitrade.strategy.domain.signal.Sell;

/**
 * Created by caosh on 2017/8/26.
 */
@MappedClass(Sell.class)
public class SellSignalDTO implements SignalDTO {
    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(SellSignalDTO.class).omitNullValues()
                .toString();
    }
}
