package me.caosh.condition.interfaces.assembler;

import hbec.intellitrade.common.security.SecurityExchange;
import hbec.intellitrade.common.security.SecurityInfo;
import hbec.intellitrade.common.security.SecurityType;
import hbec.intellitrade.condorder.domain.OrderState;
import hbec.intellitrade.condorder.domain.TradeCustomerInfo;
import hbec.intellitrade.condorder.domain.orders.turnpoint.TurnPointCondition;
import hbec.intellitrade.condorder.domain.orders.turnpoint.TurnPointOrder;
import hbec.intellitrade.condorder.domain.tradeplan.*;
import hbec.intellitrade.strategy.domain.factor.CompareOperator;
import hbec.intellitrade.trade.domain.ExchangeType;
import me.caosh.autoasm.AutoAssemblers;
import me.caosh.condition.interfaces.command.TurnUpBuyOrderCreateCommand;
import me.caosh.condition.interfaces.command.TurnUpBuyOrderUpdateCommand;

/**
 * Created by caosh on 2017/8/9.
 */
public class TurnPointOrderCommandAssembler {
    public static TurnPointOrder assemble(Long orderId,
                                          TradeCustomerInfo tradeCustomerInfo,
                                          TurnUpBuyOrderCreateCommand command) {
        OrderState orderState = OrderState.ACTIVE;
        SecurityType securityType = AutoAssemblers.getDefault()
                                                  .disassemble(command.getSecurityType(), SecurityType.class);
        SecurityExchange securityExchange = SecurityExchange.valueOf(command.getSecurityExchange());
        SecurityInfo securityInfo = new SecurityInfo(securityType, command.getSecurityCode(), securityExchange,
                command.getSecurityName());
        TurnPointCondition turnPointCondition = new TurnPointCondition(CompareOperator.LE,
                                                                       command.getBreakPrice(),
                                                                       command.getTurnUpPercent(), false);
        EntrustStrategy entrustStrategy = AutoAssemblers.getDefault()
                                                        .disassemble(command.getEntrustStrategy(),
                                                                     EntrustStrategy.class);
        TradeNumber tradeNumber = TradeNumberFactory.getInstance()
                .create(command.getEntrustMethod(), command.getEntrustNumber(), command.getEntrustAmount());
        BaseTradePlan tradePlan = new OfferedPriceTradePlan(ExchangeType.BUY, entrustStrategy, tradeNumber);
        return new TurnPointOrder(orderId, tradeCustomerInfo, orderState, securityInfo,
                                  turnPointCondition, null, tradePlan);
    }

    public static TurnPointOrder merge(TurnPointOrder oldOrder, TurnUpBuyOrderUpdateCommand command) {
        OrderState orderState = OrderState.ACTIVE;
        TurnPointCondition turnPointCondition = new TurnPointCondition(CompareOperator.LE,
                                                                       command.getBreakPrice(),
                                                                       command.getTurnUpPercent(), false);
//        if (turnPointCondition.isNeedSwap(oldOrder.getTurnPointCondition())) {
//            turnPointCondition.swap(oldOrder.getTurnPointCondition());
//        }
        EntrustStrategy entrustStrategy = AutoAssemblers.getDefault()
                                                        .disassemble(command.getEntrustStrategy(),
                                                                     EntrustStrategy.class);
        TradeNumber tradeNumber = TradeNumberFactory.getInstance()
                .create(command.getEntrustMethod(), command.getEntrustNumber(), command.getEntrustAmount());
        BaseTradePlan tradePlan = new OfferedPriceTradePlan(ExchangeType.BUY, entrustStrategy, tradeNumber);
        return new TurnPointOrder(oldOrder.getOrderId(), oldOrder.getCustomer(), orderState, oldOrder.getSecurityInfo(),
                                  turnPointCondition, null, tradePlan);
    }

    private TurnPointOrderCommandAssembler() {
    }
}
