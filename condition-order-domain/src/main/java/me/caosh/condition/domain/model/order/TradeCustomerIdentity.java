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
    public String toString() {
        return MoreObjects.toStringHelper(TradeCustomerIdentity.class).omitNullValues()
                .addValue(TradeCustomerIdentity.class.getSuperclass() != Object.class ? super.toString() : null)
                .add("userId", userId)
                .add("customerNo", customerNo)
                .toString();
    }
}
