package me.caosh.condition.interfaces.web;

import com.google.common.base.Optional;
import hbec.intellitrade.conditionorder.domain.ConditionOrder;
import hbec.intellitrade.conditionorder.domain.ConditionOrderRepository;
import hbec.intellitrade.conditionorder.domain.TradeCustomerInfo;
import hbec.intellitrade.conditionorder.domain.orders.time.TimeOrder;
import me.caosh.condition.application.order.OrderCommandService;
import me.caosh.condition.infrastructure.tunnel.impl.ConditionOrderIdGenerator;
import me.caosh.condition.interfaces.assembler.TimeOrderCommandAssembler;
import me.caosh.condition.interfaces.command.TimeOrderCreateCommand;
import me.caosh.condition.interfaces.command.TimeOrderUpdateCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Created by caosh on 2017/8/9.
 */
@RestController
@RequestMapping("/time")
public class TimeOrderController {

    private final ConditionOrderIdGenerator idGenerator;
    private final OrderCommandService orderCommandService;
    private final ConditionOrderRepository conditionOrderRepository;

    @Autowired
    public TimeOrderController(ConditionOrderIdGenerator idGenerator,
                               OrderCommandService orderCommandService,
                               ConditionOrderRepository conditionOrderRepository) {
        this.idGenerator = idGenerator;
        this.orderCommandService = orderCommandService;
        this.conditionOrderRepository = conditionOrderRepository;
    }

    @RequestMapping("/create")
    public Long create(@Valid TimeOrderCreateCommand command) {
        Long orderId = idGenerator.nextId();
        TradeCustomerInfo tradeCustomerInfo = new TradeCustomerInfo(303348, "010000061086");
        TimeOrder timeOrder = TimeOrderCommandAssembler.assemble(orderId, tradeCustomerInfo, command);
        orderCommandService.save(timeOrder);
        return orderId;
    }

    @RequestMapping("/update")
    public Long update(@Valid TimeOrderUpdateCommand command) {
        Long orderId = command.getOrderId();
        Optional<ConditionOrder> conditionOrderOptional = conditionOrderRepository.findOne(orderId);
        if (!conditionOrderOptional.isPresent()) {
            return -1L;
        }
        ConditionOrder conditionOrder = conditionOrderOptional.get();
        if (!conditionOrder.isMonitoringState()) {
            return -2L;
        }
        TimeOrder oldOrder = (TimeOrder) conditionOrder;
        TimeOrder newOrder = TimeOrderCommandAssembler.merge(oldOrder, command);
        orderCommandService.update(newOrder);
        return orderId;
    }
}
