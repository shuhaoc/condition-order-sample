package me.caosh.condition.domain.dto.order;

import com.google.common.base.MoreObjects;
import me.caosh.autoasm.Convertible;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/3/7
 */
@Convertible
public class TradeCustomerInfoDTO {
    private  Integer userId;
    private  String customerNo;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(TradeCustomerInfoDTO.class).omitNullValues()
                .add("userId", userId)
                .add("customerNo", customerNo)
                .toString();
    }
}
