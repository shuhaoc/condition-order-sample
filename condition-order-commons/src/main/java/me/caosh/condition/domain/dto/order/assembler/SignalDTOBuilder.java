package me.caosh.condition.domain.dto.order.assembler;

import com.google.common.base.Preconditions;
import me.caosh.condition.domain.dto.order.*;
import hbec.intellitrade.strategy.domain.signal.*;

/**
 * Created by caosh on 2017/8/14.
 *
 * @author caoshuhao@touker.com
 */
public class SignalDTOBuilder implements SignalVisitor {

    private SignalDTO signalDTO;

    public SignalDTOBuilder(Signal signal) {
        signal.accept(this);
    }

    public SignalDTO build() {
        return Preconditions.checkNotNull(signalDTO);
    }

    @Override
    public void visitNone(None none) {
        throw new UnsupportedOperationException("visitNone");
    }

    @Override
    public void visitBS(BS bs) {
        this.signalDTO = new BsSignalDTO();
    }

    @Override
    public void visitCacheSync(CacheSync cacheSync) {
        this.signalDTO = new CacheSyncSignalDTO();
    }

    @Override
    public void visitBuy(Buy buy) {
        this.signalDTO = new BuySignalDTO();
    }

    @Override
    public void visitSell(Sell sell) {
        this.signalDTO = new SellSignalDTO();
    }
}
