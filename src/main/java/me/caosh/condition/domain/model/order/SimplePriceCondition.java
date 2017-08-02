package me.caosh.condition.domain.model.order;

import com.google.common.base.MoreObjects;

import java.math.BigDecimal;

/**
 * Created by caosh on 2017/8/2.
 */
public class SimplePriceCondition {
    private final PriceDirection priceDirection;
    private final BigDecimal targetPrice;

    public SimplePriceCondition(PriceDirection priceDirection, BigDecimal targetPrice) {
        this.priceDirection = priceDirection;
        this.targetPrice = targetPrice;
    }

    public PriceDirection getPriceDirection() {
        return priceDirection;
    }

    public BigDecimal getTargetPrice() {
        return targetPrice;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("priceDirection", priceDirection)
                .add("targetPrice", targetPrice)
                .toString();
    }
}
