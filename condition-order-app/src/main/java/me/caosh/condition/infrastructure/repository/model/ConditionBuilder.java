package me.caosh.condition.infrastructure.repository.model;

import com.google.common.base.Preconditions;
import me.caosh.condition.domain.model.order.CompareCondition;
import me.caosh.condition.domain.model.order.Condition;
import me.caosh.condition.domain.model.order.price.PriceCondition;
import me.caosh.condition.domain.model.share.ValuedEnumUtil;

/**
 * Created by caosh on 2017/8/11.
 *
 * @author caoshuhao@touker.com
 */
public class ConditionBuilder implements ConditionDOVisitor {

    private Condition condition;

    public ConditionBuilder(ConditionDO conditionDO) {
        conditionDO.accept(this);
    }

    public Condition build() {
        return Preconditions.checkNotNull(condition);
    }

    @Override
    public void visitPriceConditionDO(PriceConditionDO priceConditionDO) {
        CompareCondition compareCondition = ValuedEnumUtil.valueOf(priceConditionDO.getCompareCondition(), CompareCondition.class);
        this.condition = new PriceCondition(compareCondition, priceConditionDO.getTargetPrice());
    }
}
