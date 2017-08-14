package me.caosh.condition.application;

import me.caosh.condition.domain.model.order.ConditionOrder;
import me.caosh.condition.domain.model.trade.EntrustOrder;
import org.springframework.data.domain.Page;

/**
 * Created by caosh on 2017/8/14.
 */
public interface ConditionOrderQueryService {
    Page<ConditionOrder> listMonitoringOrders(String customerNo);

    ConditionOrder getConditionOrder(Long orderId);

    Page<EntrustOrder> listEntrustOrders(String customerNo);
}
