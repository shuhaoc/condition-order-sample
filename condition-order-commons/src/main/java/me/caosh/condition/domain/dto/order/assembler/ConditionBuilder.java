package me.caosh.condition.domain.dto.order.assembler;

import com.google.common.base.Preconditions;
import me.caosh.condition.domain.dto.order.ConditionDTO;
import me.caosh.condition.domain.dto.order.ConditionDTOVisitor;
import me.caosh.condition.domain.dto.order.GridConditionDTO;
import me.caosh.condition.domain.dto.order.NewStockPurchaseConditionDTO;
import me.caosh.condition.domain.dto.order.PriceConditionDTO;
import me.caosh.condition.domain.dto.order.SimpleTimeConditionDTO;
import me.caosh.condition.domain.dto.order.TurnUpConditionDTO;
import me.caosh.condition.domain.model.condition.PriceCondition;
import me.caosh.condition.domain.model.condition.TimeReachedCondition;
import me.caosh.condition.domain.model.condition.TurnUpCondition;
import me.caosh.condition.domain.model.order.grid.GridCondition;
import me.caosh.condition.domain.model.order.newstock.NewStockPurchaseCondition;
import me.caosh.condition.domain.model.share.ValuedEnumUtil;
import me.caosh.condition.domain.model.strategy.condition.Condition;
import me.caosh.condition.domain.model.strategy.factor.CompareOperator;
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
        CompareOperator compareOperator = ValuedEnumUtil.valueOf(priceConditionDTO.getCompareCondition(), CompareOperator.class);
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
