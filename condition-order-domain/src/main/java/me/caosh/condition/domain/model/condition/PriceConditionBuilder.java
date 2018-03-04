package me.caosh.condition.domain.model.condition;

import hbec.intellitrade.strategy.domain.factor.CompareOperator;
import me.caosh.autoasm.ConvertibleBuilder;

import java.math.BigDecimal;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/2/4
 */
public class PriceConditionBuilder implements ConvertibleBuilder<PriceCondition> {
    private CompareOperator compareOperator;
    private BigDecimal targetPrice;

    public PriceConditionBuilder setCompareOperator(CompareOperator compareOperator) {
        this.compareOperator = compareOperator;
        return this;
    }

    public PriceConditionBuilder setTargetPrice(BigDecimal targetPrice) {
        this.targetPrice = targetPrice;
        return this;
    }

    @Override
    public PriceCondition build() {
        return new PriceCondition(compareOperator, targetPrice);
    }
}