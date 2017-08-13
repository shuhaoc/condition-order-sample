package me.caosh.condition.domain.dto.order.assembler;

import com.google.common.base.Preconditions;
import me.caosh.condition.domain.dto.order.ConditionDTO;
import me.caosh.condition.domain.dto.order.PriceConditionDTO;
import me.caosh.condition.domain.model.order.ConditionVisitor;
import me.caosh.condition.domain.model.order.price.PriceCondition;

/**
 * Created by caosh on 2017/8/11.
 *
 * @author caoshuhao@touker.com
 */
public class ConditionDTOBuilder implements ConditionVisitor {

    private ConditionDTO conditionDTO;

    public ConditionDTO build() {
        return Preconditions.checkNotNull(conditionDTO);
    }

    @Override
    public void visitPriceCondition(PriceCondition priceCondition) {
        this.conditionDTO = PriceConditionDTO.fromDomain(priceCondition);
    }
}
