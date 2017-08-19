package me.caosh.condition.domain.dto.order;

/**
 * Created by caosh on 2017/8/14.
 *
 * @author caoshuhao@touker.com
 */
public class GeneralSignalDTO implements TradeSignalDTO {
    private static final long serialVersionUID = 1L;

    @Override
    public void accept(TradeSignalDTOVisitor visitor) {
        visitor.visitGeneralSignalDTO(this);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
