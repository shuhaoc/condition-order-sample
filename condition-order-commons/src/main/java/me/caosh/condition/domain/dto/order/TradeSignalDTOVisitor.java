package me.caosh.condition.domain.dto.order;

/**
 * Created by caosh on 2017/8/14.
 *
 * @author caoshuhao@touker.com
 */
public interface TradeSignalDTOVisitor {
    void visitGeneralSignalDTO(BsSignalDTO bsSignalDTO);
    void visitCacheSyncDTO(CacheSyncSignalDTO cacheSyncSignalDTO);

    void visitBuySignalDTO(BuySignalDTO buySignalDTO);

    void visitSellSignalDTO(SellSignalDTO sellSignalDTO);
}
