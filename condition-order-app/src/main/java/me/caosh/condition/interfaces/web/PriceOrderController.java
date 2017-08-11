package me.caosh.condition.interfaces.web;

import me.caosh.condition.application.order.ConditionOrderCommandService;
import me.caosh.condition.domain.model.order.ConditionOrder;
import me.caosh.condition.domain.model.order.PriceOrder;
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
@RequestMapping("/price")
public class PriceOrderController {

    private final ConditionOrderIdGenerator idGenerator;
    private final ConditionOrderCommandService conditionOrderCommandService;
    private final ConditionOrderRepository conditionOrderRepository;

    @Autowired
    public PriceOrderController(ConditionOrderIdGenerator idGenerator,
                                ConditionOrderCommandService conditionOrderCommandService,
                                ConditionOrderRepository conditionOrderRepository) {
        this.idGenerator = idGenerator;
        this.conditionOrderCommandService = conditionOrderCommandService;
        this.conditionOrderRepository = conditionOrderRepository;
    }

    @RequestMapping("/create")
    public Long create(PriceOrderCreateCommand command) {
        Long orderId = idGenerator.nextId();
        PriceOrder priceOrder = PriceOrderCommandAssembler.assemblePriceOrder(orderId, command);
        conditionOrderCommandService.save(priceOrder);
        return orderId;
    }

    @RequestMapping("/update")
    public Long update(PriceOrderUpdateCommand command) {
        Long orderId = command.getOrderId();
        Optional<ConditionOrder> conditionOrderOptional = conditionOrderRepository.findOne(orderId);
        if (!conditionOrderOptional.isPresent()) {
            return -1L;
        }
        PriceOrder oldPriceOrder = (PriceOrder) conditionOrderOptional.get();
        PriceOrder newPriceOlder = PriceOrderCommandAssembler.mergePriceOrder(oldPriceOrder, command);
        conditionOrderCommandService.save(newPriceOlder);
        return orderId;
    }
}
