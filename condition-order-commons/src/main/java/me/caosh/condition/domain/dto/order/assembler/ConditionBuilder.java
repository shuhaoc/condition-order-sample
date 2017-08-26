package me.caosh.condition.domain.dto.order.assembler;

import com.google.common.base.Preconditions;
import me.caosh.condition.domain.dto.order.*;
import me.caosh.condition.domain.model.order.Condition;
import me.caosh.condition.domain.model.order.constant.CompareCondition;
import me.caosh.condition.domain.model.order.grid.GridCondition;
import me.caosh.condition.domain.model.order.price.PriceCondition;
import me.caosh.condition.domain.model.order.time.SimpleTimeCondition;
import me.caosh.condition.domain.model.order.turnpoint.TurnUpCondition;
import me.caosh.condition.domain.model.share.ValuedEnumUtil;
import me.caosh.condition.domain.util.InstantUtils;

/**
 * Created by caosh on 2017/8/11.
 *
 * @author caoshuhao@touker.com
 */
public class ConditionBuilder implements ConditionDTOVisitor {

    private Condition condition;

    public ConditionBuilder (ConditionDTO conditionDTO) {
        conditionDTO.accept(this);
    }

    public Condition build() {
        return Preconditions.checkNotNull(condition);
    }

    @Override
    public void visitPriceConditionDTO(PriceConditionDTO priceConditionDTO) {
        CompareCondition compareCondition = ValuedEnumUtil.valueOf(priceConditionDTO.getCompareCondition(), CompareCondition.class);
        this.condition = new PriceCondition(compareCondition, priceConditionDTO.getTargetPrice());
    }

    @Override
    public void visitTurnUpConditionDTO(TurnUpConditionDTO turnUpConditionDTO) {
        this.condition = new TurnUpCondition(turnUpConditionDTO.getBreakPrice(), turnUpConditionDTO.getTurnUpPercent(),
                turnUpConditionDTO.getBroken(), turnUpConditionDTO.getLowestPrice());
    }

    @Override
    public void visitSimpleTimeConditionDTO(SimpleTimeConditionDTO simpleTimeConditionDTO) {
        this.condition = new SimpleTimeCondition(InstantUtils.toLocalDateTime(simpleTimeConditionDTO.getTargetTime()));
    }

    @Override
    public void visitGridConditionDTO(GridConditionDTO gridConditionDTO) {
        this.condition = new GridCondition(gridConditionDTO.getGridLength(), gridConditionDTO.getBasePrice());
    }
}
