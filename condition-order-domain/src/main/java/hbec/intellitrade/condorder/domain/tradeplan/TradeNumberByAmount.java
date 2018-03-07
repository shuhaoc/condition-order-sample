package hbec.intellitrade.condorder.domain.tradeplan;

import com.google.common.base.MoreObjects;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by caosh on 2017/8/17.
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
    public int getNumber(BigDecimal entrustPrice) {
        // 简版金额下单逻辑，未考虑交易费用
        BigDecimal amountPerHand = entrustPrice.multiply(BigDecimal.valueOf(100));
        return amount.divide(amountPerHand, 0, RoundingMode.DOWN).intValue() * 100;
    }

    public Number getNumber() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TradeNumberByAmount)) return false;

        TradeNumberByAmount that = (TradeNumberByAmount) o;

        return !(amount != null ? !amount.equals(that.amount) : that.amount != null);

    }

    @Override
    public int hashCode() {
        return amount != null ? amount.hashCode() : 0;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("amount", amount)
                .toString();
    }

}
