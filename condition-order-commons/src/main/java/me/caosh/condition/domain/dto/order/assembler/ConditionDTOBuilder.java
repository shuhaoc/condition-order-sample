package me.caosh.condition.domain.dto.order.assembler;

import com.google.common.base.Preconditions;
import me.caosh.condition.domain.dto.order.ConditionDTO;
import me.caosh.condition.domain.dto.order.GridConditionDTO;
import me.caosh.condition.domain.dto.order.NewStockPurchaseConditionDTO;
import me.caosh.condition.domain.dto.order.PriceConditionDTO;
import me.caosh.condition.domain.dto.order.SimpleTimeConditionDTO;
import me.caosh.condition.domain.dto.order.TurnUpConditionDTO;
import me.caosh.condition.domain.model.condition.PriceCondition;
import me.caosh.condition.domain.model.condition.TimeReachedCondition;
import me.caosh.condition.domain.model.condition.TurnUpCondition;
import me.caosh.condition.domain.model.order.ConditionVisitor;
import me.caosh.condition.domain.model.order.grid.GridCondition;
import me.caosh.condition.domain.model.order.newstock.NewStockPurchaseCondition;
import hbec.intellitrade.strategy.domain.condition.Condition;
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
        this.conditionDTO = new PriceConditionDTO(priceCondition.getCompareOperator().getValue(),
                priceCondition.getTargetPrice());
    }

    @Override
    public void visitTurnUpCondition(TurnUpCondition turnUpCondition) {
        this.conditionDTO = new TurnUpConditionDTO(turnUpCondition.getBreakPrice(), turnUpCondition.getTurnUpPercent(),
                turnUpCondition.isBroken(), turnUpCondition.getLowestPrice().orNull());
    }

    @Override
    public void visitSimpleTimeCondition(TimeReachedCondition timeReachedCondition) {
        this.conditionDTO = new SimpleTimeConditionDTO(InstantUtils.toDate(timeReachedCondition.getTargetTime()));
    }

    @Override
    public void visitGridCondition(GridCondition gridCondition) {
        this.conditionDTO = new GridConditionDTO(gridCondition.getGridLength(), gridCondition.getBasePrice());
    }

    @Override
    public void visitNewStockPurchaseCondition(NewStockPurchaseCondition newStockPurchaseCondition) {
        String purchaseTime = DateFormats.HH_MM_SS.print(newStockPurchaseCondition.getPurchaseTime());
        int purchasedCount = newStockPurchaseCondition.getPurchasedCount();
        Date lastTriggerDate = newStockPurchaseCondition.getLastTriggerDate().isPresent()
                ? InstantUtils.toDate(newStockPurchaseCondition.getLastTriggerDate().get())
                : null;
        this.conditionDTO = new NewStockPurchaseConditionDTO(purchaseTime, purchasedCount, lastTriggerDate);
    }
}
