package me.caosh.condition.infrastructure.tunnel.model;

import me.caosh.autoasm.RuntimeType;

/**
 * Created by caosh on 2017/8/15.
 */
@RuntimeType({
        PriceConditionDO.class,
        TurnPointConditionDO.class,
        TimeReachedConditionDO.class,
//        GridConditionDO.class,
        NewStockPurchaseConditionDO.class
})
public interface ConditionDO {
}
