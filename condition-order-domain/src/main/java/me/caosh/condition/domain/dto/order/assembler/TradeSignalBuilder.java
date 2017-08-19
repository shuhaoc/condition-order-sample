package me.caosh.condition.domain.dto.order.assembler;

import com.google.common.base.Preconditions;
import me.caosh.condition.domain.dto.order.CacheSyncSignalDTO;
import me.caosh.condition.domain.dto.order.GeneralSignalDTO;
import me.caosh.condition.domain.dto.order.TradeSignalDTO;
import me.caosh.condition.domain.dto.order.TradeSignalDTOVisitor;
import me.caosh.condition.domain.model.signal.SignalFactory;
import me.caosh.condition.domain.model.signal.TradeSignal;

/**
 * Created by caosh on 2017/8/14.
 *
 * @author caoshuhao@touker.com
 */
public class TradeSignalBuilder implements TradeSignalDTOVisitor {

    private TradeSignal tradeSignal;

    public TradeSignalBuilder(TradeSignalDTO tradeSignalDTO) {
        tradeSignalDTO.accept(this);
    }

    public TradeSignal build() {
        return Preconditions.checkNotNull(tradeSignal);
    }

    @Override
    public void visitGeneralSignalDTO(GeneralSignalDTO generalSignalDTO) {
        this.tradeSignal = SignalFactory.getInstance().general();
    }

    @Override
    public void visitCacheSyncDTO(CacheSyncSignalDTO cacheSyncSignalDTO) {
        this.tradeSignal = SignalFactory.getInstance().cacheSync();
    }
}
