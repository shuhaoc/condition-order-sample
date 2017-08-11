package me.caosh.condition.infrastructure.rabbitmq;

import me.caosh.condition.domain.model.order.ConditionOrder;

/**
 * Created by caosh on 2017/8/11.
 *
 * @author caoshuhao@touker.com
 */
public interface ConditionOrderProducer {
    void send(ConditionOrder conditionOrder);
}
