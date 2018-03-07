package me.caosh.condition.interfaces.web;

import com.google.common.base.Optional;
import hbec.intellitrade.condorder.domain.ConditionOrder;
import hbec.intellitrade.condorder.domain.StrategyState;
import hbec.intellitrade.condorder.domain.TradeCustomerInfo;
import hbec.intellitrade.condorder.domain.orders.PriceOrder;
import me.caosh.condition.application.order.ConditionOrderCommandService;
import me.caosh.condition.infrastructure.repository.ConditionOrderRepository;
import me.caosh.condition.infrastructure.repository.impl.ConditionOrderIdGenerator;
import me.caosh.condition.interfaces.assembler.PriceOrderCommandAssembler;
import me.caosh.condition.interfaces.command.PriceOrderCreateCommand;
import me.caosh.condition.interfaces.command.PriceOrderUpdateCommand;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Created by caosh on 2017/8/9.
 */
@RestController
@RequestMapping("/price")
public class PriceOrderController {

    private final ConditionOrderIdGenerator idGenerator;
    private final ConditionOrderCommandService conditionOrderCommandService;
    private final ConditionOrderRepository conditionOrderRepository;

    public PriceOrderController(ConditionOrderIdGenerator idGenerator,
                                ConditionOrderCommandService conditionOrderCommandService,
                                ConditionOrderRepository conditionOrderRepository) {
        this.idGenerator = idGenerator;
        this.conditionOrderCommandService = conditionOrderCommandService;
        this.conditionOrderRepository = conditionOrderRepository;
    }

    @RequestMapping("/create")
    public Long create(@Valid PriceOrderCreateCommand command) {
        Long orderId = idGenerator.nextId();
        TradeCustomerInfo tradeCustomerInfo = new TradeCustomerInfo(303348, "010000061086");
        PriceOrder priceOrder = PriceOrderCommandAssembler.assemblePriceOrder(orderId, tradeCustomerInfo, command);
        conditionOrderCommandService.save(priceOrder);
        return orderId;
    }

    @RequestMapping("/update")
    public Long update(@Valid PriceOrderUpdateCommand command) {
        Long orderId = command.getOrderId();
        Optional<ConditionOrder> conditionOrderOptional = conditionOrderRepository.findOne(orderId);
        if (!conditionOrderOptional.isPresent()) {
            return -1L;
        }
        ConditionOrder conditionOrder = conditionOrderOptional.get();
        if (conditionOrder.getStrategyState() != StrategyState.ACTIVE && conditionOrder.getStrategyState() != StrategyState.PAUSED) {
            return -2L;
        }
        PriceOrder oldPriceOrder = (PriceOrder) conditionOrder;
        PriceOrder newPriceOlder = PriceOrderCommandAssembler.mergePriceOrder(oldPriceOrder, command);
        conditionOrderCommandService.update(newPriceOlder);
        return orderId;
    }
}
