package me.caosh.condition.application.order.impl;

import hbec.intellitrade.common.market.RealTimeMarket;
import hbec.intellitrade.common.market.RealTimeMarketSupplier;
import hbec.intellitrade.condorder.domain.ConditionOrder;
import hbec.intellitrade.condorder.domain.OrderState;
import hbec.intellitrade.condorder.domain.trigger.BasicTriggerTradingContext;
import hbec.intellitrade.condorder.domain.trigger.TriggerTradingContext;
import hbec.intellitrade.strategy.domain.signal.BS;
import hbec.intellitrade.strategy.domain.signal.CacheSync;
import hbec.intellitrade.strategy.domain.signal.Signal;
import hbec.intellitrade.strategy.domain.signalpayload.MarketSignalPayload;
import hbec.intellitrade.strategy.domain.signalpayload.SignalPayload;
import hbec.intellitrade.trade.domain.EntrustOrder;
import hbec.intellitrade.trade.domain.EntrustOrderInfo;
import hbec.intellitrade.trade.domain.EntrustOrderWriter;
import hbec.intellitrade.trade.domain.TradeCustomer;
import me.caosh.condition.application.order.OrderCommandService;
import me.caosh.condition.application.order.SignalHandlerService;
import me.caosh.condition.application.trade.factory.TradeCustomerFactory;
import me.caosh.condition.domain.service.NewStocksSupplier;
import hbec.intellitrade.condorder.domain.EntrustOrderRepository;
import me.caosh.condition.infrastructure.tunnel.ConditionOrderDbTunnel;
import me.caosh.condition.infrastructure.tunnel.impl.EntrustOrderIdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 仅作demo，此处实现没有做到下单与本地事务的一致性保证
 * Created by caosh on 2017/8/13.
 */
@Service
public class SignalHandlerServiceImpl implements SignalHandlerService {
    private static final Logger logger = LoggerFactory.getLogger(SignalHandlerServiceImpl.class);

    private final TradeCustomerFactory tradeCustomerFactory;
    private final OrderCommandService orderCommandService;
    private final EntrustOrderRepository entrustOrderRepository;
    private final EntrustOrderIdGenerator entrustOrderIdGenerator;
    private final RealTimeMarketSupplier realTimeMarketSupplier;
    private final NewStocksSupplier newStocksSupplier;
    private final ConditionOrderDbTunnel conditionOrderDbTunnel;

    public SignalHandlerServiceImpl(TradeCustomerFactory tradeCustomerFactory, OrderCommandService orderCommandService, EntrustOrderRepository entrustOrderRepository, EntrustOrderIdGenerator entrustOrderIdGenerator, RealTimeMarketSupplier realTimeMarketSupplier, NewStocksSupplier newStocksSupplier, ConditionOrderDbTunnel conditionOrderDbTunnel) {
        this.tradeCustomerFactory = tradeCustomerFactory;
        this.orderCommandService = orderCommandService;
        this.entrustOrderRepository = entrustOrderRepository;
        this.entrustOrderIdGenerator = entrustOrderIdGenerator;
        this.realTimeMarketSupplier = realTimeMarketSupplier;
        this.newStocksSupplier = newStocksSupplier;
        this.conditionOrderDbTunnel = conditionOrderDbTunnel;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void handleSignalPaylaod(SignalPayload signalPayload, Signal signal2, ConditionOrder conditionOrder2, RealTimeMarket realTimeMarket2) {
        Signal signal = signalPayload.getSignal();
        ConditionOrder conditionOrder = (ConditionOrder) signalPayload.getStrategy();

        if (conditionOrder.getOrderState() != OrderState.ACTIVE) {
            logger.warn("Order illegal state: {}", conditionOrder);
            return;
        }

        TriggerTradingContext triggerTradingContext = createTriggerTradingContext(signalPayload, signal, conditionOrder);

        if (signal instanceof BS) {
            conditionOrder.onTradeSignal(triggerTradingContext);
            orderCommandService.update(conditionOrder);
        } else if (signal instanceof CacheSync) {
            conditionOrderDbTunnel.save(conditionOrder);
            logger.info("Sync dynamic properties, conditionOrder={}", conditionOrder);
        }
    }

    private TriggerTradingContext createTriggerTradingContext(SignalPayload signalPayload, Signal signal, ConditionOrder conditionOrder) {
        RealTimeMarket realTimeMarket = null;
        if (signalPayload instanceof MarketSignalPayload) {
            realTimeMarket = ((MarketSignalPayload) signalPayload).getRealTimeMarket();
        }

        TradeCustomer tradeCustomer = tradeCustomerFactory.createTradeCustomer(conditionOrder.getCustomer());
        return new BasicTriggerTradingContext(signal, conditionOrder, tradeCustomer,
                realTimeMarketSupplier, new EntrustOrderWriterImpl(), realTimeMarket);
    }

    private class EntrustOrderWriterImpl implements EntrustOrderWriter {
        @Override
        public void save(EntrustOrderInfo entrustOrderInfo) {
            long entrustId = entrustOrderIdGenerator.nextId();
            EntrustOrder entrustOrder = new EntrustOrder(entrustId, entrustOrderInfo.getOrderId(), entrustOrderInfo.getTradeCustomerInfo(),
                    entrustOrderInfo.getEntrustCommand(), entrustOrderInfo.getEntrustResult());
            entrustOrderRepository.save(entrustOrder);
        }
    }
}
