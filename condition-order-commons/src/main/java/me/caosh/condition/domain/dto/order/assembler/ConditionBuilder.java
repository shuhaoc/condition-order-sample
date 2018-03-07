package me.caosh.condition.domain.dto.order.assembler;

import com.google.common.base.Preconditions;
import hbec.intellitrade.strategy.domain.factor.CompareOperator;
import me.caosh.condition.domain.dto.order.*;
import hbec.intellitrade.strategy.domain.strategies.condition.PriceCondition;
import me.caosh.condition.domain.model.condition.TimeReachedCondition;
import me.caosh.condition.domain.model.condition.TurnUpCondition;
import me.caosh.condition.domain.model.order.grid.GridCondition;
import me.caosh.condition.domain.model.order.newstock.NewStockPurchaseCondition;
import hbec.intellitrade.common.ValuedEnumUtil;
import hbec.intellitrade.strategy.domain.condition.Condition;
import me.caosh.condition.domain.util.DateFormats;
import me.caosh.condition.domain.util.InstantUtils;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;


/**
 * Created by caosh on 2017/8/11.
 *
 * @author caoshuhao@touker.com
 */
public class ConditionBuilder implements ConditionDTOVisitor {

    private Condition condition;

    public ConditionBuilder(ConditionDTO conditionDTO) {
        conditionDTO.accept(this);
    }

    public Condition build() {
        return Preconditions.checkNotNull(condition);
    }

    @Override
    public void visitPriceConditionDTO(PriceConditionDTO priceConditionDTO) {
        CompareOperator compareOperator = ValuedEnumUtil.valueOf(priceConditionDTO.getCompareOperator(), CompareOperator.class);
        this.condition = new PriceCondition(compareOperator, priceConditionDTO.getTargetPrice());
    }

    @Override
    public void visitTurnUpConditionDTO(TurnUpConditionDTO turnUpConditionDTO) {
        this.condition = new TurnUpCondition(turnUpConditionDTO.getBreakPrice(), turnUpConditionDTO.getTurnUpPercent(),
                turnUpConditionDTO.getBroken(), turnUpConditionDTO.getLowestPrice());
    }

    @Override
    public void visitSimpleTimeConditionDTO(SimpleTimeConditionDTO simpleTimeConditionDTO) {
        this.condition = new TimeReachedCondition(InstantUtils.toLocalDateTime(simpleTimeConditionDTO.getTargetTime()));
    }

    @Override
    public void visitGridConditionDTO(GridConditionDTO gridConditionDTO) {
        this.condition = new GridCondition(gridConditionDTO.getGridLength(), gridConditionDTO.getBasePrice());
    }

    @Override
    public void visitNewStockPurchaseConditionDTO(NewStockPurchaseConditionDTO newStockPurchaseConditionDTO) {
        LocalTime purchaseTime = LocalTime.parse(newStockPurchaseConditionDTO.getPurchaseTime(), DateFormats.HH_MM_SS);
        int purchasedCount = newStockPurchaseConditionDTO.getPurchasedCount();
        LocalDate lastTriggerDate = newStockPurchaseConditionDTO.getLastTriggerDate() != null
                ? InstantUtils.toLocalDate(newStockPurchaseConditionDTO.getLastTriggerDate())
                : null;
        this.condition = new NewStockPurchaseCondition(purchaseTime, purchasedCount, lastTriggerDate);

    }
}
