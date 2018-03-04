package me.caosh.condition.domain.model.order;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import me.caosh.condition.domain.model.account.User;
import me.caosh.condition.domain.model.trade.EntrustCommand;
import me.caosh.condition.domain.model.trade.EntrustResult;

/**
 * 交易客户
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2017/8/14
 */
public class TradeCustomerInfo extends User {
    private final String customerNo;

    /**
     * 交易系统适配器，仅当需要交易系统功能时才是必需的
     */
    private TradeSystemAdapter tradeSystemAdapter;

    public TradeCustomerInfo(Integer userId, String customerNo) {
        super(userId);
        this.customerNo = customerNo;
    }

    public TradeCustomerInfo(Integer userId, String customerNo, TradeSystemAdapter tradeSystemAdapter) {
        super(userId);
        this.customerNo = customerNo;
        this.tradeSystemAdapter = tradeSystemAdapter;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public EntrustResult entrust(EntrustCommand entrustCommand) {
        assertTradeSystemAdapterNotNull();
        return tradeSystemAdapter.entrust(customerNo, entrustCommand);
    }

    private void assertTradeSystemAdapterNotNull() {
        Preconditions.checkNotNull(tradeSystemAdapter, "tradeSystemAdapter is null");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        TradeCustomerInfo that = (TradeCustomerInfo) o;

        return customerNo.equals(that.customerNo);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + customerNo.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(TradeCustomerInfo.class).omitNullValues()
                .add("customerNo", customerNo)
                .toString();
    }
}
