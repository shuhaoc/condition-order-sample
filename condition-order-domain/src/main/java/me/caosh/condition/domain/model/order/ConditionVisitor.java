package me.caosh.condition.domain.model.order;

import me.caosh.condition.domain.model.condition.PriceCondition;
import me.caosh.condition.domain.model.condition.TimeReachedCondition;
import me.caosh.condition.domain.model.condition.TurnUpCondition;
import me.caosh.condition.domain.model.order.grid.GridCondition;
import me.caosh.condition.domain.model.order.newstock.NewStockPurchaseCondition;

/**
 * Created by caosh on 2017/8/11.
 *
 * @author caoshuhao@touker.com
 */
public interface ConditionVisitor {
    void visitPriceCondition(PriceCondition priceCondition);

    void visitTurnUpCondition(TurnUpCondition turnUpCondition);

    void visitSimpleTimeCondition(TimeReachedCondition timeReachedCondition);

    void visitGridCondition(GridCondition gridCondition);

    void visitNewStockPurchaseCondition(NewStockPurchaseCondition newStockPurchaseCondition);
}
