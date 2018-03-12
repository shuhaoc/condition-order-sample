package me.caosh.condition.interfaces.web;

import me.caosh.condition.application.order.OrderCommandService;
import me.caosh.condition.domain.dto.order.ConditionOrderDTO;
import me.caosh.condition.domain.dto.trade.EntrustOrderDTO;
import me.caosh.condition.interfaces.command.PageRequestDTO;
import me.caosh.condition.interfaces.facade.ConditionOrderQueryFacade;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by caosh on 2017/8/9.
 */
@RestController
@RequestMapping("/condition")
public class ConditionOrderController {

    private final OrderCommandService orderCommandService;
    private final ConditionOrderQueryFacade conditionOrderQueryFacade;

    public ConditionOrderController(OrderCommandService orderCommandService,
                                    ConditionOrderQueryFacade conditionOrderQueryFacade) {
        this.orderCommandService = orderCommandService;
        this.conditionOrderQueryFacade = conditionOrderQueryFacade;
    }

    @RequestMapping("/remove")
    public Integer remove(Long orderId) {
        orderCommandService.remove(orderId);
        return 1;
    }

    @RequestMapping("/monitoring")
    public List<ConditionOrderDTO> monitoring(String customerNo) {
        return conditionOrderQueryFacade.listMonitoringOrders(customerNo);
    }

    @RequestMapping("/entrusts")
    public List<EntrustOrderDTO> entrusts(String customerNo, PageRequestDTO pageRequestDTO) {
        Page<EntrustOrderDTO> entrustOrders = conditionOrderQueryFacade.listEntrustOrders(customerNo, pageRequestDTO.asPageable());
        return entrustOrders.getContent();
    }
}
