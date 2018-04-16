package me.caosh.condition.interfaces.assembler;

import hbec.intellitrade.conditionorder.domain.OrderState;
import hbec.intellitrade.conditionorder.domain.TradeCustomerInfo;
import hbec.intellitrade.conditionorder.domain.TradeCustomerInfoBuilder;
import hbec.intellitrade.conditionorder.domain.orders.turnpoint.TurnPointCondition;
import hbec.intellitrade.conditionorder.domain.orders.turnpoint.TurnPointOrder;
import hbec.intellitrade.conditionorder.domain.orders.turnpoint.TurnPointOrderBuilder;
import hbec.intellitrade.conditionorder.domain.tradeplan.BaseTradePlan;
import hbec.intellitrade.conditionorder.domain.tradeplan.EntrustStrategy;
import hbec.intellitrade.conditionorder.domain.tradeplan.OfferedPriceTradePlan;
import hbec.intellitrade.conditionorder.domain.tradeplan.TradeNumber;
import hbec.intellitrade.conditionorder.domain.tradeplan.TradeNumberFactory;
import hbec.intellitrade.strategy.domain.factor.BinaryFactorType;
import hbec.intellitrade.strategy.domain.factor.CompareOperator;
import hbec.intellitrade.trade.domain.ExchangeType;
import me.caosh.autoasm.AutoAssemblers;
import me.caosh.condition.interfaces.command.TurnPointOrderCreateCommand;
import me.caosh.condition.interfaces.command.TurnPointOrderUpdateCommand;

/**
 * Created by caosh on 2017/8/9.
 */
public class TurnPointOrderCommandAssembler {
    public static TurnPointOrder assemble(Long orderId,
                                          TradeCustomerInfo tradeCustomerInfo,
                                          TurnPointOrderCreateCommand command) {
        TradeCustomerInfoBuilder customerInfoBuilder = AutoAssemblers.getDefault()
                                                                     .disassemble(tradeCustomerInfo,
                                                                                  TradeCustomerInfoBuilder.class);
        return (TurnPointOrder) AutoAssemblers.getDefault()
                                              .useBuilder(new TurnPointOrderBuilder())
                                              .disassemble(command)
                                              .getConvertibleBuilder()
                                              .setOrderId(orderId)
                                              .setCustomer(customerInfoBuilder)
                                              .setOrderState(OrderState.ACTIVE)
                                              .build();
    }

    public static TurnPointOrder merge(TurnPointOrder oldOrder, TurnPointOrderUpdateCommand command) {
        OrderState orderState = OrderState.ACTIVE;
        TurnPointCondition turnPointCondition = new TurnPointCondition(CompareOperator.LE,
                                                                       command.getBreakPrice(),
                                                                       BinaryFactorType.PERCENT,
                                                                       command.getTurnUpPercent(),
                                                                       null,
                                                                       false);
        EntrustStrategy entrustStrategy = AutoAssemblers.getDefault()
                                                        .disassemble(command.getEntrustStrategy(),
                                                                     EntrustStrategy.class);
        TradeNumber tradeNumber = TradeNumberFactory.getInstance()
                                                    .create(command.getEntrustMethod(),
                                                            command.getEntrustNumber(),
                                                            command.getEntrustAmount());
        BaseTradePlan tradePlan = new OfferedPriceTradePlan(ExchangeType.BUY, entrustStrategy, tradeNumber);
        return new TurnPointOrder(oldOrder.getOrderId(), oldOrder.getCustomer(), orderState, oldOrder.getSecurityInfo(),
                                  turnPointCondition, tradePlan);
    }

    private TurnPointOrderCommandAssembler() {
    }
}
