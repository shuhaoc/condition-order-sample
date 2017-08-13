package me.caosh.condition.application.order.impl;

import com.google.common.base.Preconditions;
import me.caosh.condition.application.order.ConditionOrderCommandService;
import me.caosh.condition.application.order.ConditionOrderTradeCenter;
import me.caosh.condition.domain.model.market.RealTimeMarket;
import me.caosh.condition.domain.model.order.ConditionOrder;
import me.caosh.condition.domain.model.order.OrderState;
import me.caosh.condition.domain.model.order.TriggerOnce;
import me.caosh.condition.domain.model.signal.General;
import me.caosh.condition.domain.model.signal.TradeSignal;
import me.caosh.condition.domain.model.trade.EntrustCommand;
import me.caosh.condition.domain.service.SecurityEntrustService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by caosh on 2017/8/13.
 */
@Service
public class ConditionOrderTradeCenterImpl implements ConditionOrderTradeCenter {
    private static final Logger logger = LoggerFactory.getLogger(ConditionOrderTradeCenterImpl.class);

    private final SecurityEntrustService securityEntrustService;
    private final ConditionOrderCommandService conditionOrderCommandService;

    public ConditionOrderTradeCenterImpl(SecurityEntrustService securityEntrustService, ConditionOrderCommandService conditionOrderCommandService) {
        this.securityEntrustService = securityEntrustService;
        this.conditionOrderCommandService = conditionOrderCommandService;
    }

    @Override
    public void handleTriggerMessage(TradeSignal signal, ConditionOrder conditionOrder, RealTimeMarket realTimeMarket) {
        if (signal instanceof General) {
            Preconditions.checkNotNull(realTimeMarket);
            EntrustCommand entrustCommand = conditionOrder.onTradeSignal(signal, realTimeMarket);
            securityEntrustService.execute(entrustCommand);
            if (conditionOrder instanceof TriggerOnce) {
                conditionOrder.setOrderState(OrderState.TERMINATED);
            }
            conditionOrderCommandService.update(conditionOrder);
        }
    }
}
