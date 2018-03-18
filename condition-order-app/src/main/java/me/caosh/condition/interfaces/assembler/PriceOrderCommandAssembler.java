package me.caosh.condition.interfaces.assembler;

import hbec.intellitrade.common.security.SecurityInfoBuilder;
import hbec.intellitrade.condorder.domain.OrderState;
import hbec.intellitrade.condorder.domain.TradeCustomerInfo;
import hbec.intellitrade.condorder.domain.TradeCustomerInfoBuilder;
import hbec.intellitrade.condorder.domain.orders.ConditionOrderBuilder;
import hbec.intellitrade.condorder.domain.orders.price.PriceOrder;
import hbec.intellitrade.condorder.domain.strategyinfo.NativeStrategyInfo;
import me.caosh.autoasm.AutoAssemblers;
import me.caosh.condition.interfaces.command.PriceOrderCreateCommand;
import me.caosh.condition.interfaces.command.PriceOrderUpdateCommand;

/**
 * Created by caosh on 2017/8/9.
 */
public class PriceOrderCommandAssembler {
    public static PriceOrder assemblePriceOrder(Long orderId,
                                                TradeCustomerInfo tradeCustomerInfo,
                                                PriceOrderCreateCommand command) {
        TradeCustomerInfoBuilder customerInfoBuilder = AutoAssemblers.getDefault()
                                                                     .disassemble(tradeCustomerInfo,
                                                                                  TradeCustomerInfoBuilder.class);

        return (PriceOrder) AutoAssemblers.getDefault()
                                          .useBuilder(new ConditionOrderBuilder())
                                          .disassemble(command)
                                          .getConvertibleBuilder()
                                          .setOrderId(orderId)
                                          .setCustomer(customerInfoBuilder)
                                          .setOrderState(OrderState.ACTIVE)
                                          .setStrategyInfo(new ConditionOrderBuilder.StrategyInfoBuilder()
                                                                   .setStrategyType(NativeStrategyInfo.PRICE))
                                          .build();
    }

    public static PriceOrder mergePriceOrder(PriceOrder oldPriceOrder, PriceOrderUpdateCommand command) {
        TradeCustomerInfoBuilder customerInfoBuilder = AutoAssemblers.getDefault()
                                                                     .disassemble(oldPriceOrder.getCustomer(),
                                                                                  TradeCustomerInfoBuilder.class);
        SecurityInfoBuilder securityInfoBuilder = AutoAssemblers.getDefault()
                                                                .disassemble(oldPriceOrder.getSecurityInfo(),
                                                                             SecurityInfoBuilder.class);

        return (PriceOrder) AutoAssemblers.getDefault().useBuilder(new ConditionOrderBuilder())
                                          .disassemble(command)
                                          .getConvertibleBuilder()
                                          .setOrderId(oldPriceOrder.getOrderId())
                                          .setCustomer(customerInfoBuilder)
                                          .setSecurityInfo(securityInfoBuilder)
                                          .setOrderState(OrderState.ACTIVE)
                                          .setStrategyInfo(new ConditionOrderBuilder.StrategyInfoBuilder()
                                                                   .setStrategyType(NativeStrategyInfo.PRICE))
                                          .build();
    }

    private PriceOrderCommandAssembler() {
    }
}
