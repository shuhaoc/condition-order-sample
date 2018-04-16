package hbec.intellitrade.conditionorder.domain.tradeplan;

import com.google.common.base.MoreObjects;
import hbec.intellitrade.common.security.SecurityExchange;
import hbec.intellitrade.common.security.SecurityInfo;
import hbec.intellitrade.common.security.SecurityType;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/**
 * 金额下单计算方法
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2017/8/17
 */
public class TradeNumberByAmount implements TradeNumber {
    private final BigDecimal amount;

    public TradeNumberByAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public EntrustMethod getEntrustMethod() {
        return EntrustMethod.AMOUNT;
    }

    @Override
    public int getNumber(SecurityInfo securityInfo, BigDecimal price) {
        SecurityType securityType = securityInfo.getType();
        BigDecimal capitalPerHand;
        SecurityExchange exchange = securityInfo.getExchange();
        if (securityType == SecurityType.BOND) {
            // 债券上海万二、深圳千一
            if (exchange == SecurityExchange.SH) {
                // 上海单位为手，1手=10张
                capitalPerHand = price.multiply(BigDecimal.valueOf(10))
                                      .multiply(new BigDecimal("1.0002"));
                return amount.divide(capitalPerHand, 0, RoundingMode.DOWN).intValue();
            } else if (exchange == SecurityExchange.SZ) {
                // 深圳单位为张，10张为最小变动单位
                capitalPerHand = price.multiply(BigDecimal.valueOf(10))
                                      .multiply(new BigDecimal("1.001"));
                return amount.divide(capitalPerHand, 0, RoundingMode.DOWN).intValue() * 10;
            } else {
                throw new IllegalArgumentException("exchange=" + exchange);
            }
        } else {
            // 除债券以外品种暂时以千三估算
            capitalPerHand = price.multiply(BigDecimal.valueOf(100))
                                  .multiply(new BigDecimal("1.003"));
            return amount.divide(capitalPerHand, 0, RoundingMode.DOWN).intValue() * 100;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TradeNumberByAmount that = (TradeNumberByAmount) o;
        return Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(TradeNumberByAmount.class).omitNullValues()
                          .addValue(amount)
                          .toString();
    }
}
