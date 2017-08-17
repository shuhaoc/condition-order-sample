package me.caosh.condition.domain.model.order.plan;

import java.math.BigDecimal;

/**
 * Created by caosh on 2017/8/17.
 */
public interface TradeNumber {
    int getNumber(BigDecimal price);
}
