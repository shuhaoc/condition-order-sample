package me.caosh.condition.domain.model.order;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;

import java.math.BigDecimal;

/**
 * Created by caosh on 2017/8/2.
 */
public class SimplePriceCondition implements Condition {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SimplePriceCondition)) return false;

        SimplePriceCondition that = (SimplePriceCondition) o;

        if (priceDirection != that.priceDirection) return false;
        return !(targetPrice != null ? !targetPrice.equals(that.targetPrice) : that.targetPrice != null);

    }

    @Override
    public int hashCode() {
        int result = priceDirection != null ? priceDirection.hashCode() : 0;
        result = 31 * result + (targetPrice != null ? targetPrice.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("priceDirection", priceDirection)
                .add("targetPrice", targetPrice)
                .toString();
    }

    public boolean isSatisfiedBy(BigDecimal price) {
        if (priceDirection == PriceDirection.UPWARD) {
            return price.compareTo(targetPrice) >= 0;
        } else {
            Preconditions.checkArgument(priceDirection == PriceDirection.DOWNWARD);
            return price.compareTo(targetPrice) <= 0;
        }
    }
}
