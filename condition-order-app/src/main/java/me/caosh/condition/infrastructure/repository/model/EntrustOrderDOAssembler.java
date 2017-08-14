package me.caosh.condition.infrastructure.repository.model;

import me.caosh.condition.domain.model.market.SecurityExchange;
import me.caosh.condition.domain.model.market.SecurityInfo;
import me.caosh.condition.domain.model.market.SecurityType;
import me.caosh.condition.domain.model.order.ExchangeType;
import me.caosh.condition.domain.model.order.TradeCustomerIdentity;
import me.caosh.condition.domain.model.share.ValuedEnumUtil;
import me.caosh.condition.domain.model.trade.EntrustCommand;
import me.caosh.condition.domain.model.trade.EntrustOrder;
import me.caosh.condition.domain.model.trade.EntrustResult;
import me.caosh.condition.domain.model.trade.OrderType;

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
        entrustOrderDO.setUserId(entrustOrder.getEntrustCommand().getCustomerIdentity().getUserId());
        entrustOrderDO.setCustomerNo(entrustOrder.getEntrustCommand().getCustomerIdentity().getCustomerNo());
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
        TradeCustomerIdentity customerIdentity = new TradeCustomerIdentity(entrustOrderDO.getUserId(), entrustOrderDO.getCustomerNo());
        SecurityType securityType = ValuedEnumUtil.valueOf(entrustOrderDO.getSecurityType(), SecurityType.class);
        SecurityExchange securityExchange = SecurityExchange.valueOf(entrustOrderDO.getSecurityExchange());
        SecurityInfo securityInfo = new SecurityInfo(securityType, entrustOrderDO.getSecurityCode(), securityExchange,
                entrustOrderDO.getSecurityName());
        ExchangeType exchangeType = ValuedEnumUtil.valueOf(entrustOrderDO.getExchangeType(), ExchangeType.class);
        OrderType orderType = ValuedEnumUtil.valueOf(entrustOrderDO.getOrderType(), OrderType.class);
        EntrustCommand entrustCommand = new EntrustCommand(customerIdentity, securityInfo, exchangeType,
                entrustOrderDO.getEntrustPrice(), entrustOrderDO.getEntrustNumber(), orderType);
        EntrustResult entrustResult = new EntrustResult(entrustOrderDO.getEntrustState(), entrustOrderDO.getEntrustMessage(),
                entrustOrderDO.getEntrustCode());
        return new EntrustOrder(entrustOrderDO.getEntrustId(), entrustOrderDO.getOrderId(), entrustCommand, entrustResult);
    }

    private EntrustOrderDOAssembler() {
    }
}
