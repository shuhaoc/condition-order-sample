package me.caosh.condition.interfaces.assembler;

import hbec.intellitrade.common.security.SecurityInfoBuilder;
import hbec.intellitrade.condorder.domain.OrderState;
import hbec.intellitrade.condorder.domain.TradeCustomerInfo;
import hbec.intellitrade.condorder.domain.orders.PriceOrder;
import hbec.intellitrade.condorder.domain.orders.PriceOrderBuilder;
import me.caosh.autoasm.AutoAssemblers;
import me.caosh.condition.interfaces.command.PriceOrderCreateCommand;
import me.caosh.condition.interfaces.command.PriceOrderUpdateCommand;

/**
 * Created by caosh on 2017/8/9.
 */
public class PriceOrderCommandAssembler {
    public static PriceOrder assemblePriceOrder(Long orderId, TradeCustomerInfo tradeCustomerInfo, PriceOrderCreateCommand command) {
        return AutoAssemblers.getDefault().useBuilder(new PriceOrderBuilder())
                .disassemble(command)
                .getConvertibleBuilder()
                .setOrderId(orderId)
                .setTradeCustomerInfo(tradeCustomerInfo)
                .setOrderState(OrderState.ACTIVE)
                .build();
    }

    public static PriceOrder mergePriceOrder(PriceOrder oldPriceOrder, PriceOrderUpdateCommand command) {
        SecurityInfoBuilder securityInfoBuilder = AutoAssemblers.getDefault().disassemble(oldPriceOrder.getSecurityInfo(),
                SecurityInfoBuilder.class);

        return AutoAssemblers.getDefault().useBuilder(new PriceOrderBuilder())
                .disassemble(command)
                .getConvertibleBuilder()
                .setOrderId(oldPriceOrder.getOrderId())
                .setTradeCustomerInfo(oldPriceOrder.getCustomer())
                .setSecurityInfo(securityInfoBuilder)
                .setOrderState(oldPriceOrder.getOrderState())
                .build();
    }

    private PriceOrderCommandAssembler() {
    }
}
