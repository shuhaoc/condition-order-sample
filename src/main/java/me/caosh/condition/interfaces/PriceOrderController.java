package me.caosh.condition.interfaces;

import com.google.common.collect.Lists;
import me.caosh.condition.application.order.ConditionOrderCheckService;
import me.caosh.condition.application.order.ConditionOrderCommandService;
import me.caosh.condition.domain.model.market.RealTimeMarket;
import me.caosh.condition.domain.model.market.SecurityExchange;
import me.caosh.condition.domain.model.market.SecurityInfo;
import me.caosh.condition.domain.model.market.SecurityType;
import me.caosh.condition.domain.model.order.*;
import me.caosh.condition.infrastructure.repository.impl.ConditionOrderIdGenerator;
import me.caosh.condition.interfaces.assembler.PriceOrderAssembler;
import me.caosh.condition.interfaces.command.PriceOrderCreateCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * Created by caosh on 2017/8/9.
 */
@RestController
@RequestMapping("/price")
public class PriceOrderController {

    @Autowired
    private ConditionOrderIdGenerator idGenerator;

    @Autowired
    private ConditionOrderCommandService conditionOrderCommandService;

    @Autowired
    private ConditionOrderCheckService conditionOrderCheckService;

    @RequestMapping("/create")
    public Integer create(PriceOrderCreateCommand command) {
        Long orderId = idGenerator.nextId();
        PriceOrder priceOrder = PriceOrderAssembler.assemblePriceOrder(orderId, command);
        conditionOrderCommandService.save(priceOrder);
        return 1;
    }

    @RequestMapping("/check")
    public int check(BigDecimal price) {
        RealTimeMarket realTimeMarket = new RealTimeMarket(
                new SecurityInfo(SecurityType.STOCK, "600000", SecurityExchange.SH, "浦发银行"), price);
        conditionOrderCheckService.checkWithRealTimeMarkets(Lists.newArrayList(realTimeMarket));
        return 1;
    }
}
