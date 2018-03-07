package me.caosh.condition.infrastructure.tunnel.model;

import me.caosh.autoasm.RuntimeType;
import me.caosh.condition.domain.model.order.newstock.NewStockPurchaseCondition;

/**
 * Created by caosh on 2017/8/15.
 */
@RuntimeType({
        PriceConditionDO.class,
        TurnUpConditionDO.class,
        SimpleTimeConditionDO.class,
        GridConditionDO.class,
        NewStockPurchaseCondition.class
})
public interface ConditionDO {
}
