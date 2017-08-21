package me.caosh.condition.interfaces.assembler;

import me.caosh.condition.domain.model.constants.SecurityExchange;
import me.caosh.condition.domain.model.constants.SecurityType;
import me.caosh.condition.domain.model.market.SecurityInfo;
import me.caosh.condition.domain.model.order.DynamicCondition;
import me.caosh.condition.domain.model.order.TradeCustomerIdentity;
import me.caosh.condition.domain.model.order.constant.EntrustStrategy;
import me.caosh.condition.domain.model.order.constant.ExchangeType;
import me.caosh.condition.domain.model.order.constant.OrderState;
import me.caosh.condition.domain.model.order.plan.TradeNumber;
import me.caosh.condition.domain.model.order.plan.TradeNumberFactory;
import me.caosh.condition.domain.model.order.plan.TradePlan;
import me.caosh.condition.domain.model.order.turnpoint.TurnUpBuyOrder;
import me.caosh.condition.domain.model.order.turnpoint.TurnUpCondition;
import me.caosh.condition.domain.model.share.ValuedEnumUtil;
import me.caosh.condition.interfaces.command.TurnUpBuyOrderCreateCommand;
import me.caosh.condition.interfaces.command.TurnUpBuyOrderUpdateCommand;

/**
 * Created by caosh on 2017/8/9.
 */
public class TurnUpBuyOrderCommandAssembler {
    public static TurnUpBuyOrder assemble(Long orderId, TradeCustomerIdentity customerIdentity, TurnUpBuyOrderCreateCommand command) {
        OrderState orderState = OrderState.ACTIVE;
        SecurityType securityType = ValuedEnumUtil.valueOf(command.getSecurityType(), SecurityType.class);
        SecurityExchange securityExchange = SecurityExchange.valueOf(command.getSecurityExchange());
        SecurityInfo securityInfo = new SecurityInfo(securityType, command.getSecurityCode(), securityExchange,
                command.getSecurityName());
        TurnUpCondition turnUpCondition = new TurnUpCondition(command.getBreakPrice(), command.getTurnUpPercent());
        EntrustStrategy entrustStrategy = ValuedEnumUtil.valueOf(command.getEntrustStrategy(), EntrustStrategy.class);
        TradeNumber tradeNumber = TradeNumberFactory.getInstance()
                .create(command.getEntrustMethod(), command.getEntrustNumber(), command.getEntrustAmount());
        TradePlan tradePlan = new TradePlan(ExchangeType.BUY, entrustStrategy, tradeNumber);
        return new TurnUpBuyOrder(orderId, customerIdentity, false, securityInfo, turnUpCondition, tradePlan, orderState);
    }

    public static TurnUpBuyOrder merge(TurnUpBuyOrder oldOrder, TurnUpBuyOrderUpdateCommand command) {
        OrderState orderState = OrderState.ACTIVE;
        TurnUpCondition turnUpCondition = new TurnUpCondition(command.getBreakPrice(), command.getTurnUpPercent());
        if (command.getBreakPrice().compareTo(((TurnUpCondition) oldOrder.getCondition()).getBreakPrice()) == 0) {
            turnUpCondition.swap((DynamicCondition) oldOrder.getCondition());
        }
        EntrustStrategy entrustStrategy = ValuedEnumUtil.valueOf(command.getEntrustStrategy(), EntrustStrategy.class);
        TradeNumber tradeNumber = TradeNumberFactory.getInstance()
                .create(command.getEntrustMethod(), command.getEntrustNumber(), command.getEntrustAmount());
        TradePlan tradePlan = new TradePlan(ExchangeType.BUY, entrustStrategy, tradeNumber);
        return new TurnUpBuyOrder(oldOrder.getOrderId(), oldOrder.getCustomerIdentity(), false, oldOrder.getSecurityInfo(), turnUpCondition, tradePlan, orderState
        );
    }

    private TurnUpBuyOrderCommandAssembler() {
    }
}
