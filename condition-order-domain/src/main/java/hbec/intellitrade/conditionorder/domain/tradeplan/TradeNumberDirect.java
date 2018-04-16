package hbec.intellitrade.conditionorder.domain.tradeplan;

import com.google.common.base.MoreObjects;
import hbec.intellitrade.common.security.SecurityInfo;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * 数量下单计算方法
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2017/8/17
 */
public class TradeNumberDirect implements TradeNumber {
    private final int number;

    public TradeNumberDirect(int number) {
        this.number = number;
    }

    @Override
    public EntrustMethod getEntrustMethod() {
        return EntrustMethod.NUMBER;
    }

    @Override
    public int getNumber(SecurityInfo securityInfo, BigDecimal entrustPrice) {
        return number;
    }

    public Integer getNumber() {
        return number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TradeNumberDirect that = (TradeNumberDirect) o;
        return number == that.number;
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(TradeNumberDirect.class).omitNullValues()
                          .addValue(number)
                          .toString();
    }
}
