package me.caosh.condition.infrastructure.repository.model;

import com.google.common.base.MoreObjects;
import me.caosh.condition.domain.model.order.CompareCondition;

import java.math.BigDecimal;

/**
 * Created by caosh on 2017/8/15.
 */
public class PriceConditionDO implements ConditionDO {
    private Integer compareCondition;
    private BigDecimal targetPrice;

    public PriceConditionDO() {
    }

    public PriceConditionDO(Integer compareCondition, BigDecimal targetPrice) {
        this.compareCondition = compareCondition;
        this.targetPrice = targetPrice;
    }

    public Integer getCompareCondition() {
        return compareCondition;
    }

    public void setCompareCondition(Integer compareCondition) {
        this.compareCondition = compareCondition;
    }

    public BigDecimal getTargetPrice() {
        return targetPrice;
    }

    public void setTargetPrice(BigDecimal targetPrice) {
        this.targetPrice = targetPrice;
    }

    @Override
    public void accept(ConditionDOVisitor visitor) {
        visitor.visitPriceConditionDO(this);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("compareCondition", compareCondition)
                .add("targetPrice", targetPrice)
                .toString();
    }
}
