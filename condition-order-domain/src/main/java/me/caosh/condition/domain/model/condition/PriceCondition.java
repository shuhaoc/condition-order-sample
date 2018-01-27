package me.caosh.condition.domain.model.condition;

import com.google.common.base.MoreObjects;
import me.caosh.condition.domain.model.order.ConditionVisitor;
import me.caosh.condition.domain.model.strategy.condition.AbstractBasicMarketCondition;
import me.caosh.condition.domain.model.strategy.factor.BasicTargetPriceFactor;
import me.caosh.condition.domain.model.strategy.factor.CompareOperator;

import java.math.BigDecimal;

/**
 * 价格条件，到价触发
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2018/1/29
 */
public class PriceCondition extends AbstractBasicMarketCondition {
    private final BasicTargetPriceFactor targetPriceFactor;

    public PriceCondition(CompareOperator compareOperator, BigDecimal targetPrice) {
        this.targetPriceFactor = new BasicTargetPriceFactor(compareOperator, targetPrice);
    }

    public CompareOperator getCompareOperator() {
        return targetPriceFactor.getCompareOperator();
    }

    public BigDecimal getTargetPrice() {
        return targetPriceFactor.getTargetPrice();
    }

    @Override
    public BasicTargetPriceFactor getTargetPriceFactor() {
        return targetPriceFactor;
    }

    @Override
    public void accept(ConditionVisitor visitor) {
        visitor.visitPriceCondition(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PriceCondition that = (PriceCondition) o;

        return targetPriceFactor.equals(that.targetPriceFactor);
    }

    @Override
    public int hashCode() {
        return targetPriceFactor.hashCode();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(PriceCondition.class).omitNullValues()
                .addValue(targetPriceFactor)
                .toString();
    }
}
