package me.caosh.condition.infrastructure.rabbitmq;

import hbec.intellitrade.condorder.domain.ConditionOrder;

/**
 * Created by caosh on 2017/8/11.
 *
 * @author caoshuhao@touker.com
 */
public interface ConditionOrderProducer {
    void update(ConditionOrder conditionOrder);

    void remove(Long orderId);
}
