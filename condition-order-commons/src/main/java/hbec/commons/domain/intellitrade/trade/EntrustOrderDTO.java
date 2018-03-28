package hbec.commons.domain.intellitrade.trade;

import com.google.common.base.MoreObjects;
import hbec.commons.domain.intellitrade.condorder.TradeCustomerInfoDTO;

import java.io.Serializable;

/**
 * Created by caosh on 2017/8/15.
 */
public class EntrustOrderDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long entrustId;
    private Long orderId;
    private TradeCustomerInfoDTO tradeCustomerInfo = new TradeCustomerInfoDTO();
    private EntrustCommandDTO entrustCommand = new EntrustCommandDTO();
    private EntrustResultDTO entrustResult = new EntrustResultDTO();

    public Long getEntrustId() {
        return entrustId;
    }

    public void setEntrustId(Long entrustId) {
        this.entrustId = entrustId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public TradeCustomerInfoDTO getTradeCustomerInfo() {
        return tradeCustomerInfo;
    }

    public void setTradeCustomerInfo(TradeCustomerInfoDTO tradeCustomerInfo) {
        this.tradeCustomerInfo = tradeCustomerInfo;
    }

    public EntrustCommandDTO getEntrustCommand() {
        return entrustCommand;
    }

    public void setEntrustCommand(EntrustCommandDTO entrustCommand) {
        this.entrustCommand = entrustCommand;
    }

    public EntrustResultDTO getEntrustResult() {
        return entrustResult;
    }

    public void setEntrustResult(EntrustResultDTO entrustResult) {
        this.entrustResult = entrustResult;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(EntrustOrderDTO.class).omitNullValues()
                .add("entrustId", entrustId)
                .add("orderId", orderId)
                .add("tradeCustomerInfo", tradeCustomerInfo)
                .add("entrustCommand", entrustCommand)
                .add("entrustResult", entrustResult)
                .toString();
    }
}
