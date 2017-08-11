package me.caosh.condition.domain.model.order;

/**
 * Created by caosh on 2017/8/11.
 *
 * @author caoshuhao@touker.com
 */
public interface ConditionVisitor {
    void visitPriceCondition(PriceCondition priceCondition);
}
