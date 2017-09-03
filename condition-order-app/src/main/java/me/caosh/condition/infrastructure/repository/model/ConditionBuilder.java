package me.caosh.condition.infrastructure.repository.model;

import com.google.common.base.Preconditions;
import me.caosh.condition.domain.model.order.Condition;
import me.caosh.condition.domain.model.order.constant.CompareCondition;
import me.caosh.condition.domain.model.order.grid.GridCondition;
import me.caosh.condition.domain.model.order.newstock.NewStockPurchaseCondition;
import me.caosh.condition.domain.model.order.price.PriceCondition;
import me.caosh.condition.domain.model.order.time.SimpleTimeCondition;
import me.caosh.condition.domain.model.order.turnpoint.TurnUpCondition;
import me.caosh.condition.domain.model.share.ValuedEnumUtil;
import me.caosh.condition.domain.util.DateFormats;
import me.caosh.condition.domain.util.InstantUtils;

import java.time.LocalDate;
import java.time.LocalTime;

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
        CompareCondition compareCondition = ValuedEnumUtil.valueOf(priceConditionDO.getCompareCondition(), CompareCondition.class);
        this.condition = new PriceCondition(compareCondition, priceConditionDO.getTargetPrice());
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
        this.condition = new SimpleTimeCondition(InstantUtils.toLocalDateTime(simpleTimeConditionDO.getTargetTime()));
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
