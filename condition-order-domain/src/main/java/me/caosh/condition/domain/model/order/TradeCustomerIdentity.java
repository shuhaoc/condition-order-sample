package me.caosh.condition.domain.model.order;

import com.google.common.base.MoreObjects;

/**
 * Created by caosh on 2017/8/14.
 *
 * @author caoshuhao@touker.com
 */
public class TradeCustomerIdentity {
    private final Integer userId;
    private final String customerNo;

    public TradeCustomerIdentity(Integer userId, String customerNo) {
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
        if (this == o) return true;
        if (!(o instanceof TradeCustomerIdentity)) return false;

        TradeCustomerIdentity that = (TradeCustomerIdentity) o;

        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        return customerNo != null ? customerNo.equals(that.customerNo) : that.customerNo == null;
    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + (customerNo != null ? customerNo.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(TradeCustomerIdentity.class).omitNullValues()
                .addValue(TradeCustomerIdentity.class.getSuperclass() != Object.class ? super.toString() : null)
                .add("userId", userId)
                .add("customerNo", customerNo)
                .toString();
    }
}
