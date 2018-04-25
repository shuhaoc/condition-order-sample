package me.caosh.condition.interfaces.assembler;

import hbec.intellitrade.common.security.SecurityInfoBuilder;
import hbec.intellitrade.conditionorder.domain.OrderState;
import hbec.intellitrade.conditionorder.domain.TradeCustomerInfo;
import hbec.intellitrade.conditionorder.domain.TradeCustomerInfoBuilder;
import hbec.intellitrade.conditionorder.domain.orders.time.TimeOrder;
import hbec.intellitrade.conditionorder.domain.orders.time.TimeOrderBuilder;
import me.caosh.autoasm.AutoAssemblers;
import me.caosh.condition.interfaces.command.TimeOrderCreateCommand;
import me.caosh.condition.interfaces.command.TimeOrderUpdateCommand;

/**
 * Created by caosh on 2017/8/9.
 */
public class TimeOrderCommandAssembler {
    public static TimeOrder assemble(Long orderId,
            TradeCustomerInfo tradeCustomerInfo,
            TimeOrderCreateCommand command) {
        TradeCustomerInfoBuilder customerInfoBuilder = AutoAssemblers.getDefault()
                .disassemble(tradeCustomerInfo, TradeCustomerInfoBuilder.class);

        return (TimeOrder) AutoAssemblers.getDefault()
                .useBuilder(new TimeOrderBuilder())
                .disassemble(command)
                .getConvertibleBuilder()
                .setOrderId(orderId)
                .setCustomer(customerInfoBuilder)
                .setOrderState(OrderState.ACTIVE)
                .build();
    }

    public static TimeOrder merge(TimeOrder oldOrder, TimeOrderUpdateCommand command) {
        TradeCustomerInfoBuilder customerInfoBuilder = AutoAssemblers.getDefault()
                .disassemble(oldOrder.getCustomer(), TradeCustomerInfoBuilder.class);
        SecurityInfoBuilder securityInfoBuilder = AutoAssemblers.getDefault()
                .disassemble(oldOrder.getSecurityInfo(), SecurityInfoBuilder.class);

        return (TimeOrder) AutoAssemblers.getDefault().useBuilder(new TimeOrderBuilder())
                .disassemble(command)
                .getConvertibleBuilder()
                .setOrderId(oldOrder.getOrderId())
                .setCustomer(customerInfoBuilder)
                .setSecurityInfo(securityInfoBuilder)
                .setOrderState(OrderState.ACTIVE)
                .build();
    }

    private TimeOrderCommandAssembler() {
    }
}
