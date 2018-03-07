package me.caosh.condition.infrastructure.repository.model;

import hbec.intellitrade.common.ValuedEnumUtil;
import hbec.intellitrade.common.security.SecurityExchange;
import hbec.intellitrade.common.security.SecurityInfo;
import hbec.intellitrade.common.security.SecurityType;
import hbec.intellitrade.condorder.domain.TradeCustomerInfo;
import hbec.intellitrade.trade.domain.EntrustCommand;
import hbec.intellitrade.trade.domain.EntrustOrder;
import hbec.intellitrade.trade.domain.EntrustResult;
import hbec.intellitrade.trade.domain.ExchangeType;
import hbec.intellitrade.trade.domain.OrderType;

/**
 * Created by caosh on 2017/8/14.
 *
 * @author caoshuhao@touker.com
 */
public class EntrustOrderDOAssembler {
    public static EntrustOrderDO toDO(EntrustOrder entrustOrder) {
        EntrustOrderDO entrustOrderDO = new EntrustOrderDO();
        entrustOrderDO.setEntrustId(entrustOrder.getEntrustId());
        entrustOrderDO.setOrderId(entrustOrder.getOrderId());
        entrustOrderDO.setUserId(entrustOrder.getTradeCustomerInfo().getUserId());
        entrustOrderDO.setCustomerNo(entrustOrder.getTradeCustomerInfo().getCustomerNo());
        entrustOrderDO.setSecurityType(entrustOrder.getEntrustCommand().getSecurityInfo().getType().getValue());
        entrustOrderDO.setSecurityCode(entrustOrder.getEntrustCommand().getSecurityInfo().getCode());
        entrustOrderDO.setSecurityExchange(entrustOrder.getEntrustCommand().getSecurityInfo().getExchange().name());
        entrustOrderDO.setSecurityName(entrustOrder.getEntrustCommand().getSecurityInfo().getName());
        entrustOrderDO.setExchangeType(entrustOrder.getEntrustCommand().getExchangeType().getValue());
        entrustOrderDO.setEntrustPrice(entrustOrder.getEntrustCommand().getEntrustPrice());
        entrustOrderDO.setEntrustNumber(entrustOrder.getEntrustCommand().getEntrustNumber());
        entrustOrderDO.setOrderType(entrustOrder.getEntrustCommand().getOrderType().getValue());
        entrustOrderDO.setEntrustState(entrustOrder.getEntrustResult().getEntrustState());
        entrustOrderDO.setEntrustMessage(entrustOrder.getEntrustResult().getEntrustMessage());
        entrustOrderDO.setEntrustCode(entrustOrder.getEntrustResult().getEntrustCode());
        return entrustOrderDO;
    }

    public static EntrustOrder fromDO(EntrustOrderDO entrustOrderDO) {
        TradeCustomerInfo tradeCustomerInfo = new TradeCustomerInfo(entrustOrderDO.getUserId(), entrustOrderDO.getCustomerNo());
        SecurityType securityType = ValuedEnumUtil.valueOf(entrustOrderDO.getSecurityType(), SecurityType.class);
        SecurityExchange securityExchange = SecurityExchange.valueOf(entrustOrderDO.getSecurityExchange());
        SecurityInfo securityInfo = new SecurityInfo(securityType, entrustOrderDO.getSecurityCode(), securityExchange,
                entrustOrderDO.getSecurityName());
        ExchangeType exchangeType = ValuedEnumUtil.valueOf(entrustOrderDO.getExchangeType(), ExchangeType.class);
        OrderType orderType = ValuedEnumUtil.valueOf(entrustOrderDO.getOrderType(), OrderType.class);
        EntrustCommand entrustCommand = new EntrustCommand(securityInfo, exchangeType,
                entrustOrderDO.getEntrustPrice(), entrustOrderDO.getEntrustNumber(), orderType);
        EntrustResult entrustResult = new EntrustResult(entrustOrderDO.getEntrustState(), entrustOrderDO.getEntrustMessage(),
                entrustOrderDO.getEntrustCode());
        return new EntrustOrder(entrustOrderDO.getEntrustId(), entrustOrderDO.getOrderId(), tradeCustomerInfo, entrustCommand, entrustResult);
    }

    private EntrustOrderDOAssembler() {
    }
}
