package me.caosh.condition.domain.dto.order;

/**
 * Created by caosh on 2017/8/26.
 */
public class SellSignalDTO implements TradeSignalDTO {
    private static final long serialVersionUID = 1L;

    @Override
    public void accept(TradeSignalDTOVisitor visitor) {
        visitor.visitSellSignalDTO(this);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
