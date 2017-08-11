package me.caosh.condition.domain.dto.order.assembler;

import com.google.common.base.Preconditions;
import me.caosh.condition.domain.dto.order.ConditionDTOVisitor;
import me.caosh.condition.domain.dto.order.PriceConditionDTO;
import me.caosh.condition.domain.model.order.CompareCondition;
import me.caosh.condition.domain.model.order.Condition;
import me.caosh.condition.domain.model.order.PriceCondition;
import me.caosh.condition.domain.model.share.ValuedEnumUtil;

/**
 * Created by caosh on 2017/8/11.
 *
 * @author caoshuhao@touker.com
 */
public class ConditionBuilder implements ConditionDTOVisitor {

    private Condition condition;

    public Condition build() {
        return Preconditions.checkNotNull(condition);
    }

    @Override
    public void visitPriceConditionDTO(PriceConditionDTO priceConditionDTO) {
        CompareCondition compareCondition = ValuedEnumUtil.valueOf(priceConditionDTO.getCompareCondition(), CompareCondition.class);
        this.condition = new PriceCondition(compareCondition, priceConditionDTO.getTargetPrice());
    }
}
