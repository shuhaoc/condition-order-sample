package me.caosh.condition.domain.dto.order.assembler;

import me.caosh.condition.domain.dto.order.TradePlanDTO;
import me.caosh.condition.domain.model.order.constant.EntrustStrategy;
import me.caosh.condition.domain.model.order.constant.ExchangeType;
import me.caosh.condition.domain.model.order.plan.*;
import me.caosh.condition.domain.model.share.ValuedEnumUtil;

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
        ExchangeType exchangeType = ValuedEnumUtil.valueOf(tradePlanDTO.getExchangeType(), ExchangeType.class);
        EntrustStrategy entrustStrategy = ValuedEnumUtil.valueOf(tradePlanDTO.getEntrustStrategy(), EntrustStrategy.class);
        TradeNumber tradeNumber = TradeNumberFactory.getInstance()
                .create(tradePlanDTO.getEntrustMethod(), tradePlanDTO.getNumber(), tradePlanDTO.getEntrustAmount());
        return new SingleDirectionTradePlan(exchangeType, entrustStrategy, tradeNumber);
    }
}
