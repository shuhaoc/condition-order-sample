package me.caosh.condition.interfaces.assembler;

import me.caosh.condition.domain.model.market.SecurityExchange;
import me.caosh.condition.domain.model.market.SecurityInfo;
import me.caosh.condition.domain.model.market.SecurityType;
import me.caosh.condition.domain.model.order.*;
import me.caosh.condition.domain.model.share.ValuedEnumUtil;
import me.caosh.condition.interfaces.command.PriceOrderCreateCommand;
import me.caosh.condition.interfaces.command.PriceOrderUpdateCommand;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Created by caosh on 2017/8/9.
 */
public class PriceOrderCommandAssembler {
    public static PriceOrder assemblePriceOrder(Long orderId, PriceOrderCreateCommand command) {
        OrderState orderState = OrderState.ACTIVE;
        SecurityType securityType = ValuedEnumUtil.valueOf(command.getSecurityType(), SecurityType.class);
        SecurityExchange securityExchange = SecurityExchange.valueOf(command.getSecurityExchange());
        SecurityInfo securityInfo = new SecurityInfo(securityType, command.getSecurityCode(), securityExchange,
                command.getSecurityName());
        CompareCondition compareCondition = ValuedEnumUtil.valueOf(command.getCompareCondition(), CompareCondition.class);
        BigDecimal targetPrice = command.getTargetPrice();
        PriceCondition priceCondition = new PriceCondition(compareCondition, targetPrice);
        ExchangeType exchangeType = ValuedEnumUtil.valueOf(command.getExchangeType(), ExchangeType.class);
        EntrustStrategy entrustStrategy = ValuedEnumUtil.valueOf(command.getEntrustStrategy(), EntrustStrategy.class);
        TradePlan tradePlan = new TradePlan(exchangeType, entrustStrategy, command.getEntrustNumber());
        return new PriceOrder(orderId, orderState, securityInfo, priceCondition, tradePlan);
    }

    public static PriceOrder mergePriceOrder(PriceOrder oldPriceOrder, PriceOrderUpdateCommand command) {
        OrderState orderState = OrderState.ACTIVE;
        CompareCondition compareCondition = ValuedEnumUtil.valueOf(command.getCompareCondition(), CompareCondition.class);
        BigDecimal targetPrice = command.getTargetPrice();
        PriceCondition priceCondition = new PriceCondition(compareCondition, targetPrice);
        ExchangeType exchangeType = ValuedEnumUtil.valueOf(command.getExchangeType(), ExchangeType.class);
        EntrustStrategy entrustStrategy = ValuedEnumUtil.valueOf(command.getEntrustStrategy(), EntrustStrategy.class);
        TradePlan tradePlan = new TradePlan(exchangeType, entrustStrategy, command.getEntrustNumber());
        return new PriceOrder(oldPriceOrder.getOrderId(), orderState, oldPriceOrder.getSecurityInfo(), priceCondition, tradePlan);
    }

    private PriceOrderCommandAssembler() {
    }
}
