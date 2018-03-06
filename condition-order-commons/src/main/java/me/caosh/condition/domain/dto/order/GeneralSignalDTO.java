package me.caosh.condition.domain.dto.order;

import me.caosh.autoasm.MappedClass;
import hbec.intellitrade.strategy.domain.signal.BS;

/**
 * Created by caosh on 2017/8/14.
 *
 * @author caoshuhao@touker.com
 */
@MappedClass(BS.class)
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
