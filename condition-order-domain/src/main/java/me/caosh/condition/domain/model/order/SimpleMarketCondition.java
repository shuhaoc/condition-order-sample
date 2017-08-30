package me.caosh.condition.domain.model.order;

import java.math.BigDecimal;

/**
 * Created by caosh on 2017/8/19.
 */
public interface SimpleMarketCondition extends Condition {
    boolean isSatisfiedBy(BigDecimal price);
}
