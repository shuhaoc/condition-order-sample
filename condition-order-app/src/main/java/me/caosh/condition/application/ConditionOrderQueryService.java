package me.caosh.condition.application;

import me.caosh.condition.domain.model.order.ConditionOrder;
import me.caosh.condition.domain.model.trade.EntrustOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Created by caosh on 2017/8/14.
 */
public interface ConditionOrderQueryService {
    List<ConditionOrder> listMonitoringOrders(String customerNo);

    Optional<ConditionOrder> getConditionOrder(Long orderId);

    Page<EntrustOrder> listEntrustOrders(String customerNo, Pageable pageable);
}
