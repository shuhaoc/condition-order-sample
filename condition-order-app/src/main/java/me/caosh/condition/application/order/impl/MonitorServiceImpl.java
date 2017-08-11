package me.caosh.condition.application.order.impl;

import com.google.common.base.Optional;
import com.google.common.collect.Iterables;
import me.caosh.condition.application.order.MonitorService;
import me.caosh.condition.domain.model.trade.EntrustCommand;
import me.caosh.condition.domain.model.market.RealTimeMarket;
import me.caosh.condition.domain.model.order.ConditionOrder;
import me.caosh.condition.domain.model.order.RealTimeMarketDriven;
import me.caosh.condition.domain.model.order.TradeSignalDriven;
import me.caosh.condition.domain.model.signal.SignalFactory;
import me.caosh.condition.domain.model.signal.TradeSignal;
import me.caosh.condition.infrastructure.repository.ConditionOrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by caosh on 2017/8/9.
 */
@Service
public class MonitorServiceImpl implements MonitorService {
    private static final Logger logger = LoggerFactory.getLogger(MonitorServiceImpl.class);

    @Autowired
    private ConditionOrderRepository conditionOrderRepository;

    @Override
    public void run(List<RealTimeMarket> realTimeMarketList) {
        List<ConditionOrder> conditionOrders = conditionOrderRepository.findAllActive();
        conditionOrders.forEach(conditionOrder -> {
            if (conditionOrder instanceof RealTimeMarketDriven) {
                Optional<RealTimeMarket> matchedMarket = Iterables.tryFind(realTimeMarketList, realTimeMarket ->
                        conditionOrder.getSecurityInfo().equals(realTimeMarket.getSecurityInfo()));
                if (matchedMarket.isPresent()) {
                    RealTimeMarketDriven realTimeMarketDriven = (RealTimeMarketDriven) conditionOrder;
                    TradeSignal tradeSignal = realTimeMarketDriven.onRealTimeMarketUpdate(matchedMarket.get());
                    if (tradeSignal != SignalFactory.getInstance().none()) {
                        TradeSignalDriven tradeSignalDriven = (TradeSignalDriven) conditionOrder;
                        EntrustCommand entrustCommand = tradeSignalDriven.onTradeSignal(tradeSignal, matchedMarket.get());
                        logger.info("Sending entrust command => {}", entrustCommand);
                    }
                }
            }
        });
    }
}
