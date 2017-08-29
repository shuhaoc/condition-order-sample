package me.caosh.condition.domain.dto.order.assembler;

import com.google.common.base.Preconditions;
import me.caosh.condition.domain.dto.order.*;
import me.caosh.condition.domain.model.signal.*;

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

    @Override
    public void visitCacheSync(CacheSync cacheSync) {
        this.tradeSignalDTO = new CacheSyncSignalDTO();
    }

    @Override
    public void visitBuy(Buy buy) {
        this.tradeSignalDTO = new BuySignalDTO();
    }

    @Override
    public void visitSell(Sell sell) {
        this.tradeSignalDTO = new SellSignalDTO();
    }
}
