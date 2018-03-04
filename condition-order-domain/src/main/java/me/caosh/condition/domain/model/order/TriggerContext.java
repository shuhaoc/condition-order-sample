package me.caosh.condition.domain.model.order;

import com.google.common.base.MoreObjects;
import com.google.common.base.Optional;
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
public class TriggerContext implements TradingMarketSupplier {
    private static final Logger logger = LoggerFactory.getLogger(TriggerContext.class);

    private final Signal signal;
    private final ConditionOrder conditionOrder;
    private final TradeCustomer tradeCustomer;
    private final RealTimeMarketService realTimeMarketService;
    private RealTimeMarket triggerMarket;
    private RealTimeMarket tradingMarket;

    public TriggerContext(Signal signal, ConditionOrder conditionOrder, TradeCustomer tradeCustomer,
                          RealTimeMarketService realTimeMarketService, RealTimeMarket triggerMarket) {
        this.signal = signal;
        this.conditionOrder = conditionOrder;
        this.tradeCustomer = tradeCustomer;
        this.realTimeMarketService = realTimeMarketService;
        this.triggerMarket = triggerMarket;
    }

    public Signal getSignal() {
        return signal;
    }

    public ConditionOrder getConditionOrder() {
        return conditionOrder;
    }

    public Optional<RealTimeMarket> getTriggerMarket() {
        return Optional.fromNullable(triggerMarket);
    }

    public void setTriggerMarket(RealTimeMarket triggerMarket) {
        this.triggerMarket = triggerMarket;
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
        return MoreObjects.toStringHelper(this)
                .add("signal", signal)
                .add("conditionOrder", conditionOrder)
                .add("triggerMarket", triggerMarket)
                .toString();
    }
}
