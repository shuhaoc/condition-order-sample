package me.caosh.condition.domain.service.impl;

import me.caosh.condition.application.order.ConditionOrderCommandService;
import me.caosh.condition.domain.factory.TradeCustomerFactory;
import me.caosh.condition.domain.model.market.RealTimeMarket;
import me.caosh.condition.domain.model.order.ConditionOrder;
import me.caosh.condition.domain.model.order.TradeCustomer;
import me.caosh.condition.domain.model.order.TradeCustomerInfo;
import me.caosh.condition.domain.model.order.TriggerContext;
import me.caosh.condition.domain.model.order.constant.StrategyState;
import me.caosh.condition.domain.model.signal.BS;
import me.caosh.condition.domain.model.signal.CacheSync;
import me.caosh.condition.domain.model.signal.Signal;
import me.caosh.condition.domain.model.signal.TradeSignal;
import me.caosh.condition.domain.model.trade.EntrustCommand;
import me.caosh.condition.domain.model.trade.EntrustOrder;
import me.caosh.condition.domain.service.ConditionTradeService;
import me.caosh.condition.domain.service.NewStockQueryService;
import me.caosh.condition.domain.service.RealTimeMarketService;
import me.caosh.condition.infrastructure.repository.EntrustOrderRepository;
import me.caosh.condition.infrastructure.repository.impl.EntrustOrderIdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 仅作demo，此处实现没有做到下单与本地事务的一致性保证
 * Created by caosh on 2017/8/13.
 */
@Service
public class ConditionTradeServiceImpl implements ConditionTradeService {
    private static final Logger logger = LoggerFactory.getLogger(ConditionTradeServiceImpl.class);

    private final TradeCustomerFactory tradeCustomerFactory;
    private final ConditionOrderCommandService conditionOrderCommandService;
    private final EntrustOrderRepository entrustOrderRepository;
    private final EntrustOrderIdGenerator entrustOrderIdGenerator;
    private final RealTimeMarketService realTimeMarketService;
    private final NewStockQueryService newStockQueryService;

    public ConditionTradeServiceImpl(TradeCustomerFactory tradeCustomerFactory, ConditionOrderCommandService conditionOrderCommandService, EntrustOrderRepository entrustOrderRepository,
                                     EntrustOrderIdGenerator entrustOrderIdGenerator, RealTimeMarketService realTimeMarketService, NewStockQueryService newStockQueryService) {
        this.tradeCustomerFactory = tradeCustomerFactory;
        this.conditionOrderCommandService = conditionOrderCommandService;
        this.entrustOrderRepository = entrustOrderRepository;
        this.entrustOrderIdGenerator = entrustOrderIdGenerator;
        this.realTimeMarketService = realTimeMarketService;
        this.newStockQueryService = newStockQueryService;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void handleTriggerContext(Signal signal, ConditionOrder conditionOrder, RealTimeMarket realTimeMarket) {
        if (conditionOrder.getStrategyState() != StrategyState.ACTIVE) {
            logger.warn("Order illegal state: {}", conditionOrder);
            return;
        }

        TradeCustomer tradeCustomer = tradeCustomerFactory.createTradeCustomer(conditionOrder.getCustomer());
        TriggerContext triggerContext = new TriggerContext(signal, conditionOrder, tradeCustomer,
                realTimeMarketService, realTimeMarket);

        if (signal instanceof BS) {
//                List<NewStock> currentPurchasable = newStockQueryService.getCurrentPurchasable();
//                List<EntrustCommand> entrustCommands = ((NewStockPurchaseOnTrigger) conditionOrder).createEntrustCommand(currentPurchasable);
//                for (EntrustCommand entrustCommand : entrustCommands) {
//                    handleEntrustCommand(triggerContext, entrustCommand);
//                }

//
//            if (conditionOrder instanceof TriggerPhaseListener) {
//                ((TriggerPhaseListener) conditionOrder).afterEntrustCommandsExecuted(triggerContext);
//            }
            conditionOrder.onTradeSignal((TradeSignal) signal, tradeCustomer, triggerContext, realTimeMarket);
            conditionOrderCommandService.update(conditionOrder);
        } else if (signal instanceof CacheSync) {
            // TODO: use visitor pattern
            conditionOrderCommandService.updateDynamicProperties(conditionOrder);
            logger.info("Sync dynamic properties, orderId={}, condition={}", conditionOrder.getOrderId(), conditionOrder.getCondition());
        }
    }

    private void handleEntrustCommand(TriggerContext triggerContext, EntrustCommand entrustCommand) {
        ConditionOrder conditionOrder = triggerContext.getConditionOrder();
        TradeCustomerInfo tradeCustomerInfo = conditionOrder.getCustomer();
//        EntrustResult entrustResult = tradeCustomerInfo.entrust(entrustCommand);
//        logger.info("Entrust result <== {}", entrustResult);
//
//        if (conditionOrder instanceof EntrustResultAware) {
//            ((EntrustResultAware) conditionOrder).afterEntrustReturned(triggerContext, entrustResult);
//        }

        long entrustId = entrustOrderIdGenerator.nextId();
        EntrustOrder entrustOrder = new EntrustOrder(entrustId, conditionOrder.getOrderId(), tradeCustomerInfo, entrustCommand, null);
        entrustOrderRepository.save(entrustOrder);
    }

}
