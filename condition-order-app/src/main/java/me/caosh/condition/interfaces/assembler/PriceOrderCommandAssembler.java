package me.caosh.condition.interfaces.assembler;

import hbec.intellitrade.common.ValuedEnumUtil;
import hbec.intellitrade.common.security.SecurityExchange;
import hbec.intellitrade.common.security.SecurityInfo;
import hbec.intellitrade.common.security.SecurityType;
import hbec.intellitrade.condorder.domain.OrderState;
import hbec.intellitrade.condorder.domain.TradeCustomerInfo;
import hbec.intellitrade.condorder.domain.orders.PriceOrder;
import hbec.intellitrade.condorder.domain.tradeplan.BasicTradePlan;
import hbec.intellitrade.condorder.domain.tradeplan.EntrustStrategy;
import hbec.intellitrade.condorder.domain.tradeplan.TradeNumber;
import hbec.intellitrade.condorder.domain.tradeplan.TradeNumberFactory;
import hbec.intellitrade.strategy.domain.factor.CompareOperator;
import hbec.intellitrade.strategy.domain.strategies.condition.PriceCondition;
import hbec.intellitrade.trade.domain.ExchangeType;
import me.caosh.condition.interfaces.command.PriceOrderCreateCommand;
import me.caosh.condition.interfaces.command.PriceOrderUpdateCommand;

import java.math.BigDecimal;

/**
 * Created by caosh on 2017/8/9.
 */
public class PriceOrderCommandAssembler {
    public static PriceOrder assemblePriceOrder(Long orderId, TradeCustomerInfo tradeCustomerInfo, PriceOrderCreateCommand command) {
        OrderState orderState = OrderState.ACTIVE;
        SecurityType securityType = ValuedEnumUtil.valueOf(command.getSecurityType(), SecurityType.class);
        SecurityExchange securityExchange = SecurityExchange.valueOf(command.getSecurityExchange());
        SecurityInfo securityInfo = new SecurityInfo(securityType, command.getSecurityCode(), securityExchange,
                command.getSecurityName());
        CompareOperator compareOperator = ValuedEnumUtil.valueOf(command.getCompareCondition(), CompareOperator.class);
        BigDecimal targetPrice = command.getTargetPrice();
        PriceCondition priceCondition = new PriceCondition(compareOperator, targetPrice);
        ExchangeType exchangeType = ValuedEnumUtil.valueOf(command.getExchangeType(), ExchangeType.class);
        EntrustStrategy entrustStrategy = ValuedEnumUtil.valueOf(command.getEntrustStrategy(), EntrustStrategy.class);
        TradeNumber tradeNumber = TradeNumberFactory.getInstance()
                .create(command.getEntrustMethod(), command.getEntrustNumber(), command.getEntrustAmount());
        BasicTradePlan tradePlan = new BasicTradePlan(exchangeType, entrustStrategy, tradeNumber);
        return new PriceOrder(orderId, tradeCustomerInfo, securityInfo, priceCondition, tradePlan, orderState);
    }

    public static PriceOrder mergePriceOrder(PriceOrder oldPriceOrder, PriceOrderUpdateCommand command) {
        OrderState orderState = OrderState.ACTIVE;
        CompareOperator compareOperator = ValuedEnumUtil.valueOf(command.getCompareCondition(), CompareOperator.class);
        BigDecimal targetPrice = command.getTargetPrice();
        PriceCondition priceCondition = new PriceCondition(compareOperator, targetPrice);
        ExchangeType exchangeType = ValuedEnumUtil.valueOf(command.getExchangeType(), ExchangeType.class);
        EntrustStrategy entrustStrategy = ValuedEnumUtil.valueOf(command.getEntrustStrategy(), EntrustStrategy.class);
        TradeNumber tradeNumber = TradeNumberFactory.getInstance()
                .create(command.getEntrustMethod(), command.getEntrustNumber(), command.getEntrustAmount());
        BasicTradePlan tradePlan = new BasicTradePlan(exchangeType, entrustStrategy, tradeNumber);
        return new PriceOrder(oldPriceOrder.getOrderId(), oldPriceOrder.getCustomer(),
                oldPriceOrder.getSecurityInfo(), priceCondition, tradePlan, orderState);
    }

    private PriceOrderCommandAssembler() {
    }
}
