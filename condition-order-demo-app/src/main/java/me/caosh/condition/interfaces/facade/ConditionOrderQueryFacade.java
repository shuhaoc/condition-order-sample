package me.caosh.condition.interfaces.facade;

import hbec.commons.domain.intellitrade.conditionorder.ConditionOrderDTO;
import hbec.commons.domain.intellitrade.trade.EntrustOrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by caosh on 2017/8/14.
 */
public interface ConditionOrderQueryFacade {
    List<ConditionOrderDTO> listMonitoringOrders(String customerNo);

    ConditionOrderDTO getConditionOrder(Long orderId);

    Page<EntrustOrderDTO> listEntrustOrders(String customerNo, Pageable pageable);
}
