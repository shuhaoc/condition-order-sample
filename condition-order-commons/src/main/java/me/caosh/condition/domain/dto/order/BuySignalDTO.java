package me.caosh.condition.domain.dto.order;

import com.google.common.base.MoreObjects;
import me.caosh.autoasm.MappedClass;
import hbec.intellitrade.strategy.domain.signal.Buy;

/**
 * Created by caosh on 2017/8/26.
 */
@MappedClass(Buy.class)
public class BuySignalDTO implements SignalDTO {
    private static final long serialVersionUID = 1L;

    @Override
    public void accept(TradeSignalDTOVisitor visitor) {
        visitor.visitBuySignalDTO(this);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(BuySignalDTO.class).omitNullValues()
                .toString();
    }
}
