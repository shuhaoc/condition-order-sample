package me.caosh.condition.domain.dto.trade;

import me.caosh.condition.domain.model.trade.EntrustOrder;

/**
 * Created by caosh on 2017/8/15.
 */
public class EntrustOrderDTOAssembler {
    public static EntrustOrderDTO toDTO(EntrustOrder entrustOrder) {
        EntrustOrderDTO entrustOrderDTO = new EntrustOrderDTO();
        entrustOrderDTO.setEntrustId(entrustOrder.getEntrustId());
        entrustOrderDTO.setOrderId(entrustOrder.getOrderId());
        entrustOrderDTO.setUserId(entrustOrder.getTradeCustomerInfo().getUserId());
        entrustOrderDTO.setCustomerNo(entrustOrder.getTradeCustomerInfo().getCustomerNo());
        entrustOrderDTO.setSecurityType(entrustOrder.getEntrustCommand().getSecurityInfo().getType().getValue());
        entrustOrderDTO.setSecurityCode(entrustOrder.getEntrustCommand().getSecurityInfo().getCode());
        entrustOrderDTO.setSecurityExchange(entrustOrder.getEntrustCommand().getSecurityInfo().getExchange().name());
        entrustOrderDTO.setSecurityName(entrustOrder.getEntrustCommand().getSecurityInfo().getName());
        entrustOrderDTO.setExchangeType(entrustOrder.getEntrustCommand().getExchangeType().getValue());
        entrustOrderDTO.setEntrustPrice(entrustOrder.getEntrustCommand().getEntrustPrice());
        entrustOrderDTO.setEntrustNumber(entrustOrder.getEntrustCommand().getEntrustNumber());
        entrustOrderDTO.setOrderType(entrustOrder.getEntrustCommand().getOrderType().getValue());
        entrustOrderDTO.setEntrustState(entrustOrder.getEntrustResult().getEntrustState());
        entrustOrderDTO.setEntrustMessage(entrustOrder.getEntrustResult().getEntrustMessage());
        entrustOrderDTO.setEntrustCode(entrustOrder.getEntrustResult().getEntrustCode());
        return entrustOrderDTO;
    }

    private EntrustOrderDTOAssembler() {
    }
}
