package me.caosh.condition.interfaces.web;

import com.google.common.base.Optional;
import hbec.intellitrade.condorder.domain.ConditionOrder;
import hbec.intellitrade.condorder.domain.ConditionOrderRepository;
import hbec.intellitrade.condorder.domain.OrderState;
import hbec.intellitrade.condorder.domain.TradeCustomerInfo;
import hbec.intellitrade.condorder.domain.orders.turnpoint.TurnPointOrder;
import me.caosh.condition.application.order.OrderCommandService;
import me.caosh.condition.infrastructure.tunnel.impl.ConditionOrderIdGenerator;
import me.caosh.condition.interfaces.assembler.TurnPointOrderCommandAssembler;
import me.caosh.condition.interfaces.command.TurnPointOrderCreateCommand;
import me.caosh.condition.interfaces.command.TurnPointOrderUpdateCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Created by caosh on 2017/8/9.
 */
@RestController
@RequestMapping("/turnpoint")
@Deprecated
public class TurnPointOrderController {

    private final ConditionOrderIdGenerator idGenerator;
    private final OrderCommandService orderCommandService;
    private final ConditionOrderRepository conditionOrderRepository;

    @Autowired
    public TurnPointOrderController(ConditionOrderIdGenerator idGenerator,
                                    OrderCommandService orderCommandService,
                                    ConditionOrderRepository conditionOrderRepository) {
        this.idGenerator = idGenerator;
        this.orderCommandService = orderCommandService;
        this.conditionOrderRepository = conditionOrderRepository;
    }

    @RequestMapping("/create")
    public Long create(@Valid TurnPointOrderCreateCommand command) {
        Long orderId = idGenerator.nextId();
        TradeCustomerInfo tradeCustomerInfo = new TradeCustomerInfo(303348, "010000061086");
        TurnPointOrder turnPointOrder = TurnPointOrderCommandAssembler.assemble(orderId, tradeCustomerInfo, command);
        orderCommandService.save(turnPointOrder);
        return orderId;
    }
    @RequestMapping("/update")
    public Long update(@Valid TurnPointOrderUpdateCommand command) {
        Long orderId = command.getOrderId();
        Optional<ConditionOrder> conditionOrderOptional = conditionOrderRepository.findOne(orderId);
        if (!conditionOrderOptional.isPresent()) {
            return -1L;
        }
        ConditionOrder conditionOrder = conditionOrderOptional.get();
        if (conditionOrder.getOrderState() != OrderState.ACTIVE && conditionOrder.getOrderState() != OrderState.PAUSED) {
            return -2L;
        }
        TurnPointOrder oldOrder = (TurnPointOrder) conditionOrder;
        TurnPointOrder newOrder = TurnPointOrderCommandAssembler.merge(oldOrder, command);
        orderCommandService.update(newOrder);
        return orderId;
    }
}
