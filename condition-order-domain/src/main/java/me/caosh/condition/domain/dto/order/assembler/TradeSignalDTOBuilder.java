package me.caosh.condition.domain.dto.order.assembler;

import com.google.common.base.Preconditions;
import me.caosh.condition.domain.dto.order.GeneralSignalDTO;
import me.caosh.condition.domain.dto.order.TradeSignalDTO;
import me.caosh.condition.domain.model.signal.General;
import me.caosh.condition.domain.model.signal.None;
import me.caosh.condition.domain.model.signal.SignalFactory;
import me.caosh.condition.domain.model.signal.TradeSignal;
import me.caosh.condition.domain.model.signal.TradeSignalVisitor;

/**
 * Created by caosh on 2017/8/14.
 *
 * @author caoshuhao@touker.com
 */
public class TradeSignalDTOBuilder implements TradeSignalVisitor {

    private TradeSignalDTO tradeSignalDTO;

    public TradeSignalDTOBuilder(TradeSignal tradeSignal) {
        tradeSignal.accept(this);
    }

    public TradeSignalDTO build() {
        return Preconditions.checkNotNull(tradeSignalDTO);
    }

    @Override
    public void visitNone(None none) {
        throw new UnsupportedOperationException("visitNone");
    }

    @Override
    public void visitGeneral(General general) {
        this.tradeSignalDTO = new GeneralSignalDTO();
    }
}
