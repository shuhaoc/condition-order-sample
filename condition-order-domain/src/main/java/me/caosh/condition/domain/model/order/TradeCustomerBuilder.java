package me.caosh.condition.domain.model.order;

import me.caosh.autoasm.ConvertibleBuilder;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/2/4
 */
public class TradeCustomerBuilder implements ConvertibleBuilder<TradeCustomer> {
    private Integer userId;
    private String customerNo;

    public TradeCustomerBuilder setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public TradeCustomerBuilder setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
        return this;
    }

    @Override
    public TradeCustomer build() {
        return new TradeCustomer(userId, customerNo);
    }
}