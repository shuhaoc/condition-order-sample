package me.caosh.condition.interfaces.assembler;

import hbec.intellitrade.common.security.SecurityInfoBuilder;
import hbec.intellitrade.conditionorder.domain.OrderState;
import hbec.intellitrade.conditionorder.domain.TradeCustomerInfo;
import hbec.intellitrade.conditionorder.domain.TradeCustomerInfoBuilder;
import hbec.intellitrade.conditionorder.domain.orders.price.PriceOrder;
import hbec.intellitrade.conditionorder.domain.orders.price.PriceOrderBuilder;
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
                                          .useBuilder(new PriceOrderBuilder())
                                          .disassemble(command)
                                          .getConvertibleBuilder()
                                          .setOrderId(orderId)
                                          .setCustomer(customerInfoBuilder)
                                          .setOrderState(OrderState.ACTIVE)
                                          .build();
    }

    public static PriceOrder mergePriceOrder(PriceOrder oldPriceOrder, PriceOrderUpdateCommand command) {
        TradeCustomerInfoBuilder customerInfoBuilder = AutoAssemblers.getDefault()
                                                                     .disassemble(oldPriceOrder.getCustomer(),
                                                                                  TradeCustomerInfoBuilder.class);
        SecurityInfoBuilder securityInfoBuilder = AutoAssemblers.getDefault()
                                                                .disassemble(oldPriceOrder.getSecurityInfo(),
                                                                             SecurityInfoBuilder.class);

        return (PriceOrder) AutoAssemblers.getDefault().useBuilder(new PriceOrderBuilder())
                                          .disassemble(command)
                                          .getConvertibleBuilder()
                                          .setOrderId(oldPriceOrder.getOrderId())
                                          .setCustomer(customerInfoBuilder)
                                          .setSecurityInfo(securityInfoBuilder)
                                          .setOrderState(OrderState.ACTIVE)
                                          .build();
    }

    private PriceOrderCommandAssembler() {
    }
}
