package me.caosh.condition.domain.dto.order.assembler;

import com.google.common.base.Preconditions;
import me.caosh.condition.domain.dto.order.BuySignalDTO;
import me.caosh.condition.domain.dto.order.CacheSyncSignalDTO;
import me.caosh.condition.domain.dto.order.BsSignalDTO;
import me.caosh.condition.domain.dto.order.SellSignalDTO;
import me.caosh.condition.domain.dto.order.SignalDTO;
import me.caosh.condition.domain.dto.order.TradeSignalDTOVisitor;
import hbec.intellitrade.strategy.domain.signal.Signal;
import hbec.intellitrade.strategy.domain.signal.Signals;

/**
 * Created by caosh on 2017/8/14.
 *
 * @author caoshuhao@touker.com
 */
public class TradeSignalBuilder implements TradeSignalDTOVisitor {

    private Signal signal;

    public TradeSignalBuilder(SignalDTO signalDTO) {
        signalDTO.accept(this);
    }

    public Signal build() {
        return Preconditions.checkNotNull(signal);
    }

    @Override
    public void visitGeneralSignalDTO(BsSignalDTO bsSignalDTO) {
        this.signal = Signals.buyOrSell();
    }

    @Override
    public void visitCacheSyncDTO(CacheSyncSignalDTO cacheSyncSignalDTO) {
        this.signal = Signals.cacheSync();
    }

    @Override
    public void visitBuySignalDTO(BuySignalDTO buySignalDTO) {
        this.signal = Signals.buy();
    }

    @Override
    public void visitSellSignalDTO(SellSignalDTO sellSignalDTO) {
        this.signal = Signals.sell();
    }
}
