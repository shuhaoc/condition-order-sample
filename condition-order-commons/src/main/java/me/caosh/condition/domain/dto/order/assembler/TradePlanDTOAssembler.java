package me.caosh.condition.domain.dto.order.assembler;

import me.caosh.condition.domain.dto.order.TradePlanDTO;
import me.caosh.condition.domain.model.order.constant.ExchangeType;
import me.caosh.condition.domain.model.order.plan.*;

/**
 * Created by caosh on 2017/8/18.
 */
public class TradePlanDTOAssembler {
    private TradePlanDTOAssembler() {
    }

    public static TradePlanDTO fromDomain(TradePlan tradePlan) {
        TradePlanDTO tradePlanDTO = new TradePlanDTO();

        tradePlan.accept(new TradePlanVisitor() {
            @Override
            public void visitSingleDirectionTradePlan(SingleDirectionTradePlan singleDirectionTradePlan) {
                tradePlanDTO.setExchangeType(singleDirectionTradePlan.getExchangeType().getValue());
                tradePlanDTO.setEntrustStrategy(singleDirectionTradePlan.getEntrustStrategy().getValue());
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
                tradePlanDTO.setNumber(tradeNumberDirect.getNumber());
            }

            @Override
            public void visitTradeNumberByAmount(TradeNumberByAmount tradeNumberByAmount) {
                tradePlanDTO.setEntrustAmount(tradeNumberByAmount.getAmount());
            }
        });

        return tradePlanDTO;
    }

    public static TradePlan toDomain(TradePlanDTO tradePlanDTO) {
        return TradePlanFactory.getInstance().create(tradePlanDTO.getExchangeType(), tradePlanDTO.getEntrustStrategy(),
                tradePlanDTO.getEntrustMethod(), tradePlanDTO.getNumber(), tradePlanDTO.getEntrustAmount());
    }
}
