package hbec.intellitrade.conditionorder.domain.trigger;

import com.google.common.base.MoreObjects;
import hbec.intellitrade.common.market.RealTimeMarket;
import hbec.intellitrade.common.security.newstock.NewStocksSupplier;
import hbec.intellitrade.conditionorder.domain.ConditionOrder;
import hbec.intellitrade.strategy.domain.signal.Signal;
import hbec.intellitrade.strategy.domain.signalpayload.SignalPayload;
import hbec.intellitrade.trade.domain.EntrustOrderInfo;
import hbec.intellitrade.trade.domain.TradeCustomer;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/4/26
 */
public class AutoPurchaseContext implements TriggerTradingContext {
    private final SignalPayload signalPayload;
    private final NewStocksSupplier newStocksSupplier;
    private final TradeCustomer tradeCustomer;

    public AutoPurchaseContext(SignalPayload signalPayload,
            NewStocksSupplier newStocksSupplier,
            TradeCustomer tradeCustomer) {
        this.signalPayload = signalPayload;
        this.newStocksSupplier = newStocksSupplier;
        this.tradeCustomer = tradeCustomer;
    }

    @Override
    public Signal getSignal() {
        return signalPayload.getSignal();
    }

    @Override
    public ConditionOrder getConditionOrder() {
        return (ConditionOrder) signalPayload.getStrategy();
    }

    public NewStocksSupplier getNewStocksSupplier() {
        return newStocksSupplier;
    }

    @Override
    public TradeCustomer getTradeCustomer() {
        return tradeCustomer;
    }

    @Override
    public RealTimeMarket getTriggerMarket() {
        return null;
    }

    @Override
    public void saveEntrustOrder(EntrustOrderInfo entrustOrderInfo) {

    }

    @Override
    public RealTimeMarket getTradingMarket() {
        return null;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(AutoPurchaseContext.class).omitNullValues()
                .add("signalPayload", signalPayload)
                .add("newStocksSupplier", newStocksSupplier)
                .add("tradeCustomer", tradeCustomer)
                .toString();
    }
}
