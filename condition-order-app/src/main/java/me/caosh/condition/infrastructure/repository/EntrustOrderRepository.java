package me.caosh.condition.infrastructure.repository;

import me.caosh.condition.domain.model.trade.EntrustOrder;

/**
 * Created by caosh on 2017/8/3.
 */
public interface EntrustOrderRepository {
    void save(EntrustOrder entrustOrder);
}
