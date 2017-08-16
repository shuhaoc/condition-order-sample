package me.caosh.condition.interfaces.web;

import com.google.common.collect.Lists;
import me.caosh.condition.application.ConditionOrderQueryService;
import me.caosh.condition.application.order.ConditionOrderCommandService;
import me.caosh.condition.domain.dto.order.ConditionOrderDTO;
import me.caosh.condition.domain.dto.order.assembler.ConditionOrderDTOAssembler;
import me.caosh.condition.domain.dto.trade.EntrustOrderDTO;
import me.caosh.condition.domain.dto.trade.EntrustOrderDTOAssembler;
import me.caosh.condition.domain.model.order.ConditionOrder;
import me.caosh.condition.domain.model.order.price.PriceOrder;
import me.caosh.condition.domain.model.trade.EntrustOrder;
import me.caosh.condition.infrastructure.repository.ConditionOrderRepository;
import me.caosh.condition.infrastructure.repository.impl.ConditionOrderIdGenerator;
import me.caosh.condition.interfaces.assembler.PriceOrderCommandAssembler;
import me.caosh.condition.interfaces.command.PageRequestDTO;
import me.caosh.condition.interfaces.command.PriceOrderCreateCommand;
import me.caosh.condition.interfaces.command.PriceOrderUpdateCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * Created by caosh on 2017/8/9.
 */
@RestController
@RequestMapping("/condition")
public class ConditionOrderController {

    private final ConditionOrderCommandService conditionOrderCommandService;
    private final ConditionOrderQueryService conditionOrderQueryService;

    public ConditionOrderController(ConditionOrderCommandService conditionOrderCommandService,
                                    ConditionOrderQueryService conditionOrderQueryService) {
        this.conditionOrderCommandService = conditionOrderCommandService;
        this.conditionOrderQueryService = conditionOrderQueryService;
    }

    @RequestMapping("/remove")
    public Integer remove(Long orderId) {
        conditionOrderCommandService.remove(orderId);
        return 1;
    }

    @RequestMapping("/monitoring")
    public List<ConditionOrderDTO> monitoring(String customerNo) {
        List<ConditionOrder> conditionOrders = conditionOrderQueryService.listMonitoringOrders(customerNo);
        return Lists.transform(conditionOrders, ConditionOrderDTOAssembler::toDTO);
    }

    @RequestMapping("/entrusts")
    public List<EntrustOrderDTO> entrusts(String customerNo, PageRequestDTO pageRequestDTO) {
        Page<EntrustOrder> entrustOrders = conditionOrderQueryService.listEntrustOrders(customerNo, pageRequestDTO.asPageable());
        return Lists.transform(entrustOrders.getContent(), EntrustOrderDTOAssembler::toDTO);
    }
}
