package me.caosh.condition.domain.dto.order.assembler;

import hbec.intellitrade.common.security.SecurityInfo;
import me.caosh.condition.domain.dto.order.TradePlanDTO;
import me.caosh.condition.domain.model.order.plan.AutoPurchaseTradePlan;
import me.caosh.condition.domain.model.order.plan.BasicTradePlan;
import me.caosh.condition.domain.model.order.plan.DoubleDirectionTradePlan;
import me.caosh.condition.domain.model.order.plan.TradeNumberByAmount;
import me.caosh.condition.domain.model.order.plan.TradeNumberDirect;
import me.caosh.condition.domain.model.order.plan.TradeNumberVisitor;
import me.caosh.condition.domain.model.order.plan.TradePlan;
import me.caosh.condition.domain.model.order.plan.TradePlanFactory;
import me.caosh.condition.domain.model.order.plan.TradePlanVisitor;

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
                tradePlanDTO.setNumber(tradeNumberDirect.getNumber());
            }

            @Override
            public void visitTradeNumberByAmount(TradeNumberByAmount tradeNumberByAmount) {
                tradePlanDTO.setEntrustAmount(tradeNumberByAmount.getAmount());
            }
        });

        return tradePlanDTO;
    }

    public static TradePlan toDomain(SecurityInfo securityInfo, TradePlanDTO tradePlanDTO) {
        return TradePlanFactory.getInstance().create(securityInfo, tradePlanDTO.getExchangeType(), tradePlanDTO.getEntrustStrategy(),
                tradePlanDTO.getEntrustMethod(), tradePlanDTO.getNumber(), tradePlanDTO.getEntrustAmount());
    }
}
