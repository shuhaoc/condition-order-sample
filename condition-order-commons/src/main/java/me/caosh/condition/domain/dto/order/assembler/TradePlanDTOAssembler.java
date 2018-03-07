package me.caosh.condition.domain.dto.order.assembler;

import hbec.intellitrade.condorder.domain.tradeplan.AutoPurchaseTradePlan;
import hbec.intellitrade.condorder.domain.tradeplan.BasicTradePlan;
import hbec.intellitrade.condorder.domain.tradeplan.DoubleDirectionTradePlan;
import hbec.intellitrade.condorder.domain.tradeplan.TradeNumberByAmount;
import hbec.intellitrade.condorder.domain.tradeplan.TradeNumberDirect;
import hbec.intellitrade.condorder.domain.tradeplan.TradeNumberVisitor;
import hbec.intellitrade.condorder.domain.tradeplan.TradePlan;
import hbec.intellitrade.condorder.domain.tradeplan.TradePlanFactory;
import hbec.intellitrade.condorder.domain.tradeplan.TradePlanVisitor;
import me.caosh.condition.domain.dto.order.TradePlanDTO;

import java.math.BigDecimal;

/**
 * Created by caosh on 2017/8/18.
 */
public class TradePlanDTOAssembler {
    private TradePlanDTOAssembler() {
    }

    public static TradePlanDTO fromDomain(TradePlan tradePlan) {
        final TradePlanDTO tradePlanDTO = new TradePlanDTO();

        tradePlan.accept(new TradePlanVisitor() {
            @Override
            public void visitSingleDirectionTradePlan(BasicTradePlan basicTradePlan) {
                tradePlanDTO.setExchangeType(basicTradePlan.getExchangeType().getValue());
                tradePlanDTO.setEntrustStrategy(basicTradePlan.getEntrustStrategy().getValue());
            }

            @Override
            public void visitDoubleDirectionTradePlan(DoubleDirectionTradePlan doubleDirectionTradePlan) {
                tradePlanDTO.setExchangeType(TradePlanFactory.DOUBLE_EXCHANGE_TYPE);
                tradePlanDTO.setEntrustStrategy(doubleDirectionTradePlan.getBuyPlan().getEntrustStrategy().getValue());
            }

            @Override
            public void visitAutoPurchaseTradePlan(AutoPurchaseTradePlan autoPurchaseTradePlan) {
                tradePlanDTO.setExchangeType(autoPurchaseTradePlan.getExchangeTypeValue());
                tradePlanDTO.setEntrustStrategy(autoPurchaseTradePlan.getEntrustStrategyValue());
            }
        });

        tradePlanDTO.setEntrustMethod(tradePlan.getTradeNumber().getEntrustMethod().getValue());
        tradePlan.getTradeNumber().accept(new TradeNumberVisitor(){
            @Override
            public void visitTradeNumberDirect(TradeNumberDirect tradeNumberDirect) {
                tradePlanDTO.setNumber(BigDecimal.valueOf(tradeNumberDirect.getNumber()));
            }

            @Override
            public void visitTradeNumberByAmount(TradeNumberByAmount tradeNumberByAmount) {
                tradePlanDTO.setNumber(tradeNumberByAmount.getAmount());
            }
        });

        return tradePlanDTO;
    }

    public static TradePlan toDomain(TradePlanDTO tradePlanDTO) {
        return TradePlanFactory.getInstance().create(tradePlanDTO.getExchangeType(), tradePlanDTO.getEntrustStrategy(),
                tradePlanDTO.getEntrustMethod(), tradePlanDTO.getNumber().intValue(), tradePlanDTO.getNumber());
    }
}
