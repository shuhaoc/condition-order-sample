package me.caosh.condition.interfaces.assembler;

import hbec.intellitrade.common.security.SecurityInfoBuilder;
import hbec.intellitrade.conditionorder.domain.OrderState;
import hbec.intellitrade.conditionorder.domain.TradeCustomerInfo;
import hbec.intellitrade.conditionorder.domain.TradeCustomerInfoBuilder;
import hbec.intellitrade.conditionorder.domain.orders.turnpoint.TurnPointOrder;
import hbec.intellitrade.conditionorder.domain.orders.turnpoint.TurnPointOrderBuilder;
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
                .disassemble(tradeCustomerInfo, TradeCustomerInfoBuilder.class);
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
        TradeCustomerInfoBuilder customerInfoBuilder = AutoAssemblers.getDefault()
                .disassemble(oldOrder.getCustomer(), TradeCustomerInfoBuilder.class);
        SecurityInfoBuilder securityInfoBuilder = AutoAssemblers.getDefault()
                .disassemble(oldOrder.getSecurityInfo(), SecurityInfoBuilder.class);

        return (TurnPointOrder) AutoAssemblers.getDefault().useBuilder(new TurnPointOrderBuilder())
                .disassemble(command)
                .getConvertibleBuilder()
                .setOrderId(oldOrder.getOrderId())
                .setCustomer(customerInfoBuilder)
                .setSecurityInfo(securityInfoBuilder)
                .setOrderState(OrderState.ACTIVE)
                .build();
    }

    private TurnPointOrderCommandAssembler() {
    }
}
