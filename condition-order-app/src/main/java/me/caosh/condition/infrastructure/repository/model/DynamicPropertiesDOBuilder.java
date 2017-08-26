package me.caosh.condition.infrastructure.repository.model;

import me.caosh.condition.domain.model.order.Condition;
import me.caosh.condition.domain.model.order.ConditionVisitor;
import me.caosh.condition.domain.model.order.grid.GridCondition;
import me.caosh.condition.domain.model.order.price.PriceCondition;
import me.caosh.condition.domain.model.order.time.SimpleTimeCondition;
import me.caosh.condition.domain.model.order.turnpoint.TurnUpCondition;

/**
 * Created by caosh on 2017/8/15.
 */
public class DynamicPropertiesDOBuilder implements ConditionVisitor {

    private DynamicPropertiesDO dynamicPropertiesDO;

    public DynamicPropertiesDOBuilder(Condition condition) {
        condition.accept(this);
    }

    public DynamicPropertiesDO build() {
        return dynamicPropertiesDO;
    }

    @Override
    public void visitPriceCondition(PriceCondition priceCondition) {
    }

    @Override
    public void visitTurnUpCondition(TurnUpCondition turnUpCondition) {
        this.dynamicPropertiesDO = new TurnUpDynamicPropertiesDO(turnUpCondition.getBroken(), turnUpCondition.getLowestPrice().orElse(null));
    }

    @Override
    public void visitSimpleTimeCondition(SimpleTimeCondition simpleTimeCondition) {
    }

    @Override
    public void visitGridCondition(GridCondition gridCondition) {
        this.dynamicPropertiesDO = new GridDynamicPropertiesDO(gridCondition.getBasePrice());
    }
}
