package hbec.intellitrade.conditionorder.domain;

import me.caosh.autoasm.ConvertibleBuilder;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/2/4
 */
public class TradeCustomerInfoBuilder implements ConvertibleBuilder<TradeCustomerInfo> {
    private Integer userId;
    private String customerNo;

    public TradeCustomerInfoBuilder setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public TradeCustomerInfoBuilder setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
        return this;
    }

    @Override
    public TradeCustomerInfo build() {
        return new TradeCustomerInfo(userId, customerNo);
    }
}