package me.caosh.condition.interfaces.assembler;

import hbec.intellitrade.conditionorder.domain.OrderState;
import hbec.intellitrade.conditionorder.domain.TradeCustomerInfo;
import hbec.intellitrade.conditionorder.domain.orders.newstock.NewStockOrder;
import hbec.intellitrade.conditionorder.domain.orders.newstock.NewStockPurchaseCondition;
import me.caosh.autoasm.util.DateConvertUtils;
import me.caosh.condition.interfaces.command.NewStockOrderCreateCommand;
import me.caosh.condition.interfaces.command.NewStockOrderUpdateCommand;
import me.caosh.util.DateFormats;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;


/**
 * Created by caosh on 2017/8/9.
 */
public class NewStockOrderCommandAssembler {
    public static NewStockOrder assemble(Long orderId,
            TradeCustomerInfo tradeCustomerInfo,
            NewStockOrderCreateCommand command) {
        LocalTime purchaseTime = LocalTime.parse(command.getPurchaseTime(), DateConvertUtils.HH_MM_SS);
        LocalDateTime expireTime = command.getExpireTime() != null
                ? LocalDateTime.fromDateFields(command.getExpireTime())
                : null;
        NewStockPurchaseCondition newStockPurchaseCondition = new NewStockPurchaseCondition(purchaseTime);
        return new NewStockOrder(orderId, tradeCustomerInfo, OrderState.ACTIVE, newStockPurchaseCondition, expireTime);
    }

    public static NewStockOrder merge(NewStockOrder oldOrder, NewStockOrderUpdateCommand command) {
        LocalTime purchaseTime = LocalTime.parse(command.getPurchaseTime(), DateFormats.HH_MM_SS);
        NewStockPurchaseCondition oldOrderCondition = oldOrder.getCondition();
        NewStockPurchaseCondition newStockPurchaseCondition = new NewStockPurchaseCondition(purchaseTime,
                false, oldOrderCondition.getPurchasedCount());

        LocalDateTime expireTime = command.getExpireTime() != null
                ? LocalDateTime.fromDateFields(command.getExpireTime())
                : null;
        return new NewStockOrder(oldOrder.getOrderId(),
                oldOrder.getCustomer(),
                OrderState.ACTIVE, newStockPurchaseCondition,
                expireTime
        );
    }

    private NewStockOrderCommandAssembler() {
    }
}
