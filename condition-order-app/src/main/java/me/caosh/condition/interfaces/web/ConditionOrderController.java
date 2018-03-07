package me.caosh.condition.interfaces.web;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import hbec.intellitrade.trade.domain.EntrustOrder;
import me.caosh.condition.application.order.ConditionOrderCommandService;
import me.caosh.condition.interfaces.facade.ConditionOrderQueryFacade;
import me.caosh.condition.domain.dto.order.ConditionOrderDTO;
import me.caosh.condition.domain.dto.trade.EntrustOrderDTO;
import me.caosh.condition.domain.dto.trade.EntrustOrderDTOAssembler;
import me.caosh.condition.interfaces.command.PageRequestDTO;
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

    private final ConditionOrderCommandService conditionOrderCommandService;
    private final ConditionOrderQueryFacade conditionOrderQueryFacade;

    public ConditionOrderController(ConditionOrderCommandService conditionOrderCommandService,
                                    ConditionOrderQueryFacade conditionOrderQueryFacade) {
        this.conditionOrderCommandService = conditionOrderCommandService;
        this.conditionOrderQueryFacade = conditionOrderQueryFacade;
    }

    @RequestMapping("/remove")
    public Integer remove(Long orderId) {
        conditionOrderCommandService.remove(orderId);
        return 1;
    }

    @RequestMapping("/monitoring")
    public List<ConditionOrderDTO> monitoring(String customerNo) {
        return conditionOrderQueryFacade.listMonitoringOrders(customerNo);

    }

    @RequestMapping("/entrusts")
    public List<EntrustOrderDTO> entrusts(String customerNo, PageRequestDTO pageRequestDTO) {
        Page<EntrustOrder> entrustOrders = conditionOrderQueryFacade.listEntrustOrders(customerNo, pageRequestDTO.asPageable());
        return Lists.transform(entrustOrders.getContent(), new Function<EntrustOrder, EntrustOrderDTO>() {
            @Override
            public EntrustOrderDTO apply(EntrustOrder entrustOrder) {
                return EntrustOrderDTOAssembler.toDTO(entrustOrder);
            }
        });
    }
}
