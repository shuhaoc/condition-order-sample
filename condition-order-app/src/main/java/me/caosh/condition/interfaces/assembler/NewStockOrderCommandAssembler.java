package me.caosh.condition.interfaces.assembler;

import me.caosh.condition.domain.model.order.TradeCustomerIdentity;
import me.caosh.condition.domain.model.order.constant.OrderState;
import me.caosh.condition.domain.model.order.newstock.NewStockOrder;
import me.caosh.condition.domain.model.order.newstock.NewStockPurchaseCondition;
import me.caosh.condition.domain.util.DateFormats;
import me.caosh.condition.interfaces.command.NewStockOrderCreateCommand;
import me.caosh.condition.interfaces.command.NewStockOrderUpdateCommand;

import java.time.LocalTime;

/**
 * Created by caosh on 2017/8/9.
 */
public class NewStockOrderCommandAssembler {
    public static NewStockOrder assemble(Long orderId, TradeCustomerIdentity customerIdentity, NewStockOrderCreateCommand command) {
        OrderState orderState = OrderState.ACTIVE;
        LocalTime purchaseTime = LocalTime.parse(command.getPurchaseTime(), DateFormats.HH_MM_SS);
        NewStockPurchaseCondition newStockPurchaseCondition = new NewStockPurchaseCondition(purchaseTime);
        return new NewStockOrder(orderId, customerIdentity, newStockPurchaseCondition, orderState);
    }

    public static NewStockOrder merge(NewStockOrder oldOrder, NewStockOrderUpdateCommand command) {
        OrderState orderState = OrderState.ACTIVE;
        LocalTime purchaseTime = LocalTime.parse(command.getPurchaseTime(), DateFormats.HH_MM_SS);
        NewStockPurchaseCondition newStockPurchaseCondition = new NewStockPurchaseCondition(purchaseTime);
        return new NewStockOrder(oldOrder.getOrderId(), oldOrder.getCustomerIdentity(),
                newStockPurchaseCondition, orderState);
    }

    private NewStockOrderCommandAssembler() {
    }
}
