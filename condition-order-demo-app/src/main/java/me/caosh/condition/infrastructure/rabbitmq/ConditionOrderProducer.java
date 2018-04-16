package me.caosh.condition.infrastructure.rabbitmq;

import hbec.intellitrade.conditionorder.domain.ConditionOrder;

/**
 * Created by caosh on 2017/8/11.
 *
 * @author caoshuhao@touker.com
 */
public interface ConditionOrderProducer {
    void save(ConditionOrder conditionOrder);

    void remove(Long orderId);
}
