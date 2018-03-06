package hbec.intellitrade.trade.domain;

import com.google.common.base.MoreObjects;
import me.caosh.condition.domain.model.order.TradeCustomerInfo;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/3/6
 */
public class EntrustOrderInfo {
    private final long orderId;
    private final TradeCustomerInfo tradeCustomerInfo;
    private final EntrustCommand entrustCommand;
    private final EntrustResult entrustResult;

    public EntrustOrderInfo(long orderId, TradeCustomerInfo tradeCustomerInfo, EntrustCommand entrustCommand, EntrustResult entrustResult) {
        this.orderId = orderId;
        this.tradeCustomerInfo = tradeCustomerInfo;
        this.entrustCommand = entrustCommand;
        this.entrustResult = entrustResult;
    }

    public long getOrderId() {
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
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EntrustOrderInfo that = (EntrustOrderInfo) o;

        if (orderId != that.orderId) {
            return false;
        }
        if (!tradeCustomerInfo.equals(that.tradeCustomerInfo)) {
            return false;
        }
        if (!entrustCommand.equals(that.entrustCommand)) {
            return false;
        }
        return entrustResult.equals(that.entrustResult);
    }

    @Override
    public int hashCode() {
        int result = (int) (orderId ^ (orderId >>> 32));
        result = 31 * result + tradeCustomerInfo.hashCode();
        result = 31 * result + entrustCommand.hashCode();
        result = 31 * result + entrustResult.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(EntrustOrderInfo.class).omitNullValues()
                .add("orderId", orderId)
                .add("tradeCustomerInfo", tradeCustomerInfo)
                .add("entrustCommand", entrustCommand)
                .add("entrustResult", entrustResult)
                .toString();
    }
}
