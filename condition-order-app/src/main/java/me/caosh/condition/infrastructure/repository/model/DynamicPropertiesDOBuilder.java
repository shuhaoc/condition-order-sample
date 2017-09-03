package me.caosh.condition.infrastructure.repository.model;

import me.caosh.condition.domain.model.order.Condition;
import me.caosh.condition.domain.model.order.ConditionVisitor;
import me.caosh.condition.domain.model.order.grid.GridCondition;
import me.caosh.condition.domain.model.order.newstock.NewStockPurchaseCondition;
import me.caosh.condition.domain.model.order.price.PriceCondition;
import me.caosh.condition.domain.model.order.time.SimpleTimeCondition;
import me.caosh.condition.domain.model.order.turnpoint.TurnUpCondition;
import me.caosh.condition.domain.util.InstantUtils;

import java.util.Date;

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

    @Override
    public void visitNewStockPurchaseCondition(NewStockPurchaseCondition newStockPurchaseCondition) {
        int purchasedCount = newStockPurchaseCondition.getPurchasedCount();
        Date lastTriggerDate = newStockPurchaseCondition.getLastTriggerDate().isPresent()
                ? InstantUtils.toDate(newStockPurchaseCondition.getLastTriggerDate().get())
                : null;
        this.dynamicPropertiesDO = new NewStockPurchaseDynamicPropertiesDO(purchasedCount, lastTriggerDate);
    }
}
