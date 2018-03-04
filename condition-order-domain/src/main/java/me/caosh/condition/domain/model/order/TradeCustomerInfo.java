package me.caosh.condition.domain.model.order;

import com.google.common.base.MoreObjects;
import me.caosh.condition.domain.model.account.User;

/**
 * 交易客户信息
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2017/8/14
 */
public class TradeCustomerInfo extends User {
    private final String customerNo;

    public TradeCustomerInfo(Integer userId, String customerNo) {
        super(userId);
        this.customerNo = customerNo;
    }

    public String getCustomerNo() {
        return customerNo;
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
