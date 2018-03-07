package me.caosh.condition.infrastructure.repository.model;

import hbec.intellitrade.condorder.domain.ConditionVisitor;
import hbec.intellitrade.strategy.domain.condition.Condition;
import hbec.intellitrade.strategy.domain.strategies.condition.PriceCondition;
import me.caosh.condition.domain.model.condition.TimeReachedCondition;
import me.caosh.condition.domain.model.condition.TurnUpCondition;
import me.caosh.condition.domain.model.order.grid.GridCondition;
import me.caosh.condition.domain.model.order.newstock.NewStockPurchaseCondition;
import me.caosh.condition.domain.util.DateFormats;
import me.caosh.condition.domain.util.InstantUtils;

/**
 * Created by caosh on 2017/8/15.
 */
public class ConditionDOBuilder implements ConditionVisitor {

    private ConditionDO conditionDO;

    public ConditionDOBuilder(Condition condition) {
        condition.accept(this);
    }

    public ConditionDO build() {
        return conditionDO;
    }

    @Override
    public void visitPriceCondition(PriceCondition priceCondition) {
        this.conditionDO = new PriceConditionDO(priceCondition.getCompareOperator().getValue(), priceCondition.getTargetPrice());
    }

    @Override
    public void visitTurnUpCondition(TurnUpCondition turnUpCondition) {
        this.conditionDO = new TurnUpConditionDO(turnUpCondition.getBreakPrice(), turnUpCondition.getTurnUpPercent());
    }

    @Override
    public void visitSimpleTimeCondition(TimeReachedCondition timeReachedCondition) {
        this.conditionDO = new SimpleTimeConditionDO(InstantUtils.toDate(timeReachedCondition.getTargetTime()));
    }

    @Override
    public void visitGridCondition(GridCondition gridCondition) {
        this.conditionDO = new GridConditionDO(gridCondition.getGridLength());
    }

    @Override
    public void visitNewStockPurchaseCondition(NewStockPurchaseCondition newStockPurchaseCondition) {
        String purchaseTime = DateFormats.HH_MM_SS.print(newStockPurchaseCondition.getPurchaseTime());
        this.conditionDO = new NewStockPurchaseConditionDO(purchaseTime);
    }
}
