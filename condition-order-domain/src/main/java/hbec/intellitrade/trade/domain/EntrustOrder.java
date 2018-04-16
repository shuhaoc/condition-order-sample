package hbec.intellitrade.trade.domain;

import com.google.common.base.MoreObjects;
import hbec.intellitrade.conditionorder.domain.TradeCustomerInfo;

/**
 * Created by caosh on 2017/8/14.
 *
 * @author caoshuhao@touker.com
 */
public class EntrustOrder {
    private final Long entrustId;
    private final Long orderId;
    private final TradeCustomerInfo tradeCustomerInfo;
    private final EntrustCommand entrustCommand;
    private final EntrustResult entrustResult;

    public EntrustOrder(Long entrustId, Long orderId, TradeCustomerInfo tradeCustomerInfo, EntrustCommand entrustCommand, EntrustResult entrustResult) {
        this.entrustId = entrustId;
        this.orderId = orderId;
        this.tradeCustomerInfo = tradeCustomerInfo;
        this.entrustCommand = entrustCommand;
        this.entrustResult = entrustResult;
    }

    public Long getEntrustId() {
        return entrustId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public TradeCustomerInfo getTradeCustomerInfo() {
        return tradeCustomerInfo;
    }

    public EntrustCommand getEntrustCommand() {
        return entrustCommand;
    }

    public EntrustResult getEntrustResult() {
        return entrustResult;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(EntrustOrder.class).omitNullValues()
                .addValue(EntrustOrder.class.getSuperclass() != Object.class ? super.toString() : null)
                .add("entrustId", entrustId)
                .add("orderId", orderId)
                .add("entrustCommand", entrustCommand)
                .add("entrustResult", entrustResult)
                .toString();
    }
}
