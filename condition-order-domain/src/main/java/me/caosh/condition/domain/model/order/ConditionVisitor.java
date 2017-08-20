package me.caosh.condition.domain.model.order;

import me.caosh.condition.domain.model.order.price.PriceCondition;
import me.caosh.condition.domain.model.order.time.SimpleTimeCondition;
import me.caosh.condition.domain.model.order.turnpoint.TurnUpCondition;

/**
 * Created by caosh on 2017/8/11.
 *
 * @author caoshuhao@touker.com
 */
public interface ConditionVisitor {
    void visitPriceCondition(PriceCondition priceCondition);

    void visitTurnUpCondition(TurnUpCondition turnUpCondition);

    void visitSimpleTimeCondition(SimpleTimeCondition simpleTimeCondition);
}
