package me.caosh.condition.domain.model.order;

import com.google.common.base.MoreObjects;

/**
 * 交易客户信息
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2017/8/14
 */
public class TradeCustomerInfo {
    private final Integer userId;
    private final String customerNo;

    public TradeCustomerInfo(Integer userId, String customerNo) {
        this.userId = userId;
        this.customerNo = customerNo;
    }

    public Integer getUserId() {
        return userId;
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

        TradeCustomerInfo that = (TradeCustomerInfo) o;

        if (!userId.equals(that.userId)) {
            return false;
        }
        return customerNo.equals(that.customerNo);
    }

    @Override
    public int hashCode() {
        int result = userId.hashCode();
        result = 31 * result + customerNo.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(TradeCustomerInfo.class).omitNullValues()
                .add("userId", userId)
                .add("customerNo", customerNo)
                .toString();
    }
}
