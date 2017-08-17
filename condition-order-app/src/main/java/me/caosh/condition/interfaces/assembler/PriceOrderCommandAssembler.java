package me.caosh.condition.interfaces.assembler;

import me.caosh.condition.domain.model.constants.SecurityExchange;
import me.caosh.condition.domain.model.market.SecurityInfo;
import me.caosh.condition.domain.model.constants.SecurityType;
import me.caosh.condition.domain.model.order.*;
import me.caosh.condition.domain.model.order.constant.CompareCondition;
import me.caosh.condition.domain.model.order.constant.EntrustStrategy;
import me.caosh.condition.domain.model.order.constant.ExchangeType;
import me.caosh.condition.domain.model.order.constant.OrderState;
import me.caosh.condition.domain.model.order.plan.TradeNumberDirect;
import me.caosh.condition.domain.model.order.plan.TradePlan;
import me.caosh.condition.domain.model.order.price.PriceCondition;
import me.caosh.condition.domain.model.order.price.PriceOrder;
import me.caosh.condition.domain.model.share.ValuedEnumUtil;
import me.caosh.condition.interfaces.command.PriceOrderCreateCommand;
import me.caosh.condition.interfaces.command.PriceOrderUpdateCommand;

import java.math.BigDecimal;

/**
 * Created by caosh on 2017/8/9.
 */
public class PriceOrderCommandAssembler {
    public static PriceOrder assemblePriceOrder(Long orderId, TradeCustomerIdentity customerIdentity, PriceOrderCreateCommand command) {
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
        TradePlan tradePlan = new TradePlan(exchangeType, entrustStrategy, new TradeNumberDirect(command.getEntrustNumber()));
        return new PriceOrder(orderId, customerIdentity, false, orderState, securityInfo, priceCondition, tradePlan);
    }

    public static PriceOrder mergePriceOrder(PriceOrder oldPriceOrder, PriceOrderUpdateCommand command) {
        OrderState orderState = OrderState.ACTIVE;
        CompareCondition compareCondition = ValuedEnumUtil.valueOf(command.getCompareCondition(), CompareCondition.class);
        BigDecimal targetPrice = command.getTargetPrice();
        PriceCondition priceCondition = new PriceCondition(compareCondition, targetPrice);
        ExchangeType exchangeType = ValuedEnumUtil.valueOf(command.getExchangeType(), ExchangeType.class);
        EntrustStrategy entrustStrategy = ValuedEnumUtil.valueOf(command.getEntrustStrategy(), EntrustStrategy.class);
        TradePlan tradePlan = new TradePlan(exchangeType, entrustStrategy, new TradeNumberDirect(command.getEntrustNumber()));
        return new PriceOrder(oldPriceOrder.getOrderId(), oldPriceOrder.getCustomerIdentity(), false, orderState,
                oldPriceOrder.getSecurityInfo(), priceCondition, tradePlan);
    }

    private PriceOrderCommandAssembler() {
    }
}
