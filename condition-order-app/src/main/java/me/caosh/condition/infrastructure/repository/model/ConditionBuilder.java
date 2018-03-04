package me.caosh.condition.infrastructure.repository.model;

import com.google.common.base.Preconditions;
import hbec.intellitrade.strategy.domain.factor.CompareOperator;
import me.caosh.condition.domain.model.condition.PriceCondition;
import me.caosh.condition.domain.model.condition.TimeReachedCondition;
import me.caosh.condition.domain.model.condition.TurnUpCondition;
import me.caosh.condition.domain.model.order.grid.GridCondition;
import me.caosh.condition.domain.model.order.newstock.NewStockPurchaseCondition;
import me.caosh.condition.domain.model.share.ValuedEnumUtil;
import me.caosh.condition.domain.model.strategy.condition.Condition;
import me.caosh.condition.domain.util.DateFormats;
import me.caosh.condition.domain.util.InstantUtils;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;


/**
 * Created by caosh on 2017/8/11.
 *
 * @author caoshuhao@touker.com
 */
public class ConditionBuilder implements ConditionDOVisitor, DynamicPropertiesDOVisitor {

    private Condition condition;

    public ConditionBuilder(ConditionDO conditionDO, DynamicPropertiesDO dynamicPropertiesDO) {
        conditionDO.accept(this);
        if (dynamicPropertiesDO != null) {
            dynamicPropertiesDO.accept(this);
        }
    }

    public Condition build() {
        return Preconditions.checkNotNull(condition);
    }

    @Override
    public void visitPriceConditionDO(PriceConditionDO priceConditionDO) {
        CompareOperator compareOperator = ValuedEnumUtil.valueOf(priceConditionDO.getCompareOperator(), CompareOperator.class);
        this.condition = new PriceCondition(compareOperator, priceConditionDO.getTargetPrice());
    }

    @Override
    public void visitTurnUpConditionDO(TurnUpConditionDO turnUpConditionDO) {
        this.condition = new TurnUpCondition(turnUpConditionDO.getBreakPrice(), turnUpConditionDO.getTurnUpPercent());
    }

    @Override
    public void visitTurnUpDynamicPropertiesDO(TurnUpDynamicPropertiesDO turnUpDynamicPropertiesDO) {
        Preconditions.checkNotNull(condition);
        TurnUpCondition turnUpCondition = (TurnUpCondition) condition;
        this.condition = new TurnUpCondition(turnUpCondition.getBreakPrice(), turnUpCondition.getTurnUpPercent(),
                turnUpDynamicPropertiesDO.getBroken(), turnUpDynamicPropertiesDO.getLowestPrice());
    }

    @Override
    public void visitSimpleTimeConditionDO(SimpleTimeConditionDO simpleTimeConditionDO) {
        this.condition = new TimeReachedCondition(InstantUtils.toLocalDateTime(simpleTimeConditionDO.getTargetTime()));
    }

    @Override
    public void visitGridConditionDO(GridConditionDO gridConditionDO) {
        this.condition = new GridCondition((gridConditionDO.getGridLength()));
    }

    @Override
    public void visitGridDynamicPropertiesDO(GridDynamicPropertiesDO gridDynamicPropertiesDO) {
        Preconditions.checkNotNull(condition);
        GridCondition gridCondition = (GridCondition) condition;
        this.condition = new GridCondition(gridCondition.getGridLength(), gridDynamicPropertiesDO.getBasePrice());
    }

    @Override
    public void visitNewStockPurchaseConditionDO(NewStockPurchaseConditionDO newStockPurchaseConditionDO) {
        LocalTime purchaseTime = LocalTime.parse(newStockPurchaseConditionDO.getPurchaseTime(), DateFormats.HH_MM_SS);
        this.condition = new NewStockPurchaseCondition(purchaseTime);
    }

    @Override
    public void visitNewStockPurchaseDynamicPropertiesDO(NewStockPurchaseDynamicPropertiesDO newStockPurchaseDynamicPropertiesDO) {
        Preconditions.checkNotNull(condition);
        NewStockPurchaseCondition newStockPurchaseCondition = (NewStockPurchaseCondition) condition;
        int purchasedCount = newStockPurchaseDynamicPropertiesDO.getPurchasedCount();
        LocalDate lastTriggerDate = newStockPurchaseDynamicPropertiesDO.getLastTriggerDate() != null
                ? InstantUtils.toLocalDate(newStockPurchaseDynamicPropertiesDO.getLastTriggerDate())
                : null;
        this.condition = new NewStockPurchaseCondition(newStockPurchaseCondition.getPurchaseTime(), purchasedCount, lastTriggerDate);
    }
}
