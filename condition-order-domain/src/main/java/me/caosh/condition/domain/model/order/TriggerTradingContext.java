package me.caosh.condition.domain.model.order;

import com.google.common.base.MoreObjects;
import me.caosh.condition.domain.model.market.MarketID;
import me.caosh.condition.domain.model.market.RealTimeMarket;
import me.caosh.condition.domain.model.signal.Signal;
import me.caosh.condition.domain.service.RealTimeMarketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by caosh on 2017/8/14.
 *
 * @author caoshuhao@touker.com
 */
public class TriggerTradingContext implements TradingMarketSupplier {
    private static final Logger logger = LoggerFactory.getLogger(TriggerTradingContext.class);

    private final Signal signal;
    private final ConditionOrder conditionOrder;
    private final TradeCustomer tradeCustomer;
    private final RealTimeMarketService realTimeMarketService;
    private RealTimeMarket triggerMarket;
    private RealTimeMarket tradingMarket;

    public TriggerTradingContext(Signal signal, ConditionOrder conditionOrder, TradeCustomer tradeCustomer,
                                 RealTimeMarketService realTimeMarketService, RealTimeMarket triggerMarket) {
        this.signal = signal;
        this.conditionOrder = conditionOrder;
        this.tradeCustomer = tradeCustomer;
        this.realTimeMarketService = realTimeMarketService;
        this.triggerMarket = triggerMarket;

        tryInitTradingMarket(conditionOrder, triggerMarket);
    }

    private void tryInitTradingMarket(ConditionOrder conditionOrder, RealTimeMarket triggerMarket) {
        if (triggerMarket == null) {
            return;
        }
        if (triggerMarket.getMarketID().equals(conditionOrder.getSecurityInfo().getMarketID())) {
            this.tradingMarket = triggerMarket;
        }
    }

    public Signal getSignal() {
        return signal;
    }

    public ConditionOrder getConditionOrder() {
        return conditionOrder;
    }

    public TradeCustomer getTradeCustomer() {
        return tradeCustomer;
    }

    @Override
    public RealTimeMarket getTradingMarket() {
        if (tradingMarket != null) {
            return tradingMarket;
        }

        MarketID tradingMarketID = conditionOrder.getSecurityInfo().getMarketID();
        if (triggerMarket.getMarketID().equals(tradingMarketID)) {
            return triggerMarket;
        }

        tradingMarket = realTimeMarketService.getCurrentMarket(tradingMarketID);
        logger.info("Get trading real-time market <== {}", tradingMarket);
        return tradingMarket;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(TriggerTradingContext.class).omitNullValues()
                .add("signal", signal)
                .add("conditionOrder", conditionOrder)
                .add("tradeCustomer", tradeCustomer)
                .add("realTimeMarketService", realTimeMarketService)
                .add("triggerMarket", triggerMarket)
                .add("tradingMarket", tradingMarket)
                .toString();
    }
}
