package me.caosh.condition.application.order.impl;

import com.google.common.base.Preconditions;
import me.caosh.condition.application.order.ConditionOrderCommandService;
import me.caosh.condition.application.order.ConditionOrderTradeCenter;
import me.caosh.condition.domain.model.market.RealTimeMarket;
import me.caosh.condition.domain.model.order.ConditionOrder;
import me.caosh.condition.domain.model.order.TriggerContext;
import me.caosh.condition.domain.model.order.constant.OrderState;
import me.caosh.condition.domain.model.signal.CacheSync;
import me.caosh.condition.domain.model.signal.General;
import me.caosh.condition.domain.model.signal.TradeSignal;
import me.caosh.condition.domain.model.strategy.LifeCircle;
import me.caosh.condition.domain.model.trade.EntrustCommand;
import me.caosh.condition.domain.model.trade.EntrustOrder;
import me.caosh.condition.domain.model.trade.EntrustResult;
import me.caosh.condition.domain.model.trade.EntrustResultAware;
import me.caosh.condition.domain.service.RealTimeMarketService;
import me.caosh.condition.domain.service.SecurityEntrustService;
import me.caosh.condition.infrastructure.repository.EntrustOrderRepository;
import me.caosh.condition.infrastructure.repository.impl.EntrustOrderIdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by caosh on 2017/8/13.
 */
@Service
public class ConditionOrderTradeCenterImpl implements ConditionOrderTradeCenter {
    private static final Logger logger = LoggerFactory.getLogger(ConditionOrderTradeCenterImpl.class);

    private final SecurityEntrustService securityEntrustService;
    private final ConditionOrderCommandService conditionOrderCommandService;
    private final EntrustOrderRepository entrustOrderRepository;
    private final EntrustOrderIdGenerator entrustOrderIdGenerator;
    private final RealTimeMarketService realTimeMarketService;

    public ConditionOrderTradeCenterImpl(SecurityEntrustService securityEntrustService,
                                         ConditionOrderCommandService conditionOrderCommandService,
                                         EntrustOrderRepository entrustOrderRepository,
                                         EntrustOrderIdGenerator entrustOrderIdGenerator,
                                         RealTimeMarketService realTimeMarketService) {
        this.securityEntrustService = securityEntrustService;
        this.conditionOrderCommandService = conditionOrderCommandService;
        this.entrustOrderRepository = entrustOrderRepository;
        this.entrustOrderIdGenerator = entrustOrderIdGenerator;
        this.realTimeMarketService = realTimeMarketService;
    }

    @Transactional
    @Override
    public void handleTriggerContext(TriggerContext triggerContext) {
        // TODO: exception handler strategy: retry times? exception type?
        TradeSignal signal = triggerContext.getTradeSignal();
        ConditionOrder conditionOrder = triggerContext.getConditionOrder();
        if (signal instanceof General) {
            // TODO: use responsibility chain pattern
            RealTimeMarket realTimeMarket = triggerContext.getTriggerRealTimeMarket();
            if (realTimeMarket == null) {
                realTimeMarket = realTimeMarketService.getCurrentMarket(conditionOrder.getSecurityInfo().asMarketID());
                logger.info("Get real-time market <== {}", realTimeMarket);
                Preconditions.checkNotNull(realTimeMarket);
            }
            EntrustCommand entrustCommand = conditionOrder.onTradeSignal(signal, realTimeMarket);
            EntrustResult entrustResult = securityEntrustService.execute(entrustCommand);
            logger.info("Entrust result <== {}", entrustResult);

            if (conditionOrder instanceof EntrustResultAware) {
                ((EntrustResultAware) conditionOrder).afterEntrustReturned(triggerContext, entrustResult);
            }

            EntrustOrder entrustOrder = new EntrustOrder(entrustOrderIdGenerator.nextId(), conditionOrder.getOrderId(), entrustCommand, entrustResult);
            entrustOrderRepository.save(entrustOrder);

            if (conditionOrder.getStrategyInfo().getLifeCircle() == LifeCircle.ONCE) {
                conditionOrder.setOrderState(OrderState.TERMINATED);
            }
            conditionOrderCommandService.update(conditionOrder);
        } else if (signal instanceof CacheSync) {
            // TODO: use visitor pattern
            conditionOrderCommandService.updateDynamicProperties(conditionOrder);
            logger.info("Sync dynamic properties, orderId={}, condition={}", conditionOrder.getOrderId(), conditionOrder.getCondition());
        }
    }
}
