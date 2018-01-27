package me.caosh.condition.domain.model.trade;

import com.google.common.base.MoreObjects;
import me.caosh.condition.domain.model.order.TradeCustomer;

/**
 * Created by caosh on 2017/8/14.
 *
 * @author caoshuhao@touker.com
 */
public class EntrustOrder {
    private final Long entrustId;
    private final Long orderId;
    private final TradeCustomer tradeCustomer;
    private final EntrustCommand entrustCommand;
    private final EntrustResult entrustResult;

    public EntrustOrder(Long entrustId, Long orderId, TradeCustomer tradeCustomer, EntrustCommand entrustCommand, EntrustResult entrustResult) {
        this.entrustId = entrustId;
        this.orderId = orderId;
        this.tradeCustomer = tradeCustomer;
        this.entrustCommand = entrustCommand;
        this.entrustResult = entrustResult;
    }

    public Long getEntrustId() {
        return entrustId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public TradeCustomer getTradeCustomer() {
        return tradeCustomer;
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
