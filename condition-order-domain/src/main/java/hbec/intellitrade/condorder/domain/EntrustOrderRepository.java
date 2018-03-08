package hbec.intellitrade.condorder.domain;

import hbec.intellitrade.trade.domain.EntrustOrder;

/**
 * Created by caosh on 2017/8/3.
 */
public interface EntrustOrderRepository {
    void save(EntrustOrder entrustOrder);
}
