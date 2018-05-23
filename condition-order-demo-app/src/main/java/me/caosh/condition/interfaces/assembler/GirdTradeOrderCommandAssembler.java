package me.caosh.condition.interfaces.assembler;

import hbec.intellitrade.conditionorder.domain.OrderState;
import hbec.intellitrade.conditionorder.domain.TradeCustomerInfo;
import hbec.intellitrade.conditionorder.domain.TradeCustomerInfoBuilder;
import hbec.intellitrade.conditionorder.domain.orders.grid.GridTradeOrder;
import hbec.intellitrade.conditionorder.domain.orders.grid.GridTradeOrderBuilder;
import me.caosh.autoasm.AutoAssemblers;
import me.caosh.condition.interfaces.command.GridTradeOrderCreateCommand;
import me.caosh.condition.interfaces.command.GridTradeOrderUpdateCommand;

/**
 * Created by caosh on 2017/8/9.
 */
public class GirdTradeOrderCommandAssembler {
    public static GridTradeOrder assemble(Long orderId, TradeCustomerInfo tradeCustomerInfo, GridTradeOrderCreateCommand command) {
        TradeCustomerInfoBuilder customerInfoBuilder = AutoAssemblers.getDefault()
                .disassemble(tradeCustomerInfo, TradeCustomerInfoBuilder.class);
        return (GridTradeOrder) AutoAssemblers.getDefault()
                .useBuilder(new GridTradeOrderBuilder())
                .disassemble(command)
                .getConvertibleBuilder()
                .setOrderId(orderId)
                .setCustomer(customerInfoBuilder)
                .setOrderState(OrderState.ACTIVE)
                .build();
    }

    public static GridTradeOrder merge(GridTradeOrder oldOrder, GridTradeOrderUpdateCommand command) {
        return null;
    }

    private GirdTradeOrderCommandAssembler() {
    }
}
