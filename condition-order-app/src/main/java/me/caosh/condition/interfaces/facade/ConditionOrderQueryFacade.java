package me.caosh.condition.interfaces.facade;

import hbec.intellitrade.trade.domain.EntrustOrder;
import me.caosh.condition.domain.dto.order.ConditionOrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by caosh on 2017/8/14.
 */
public interface ConditionOrderQueryFacade {
    List<ConditionOrderDTO> listMonitoringOrders(String customerNo);

    ConditionOrderDTO getConditionOrder(Long orderId);

    Page<EntrustOrder> listEntrustOrders(String customerNo, Pageable pageable);
}
