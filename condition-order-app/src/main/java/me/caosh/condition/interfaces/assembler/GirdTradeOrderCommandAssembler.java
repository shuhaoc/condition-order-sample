package me.caosh.condition.interfaces.assembler;

import hbec.intellitrade.common.ValuedEnumUtil;
import hbec.intellitrade.common.security.SecurityExchange;
import hbec.intellitrade.common.security.SecurityInfo;
import hbec.intellitrade.common.security.SecurityType;
import hbec.intellitrade.condorder.domain.OrderState;
import hbec.intellitrade.condorder.domain.TradeCustomerInfo;
import hbec.intellitrade.condorder.domain.tradeplan.DoubleDirectionTradePlan;
import hbec.intellitrade.condorder.domain.tradeplan.TradePlanFactory;
import me.caosh.condition.domain.model.order.grid.GridCondition;
import me.caosh.condition.domain.model.order.grid.GridTradeOrder;
import me.caosh.condition.interfaces.command.GridTradeOrderCreateCommand;
import me.caosh.condition.interfaces.command.GridTradeOrderUpdateCommand;

/**
 * Created by caosh on 2017/8/9.
 */
public class GirdTradeOrderCommandAssembler {
    public static GridTradeOrder assemble(Long orderId, TradeCustomerInfo tradeCustomerInfo, GridTradeOrderCreateCommand command) {
        OrderState orderState = OrderState.ACTIVE;
        SecurityType securityType = ValuedEnumUtil.valueOf(command.getSecurityType(), SecurityType.class);
        SecurityExchange securityExchange = SecurityExchange.valueOf(command.getSecurityExchange());
        SecurityInfo securityInfo = new SecurityInfo(securityType, command.getSecurityCode(), securityExchange, command.getSecurityName());
        GridCondition gridCondition = new GridCondition(command.getGridLength(), command.getBasePrice());
        DoubleDirectionTradePlan tradePlan = TradePlanFactory.getInstance().createDouble(command.getEntrustStrategy(),
                command.getEntrustMethod(),
                command.getEntrustNumber(),
                command.getEntrustAmount());
        return new GridTradeOrder(orderId,
                tradeCustomerInfo,
                securityInfo,
                gridCondition,
                null, tradePlan,
                orderState);
    }

    public static GridTradeOrder merge(GridTradeOrder oldOrder, GridTradeOrderUpdateCommand command) {
        OrderState orderState = OrderState.ACTIVE;
        GridCondition gridCondition = new GridCondition(command.getGridLength(), command.getBasePrice());
        DoubleDirectionTradePlan tradePlan = TradePlanFactory.getInstance().createDouble(
                command.getEntrustStrategy(),
                command.getEntrustMethod(),
                command.getEntrustNumber(),
                command.getEntrustAmount());
        return new GridTradeOrder(oldOrder.getOrderId(),
                oldOrder.getCustomer(),
                oldOrder.getSecurityInfo(),
                gridCondition, null, tradePlan, orderState
        );
    }

    private GirdTradeOrderCommandAssembler() {
    }
}
