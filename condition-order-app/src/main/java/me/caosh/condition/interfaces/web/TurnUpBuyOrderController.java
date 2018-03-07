package me.caosh.condition.interfaces.web;

import com.google.common.base.Optional;
import hbec.intellitrade.condorder.domain.ConditionOrder;
import hbec.intellitrade.condorder.domain.OrderState;
import hbec.intellitrade.condorder.domain.TradeCustomerInfo;
import me.caosh.condition.application.order.ConditionOrderCommandService;
import me.caosh.condition.domain.model.order.turnpoint.TurnUpBuyOrder;
import me.caosh.condition.infrastructure.repository.ConditionOrderRepository;
import me.caosh.condition.infrastructure.repository.impl.ConditionOrderIdGenerator;
import me.caosh.condition.interfaces.assembler.TurnUpBuyOrderCommandAssembler;
import me.caosh.condition.interfaces.command.TurnUpBuyOrderCreateCommand;
import me.caosh.condition.interfaces.command.TurnUpBuyOrderUpdateCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Created by caosh on 2017/8/9.
 */
@RestController
@RequestMapping("/turnup")
public class TurnUpBuyOrderController {

    private final ConditionOrderIdGenerator idGenerator;
    private final ConditionOrderCommandService conditionOrderCommandService;
    private final ConditionOrderRepository conditionOrderRepository;

    @Autowired
    public TurnUpBuyOrderController(ConditionOrderIdGenerator idGenerator,
                                    ConditionOrderCommandService conditionOrderCommandService,
                                    ConditionOrderRepository conditionOrderRepository) {
        this.idGenerator = idGenerator;
        this.conditionOrderCommandService = conditionOrderCommandService;
        this.conditionOrderRepository = conditionOrderRepository;
    }

    @RequestMapping("/create")
    public Long create(@Valid TurnUpBuyOrderCreateCommand command) {
        Long orderId = idGenerator.nextId();
        TradeCustomerInfo tradeCustomerInfo = new TradeCustomerInfo(303348, "010000061086");
        TurnUpBuyOrder turnUpBuyOrder = TurnUpBuyOrderCommandAssembler.assemble(orderId, tradeCustomerInfo, command);
        conditionOrderCommandService.save(turnUpBuyOrder);
        return orderId;
    }
    @RequestMapping("/update")
    public Long update(@Valid TurnUpBuyOrderUpdateCommand command) {
        Long orderId = command.getOrderId();
        Optional<ConditionOrder> conditionOrderOptional = conditionOrderRepository.findOne(orderId);
        if (!conditionOrderOptional.isPresent()) {
            return -1L;
        }
        ConditionOrder conditionOrder = conditionOrderOptional.get();
        if (conditionOrder.getOrderState() != OrderState.ACTIVE && conditionOrder.getOrderState() != OrderState.PAUSED) {
            return -2L;
        }
        TurnUpBuyOrder oldOrder = (TurnUpBuyOrder) conditionOrder;
        TurnUpBuyOrder newOrder = TurnUpBuyOrderCommandAssembler.merge(oldOrder, command);
        conditionOrderCommandService.update(newOrder);
        return orderId;
    }
}
