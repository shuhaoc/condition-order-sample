package me.caosh.condition.interfaces.assembler;

import hbec.intellitrade.common.security.SecurityExchange;
import hbec.intellitrade.common.security.SecurityInfo;
import hbec.intellitrade.common.security.SecurityType;
import hbec.intellitrade.condorder.domain.OrderState;
import hbec.intellitrade.condorder.domain.TradeCustomerInfo;
import hbec.intellitrade.condorder.domain.tradeplan.BaseTradePlan;
import hbec.intellitrade.condorder.domain.tradeplan.EntrustStrategy;
import hbec.intellitrade.condorder.domain.tradeplan.TradeNumber;
import hbec.intellitrade.condorder.domain.tradeplan.TradeNumberFactory;
import hbec.intellitrade.trade.domain.ExchangeType;
import me.caosh.autoasm.AutoAssemblers;
import me.caosh.condition.domain.model.condition.TimeReachedCondition;
import me.caosh.condition.domain.model.order.time.TimeOrder;
import me.caosh.condition.interfaces.command.TimeOrderCreateCommand;
import me.caosh.condition.interfaces.command.TimeOrderUpdateCommand;
import me.caosh.util.InstantUtils;

/**
 * Created by caosh on 2017/8/9.
 */
public class TimeOrderCommandAssembler {
    public static TimeOrder assemble(Long orderId, TradeCustomerInfo tradeCustomerInfo, TimeOrderCreateCommand command) {
        OrderState orderState = OrderState.ACTIVE;
        SecurityType securityType = AutoAssemblers.getDefault()
                                                  .disassemble(command.getSecurityType(), SecurityType.class);
        SecurityExchange securityExchange = SecurityExchange.valueOf(command.getSecurityExchange());
        SecurityInfo securityInfo = new SecurityInfo(securityType, command.getSecurityCode(), securityExchange,
                command.getSecurityName());
        TimeReachedCondition timeReachedCondition = new TimeReachedCondition(InstantUtils.toLocalDateTime(command.getTargetTime()));
        ExchangeType exchangeType = AutoAssemblers.getDefault()
                                                  .disassemble(command.getExchangeType(), ExchangeType.class);
        EntrustStrategy entrustStrategy = AutoAssemblers.getDefault()
                                                        .disassemble(command.getEntrustStrategy(),
                                                                     EntrustStrategy.class);
        TradeNumber tradeNumber = TradeNumberFactory.getInstance()
                .create(command.getEntrustMethod(), command.getEntrustNumber(), command.getEntrustAmount());
        BaseTradePlan tradePlan = new BaseTradePlan(exchangeType, entrustStrategy, tradeNumber);
        return new TimeOrder(orderId, tradeCustomerInfo, securityInfo, timeReachedCondition, null, tradePlan, orderState);
    }

    public static TimeOrder merge(TimeOrder oldOrder, TimeOrderUpdateCommand command) {
        OrderState orderState = OrderState.ACTIVE;
        TimeReachedCondition timeReachedCondition = new TimeReachedCondition(InstantUtils.toLocalDateTime(command.getTargetTime()));
        ExchangeType exchangeType = AutoAssemblers.getDefault()
                                                  .disassemble(command.getExchangeType(), ExchangeType.class);
        EntrustStrategy entrustStrategy = AutoAssemblers.getDefault()
                                                        .disassemble(command.getEntrustStrategy(),
                                                                     EntrustStrategy.class);
        TradeNumber tradeNumber = TradeNumberFactory.getInstance()
                .create(command.getEntrustMethod(), command.getEntrustNumber(), command.getEntrustAmount());
        BaseTradePlan tradePlan = new BaseTradePlan(exchangeType, entrustStrategy, tradeNumber);
        return new TimeOrder(oldOrder.getOrderId(), oldOrder.getCustomer(),
                oldOrder.getSecurityInfo(), timeReachedCondition, null, tradePlan, orderState);
    }

    private TimeOrderCommandAssembler() {
    }
}
