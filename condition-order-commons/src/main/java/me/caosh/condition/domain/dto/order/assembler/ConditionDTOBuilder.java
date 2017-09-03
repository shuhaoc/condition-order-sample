package me.caosh.condition.domain.dto.order.assembler;

import com.google.common.base.Preconditions;
import me.caosh.condition.domain.dto.order.*;
import me.caosh.condition.domain.model.order.Condition;
import me.caosh.condition.domain.model.order.ConditionVisitor;
import me.caosh.condition.domain.model.order.grid.GridCondition;
import me.caosh.condition.domain.model.order.newstock.NewStockPurchaseCondition;
import me.caosh.condition.domain.model.order.price.PriceCondition;
import me.caosh.condition.domain.model.order.time.SimpleTimeCondition;
import me.caosh.condition.domain.model.order.turnpoint.TurnUpCondition;
import me.caosh.condition.domain.util.DateFormats;
import me.caosh.condition.domain.util.InstantUtils;

import java.util.Date;

/**
 * Created by caosh on 2017/8/11.
 *
 * @author caoshuhao@touker.com
 */
public class ConditionDTOBuilder implements ConditionVisitor {

    private ConditionDTO conditionDTO;

    public ConditionDTOBuilder(Condition condition) {
        condition.accept(this);
    }

    public ConditionDTO build() {
        return Preconditions.checkNotNull(conditionDTO);
    }

    @Override
    public void visitPriceCondition(PriceCondition priceCondition) {
        this.conditionDTO = new PriceConditionDTO(priceCondition.getCompareCondition().getValue(),
                priceCondition.getTargetPrice());
    }

    @Override
    public void visitTurnUpCondition(TurnUpCondition turnUpCondition) {
        this.conditionDTO = new TurnUpConditionDTO(turnUpCondition.getBreakPrice(), turnUpCondition.getTurnUpPercent(),
                turnUpCondition.getBroken(), turnUpCondition.getLowestPrice().orElse(null));
    }

    @Override
    public void visitSimpleTimeCondition(SimpleTimeCondition simpleTimeCondition) {
        this.conditionDTO = new SimpleTimeConditionDTO(InstantUtils.toDate(simpleTimeCondition.getTargetTime()));
    }

    @Override
    public void visitGridCondition(GridCondition gridCondition) {
        this.conditionDTO = new GridConditionDTO(gridCondition.getGridLength(), gridCondition.getBasePrice());
    }

    @Override
    public void visitNewStockPurchaseCondition(NewStockPurchaseCondition newStockPurchaseCondition) {
        String purchaseTime = DateFormats.HH_MM_SS.format(newStockPurchaseCondition.getPurchaseTime());
        int purchasedCount = newStockPurchaseCondition.getPurchasedCount();
        Date lastTriggerDate = newStockPurchaseCondition.getLastTriggerDate().isPresent()
                ? InstantUtils.toDate(newStockPurchaseCondition.getLastTriggerDate().get())
                : null;
        this.conditionDTO = new NewStockPurchaseConditionDTO(purchaseTime, purchasedCount, lastTriggerDate);
    }
}
