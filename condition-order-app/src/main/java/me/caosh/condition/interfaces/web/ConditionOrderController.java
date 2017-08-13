package me.caosh.condition.interfaces.web;

import me.caosh.condition.application.order.ConditionOrderCommandService;
import me.caosh.condition.domain.model.order.ConditionOrder;
import me.caosh.condition.domain.model.order.price.PriceOrder;
import me.caosh.condition.infrastructure.repository.ConditionOrderRepository;
import me.caosh.condition.infrastructure.repository.impl.ConditionOrderIdGenerator;
import me.caosh.condition.interfaces.assembler.PriceOrderCommandAssembler;
import me.caosh.condition.interfaces.command.PriceOrderCreateCommand;
import me.caosh.condition.interfaces.command.PriceOrderUpdateCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * Created by caosh on 2017/8/9.
 */
@RestController
@RequestMapping("/condition")
public class ConditionOrderController {

    private final ConditionOrderCommandService conditionOrderCommandService;

    public ConditionOrderController(ConditionOrderCommandService conditionOrderCommandService) {
        this.conditionOrderCommandService = conditionOrderCommandService;
    }

    @RequestMapping("/remove")
    public Integer remove(Long orderId) {
        conditionOrderCommandService.remove(orderId);
        return 1;
    }
}
