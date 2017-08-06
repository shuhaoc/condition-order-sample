package me.caosh.condition.domain.model.order;

import me.caosh.condition.domain.model.market.SecurityInfo;

import java.time.LocalDateTime;

/**
 * Created by caosh on 2017/8/2.
 */
public interface ConditionOrder {
    Long getOrderId();

    OrderState getOrderState();

    SecurityInfo getSecurityInfo();

    Condition getCondition();

    TradePlan getTradePlan();

    LocalDateTime getCreateTime();

    LocalDateTime getUpdateTime();
}
