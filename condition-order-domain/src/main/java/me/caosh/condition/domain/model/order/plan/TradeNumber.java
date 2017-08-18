package me.caosh.condition.domain.model.order.plan;

import me.caosh.condition.domain.model.constants.EntrustMethod;

import java.math.BigDecimal;

/**
 * Created by caosh on 2017/8/17.
 */
public interface TradeNumber {
    void accept(TradeNumberVisitor visitor);

    EntrustMethod getEntrustMethod();

    int getNumber(BigDecimal price);
}
