package me.caosh.condition.infrastructure.repository.model;

/**
 * Created by caosh on 2017/8/15.
 */
public interface ConditionDOVisitor {
    void visitPriceConditionDO(PriceConditionDO priceConditionDO);
}
