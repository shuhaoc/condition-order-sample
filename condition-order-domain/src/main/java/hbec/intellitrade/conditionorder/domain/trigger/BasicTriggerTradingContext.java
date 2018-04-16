package hbec.intellitrade.conditionorder.domain.trigger;

import com.google.common.base.MoreObjects;
import hbec.intellitrade.common.market.MarketID;
import hbec.intellitrade.common.market.RealTimeMarket;
import hbec.intellitrade.common.market.RealTimeMarketSupplier;
import hbec.intellitrade.conditionorder.domain.ConditionOrder;
import hbec.intellitrade.conditionorder.domain.ExplicitTradingSecurityOrder;
import hbec.intellitrade.strategy.domain.signal.Signal;
import hbec.intellitrade.trade.domain.EntrustOrderInfo;
import hbec.intellitrade.trade.domain.EntrustOrderWriter;
import hbec.intellitrade.trade.domain.TradeCustomer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by caosh on 2017/8/14.
 *
 * @author caoshuhao@touker.com
 */
public class BasicTriggerTradingContext implements TriggerTradingContext {
    private static final Logger logger = LoggerFactory.getLogger(BasicTriggerTradingContext.class);

    private final Signal signal;
    private final ExplicitTradingSecurityOrder conditionOrder;
    private final TradeCustomer tradeCustomer;
    private final RealTimeMarketSupplier realTimeMarketSupplier;
    private final EntrustOrderWriter entrustOrderWriter;
    private RealTimeMarket triggerMarket;
    private RealTimeMarket tradingMarket;

    public BasicTriggerTradingContext(Signal signal,
                                      ExplicitTradingSecurityOrder conditionOrder,
                                      TradeCustomer tradeCustomer,
                                      RealTimeMarketSupplier realTimeMarketSupplier, EntrustOrderWriter entrustOrderWriter,
                                      RealTimeMarket triggerMarket) {
        this.signal = signal;
        this.conditionOrder = conditionOrder;
        this.tradeCustomer = tradeCustomer;
        this.realTimeMarketSupplier = realTimeMarketSupplier;
        this.entrustOrderWriter = entrustOrderWriter;
        this.triggerMarket = triggerMarket;

        tryInitTradingMarket(conditionOrder, triggerMarket);
    }

    private void tryInitTradingMarket(ExplicitTradingSecurityOrder conditionOrder, RealTimeMarket triggerMarket) {
        if (triggerMarket == null) {
            return;
        }
        if (triggerMarket.getMarketID().equals(conditionOrder.getSecurityInfo().getMarketID())) {
            this.tradingMarket = triggerMarket;
        }
    }

    @Override
    public Signal getSignal() {
        return signal;
    }

    @Override
    public ConditionOrder getConditionOrder() {
        return conditionOrder;
    }

    @Override
    public TradeCustomer getTradeCustomer() {
        return tradeCustomer;
    }

    @Override
    public RealTimeMarket getTriggerMarket() {
        return triggerMarket;
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

        tradingMarket = realTimeMarketSupplier.getCurrentMarket(tradingMarketID);
        logger.info("Get trading real-time market <== {}", tradingMarket);
        return tradingMarket;
    }

    @Override
    public void saveEntrustOrder(EntrustOrderInfo entrustOrderInfo) {
        entrustOrderWriter.save(entrustOrderInfo);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(BasicTriggerTradingContext.class).omitNullValues()
                          .add("signal", signal)
                          .add("conditionOrder", conditionOrder)
                          .add("tradeCustomer", tradeCustomer)
                          .add("triggerMarket", triggerMarket)
                          .add("tradingMarket", tradingMarket)
                          .toString();
    }
}
