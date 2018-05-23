package me.caosh.condition.infrastructure.tunnel.model;

import me.caosh.autoasm.RuntimeType;

/**
 * Created by caosh on 2017/8/15.
 */
@RuntimeType({
        PriceConditionDO.class,
        TurnPointConditionDO.class,
        TimeReachedConditionDO.class,
        NewStockPurchaseConditionDO.class,
        GridConditionDO.class
})
public interface ConditionDO {
}
