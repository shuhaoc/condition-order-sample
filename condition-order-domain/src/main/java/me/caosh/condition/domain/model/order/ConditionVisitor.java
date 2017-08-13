package me.caosh.condition.domain.model.order;

import me.caosh.condition.domain.model.order.price.PriceCondition;

/**
 * Created by caosh on 2017/8/11.
 *
 * @author caoshuhao@touker.com
 */
public interface ConditionVisitor {
    void visitPriceCondition(PriceCondition priceCondition);
}
