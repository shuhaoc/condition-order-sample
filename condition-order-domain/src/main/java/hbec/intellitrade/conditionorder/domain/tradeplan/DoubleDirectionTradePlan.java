package hbec.intellitrade.conditionorder.domain.tradeplan;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import hbec.intellitrade.common.security.SecurityInfo;
import hbec.intellitrade.conditionorder.domain.trigger.TradingMarketSupplier;
import hbec.intellitrade.strategy.domain.signal.Buy;
import hbec.intellitrade.strategy.domain.signal.Sell;
import hbec.intellitrade.strategy.domain.signal.TradeSignal;
import hbec.intellitrade.trade.domain.EntrustCommand;
import hbec.intellitrade.trade.domain.ExchangeType;
import hbec.intellitrade.trade.domain.OrderType;

/**
 * 双向交易计划
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2017/8/24
 */
public class DoubleDirectionTradePlan implements SingleEntrustTradePlan {
    static final int DOUBLE_EXCHANGE_TYPE = 0;

    private final BaseTradePlan buyPlan;
    private final BaseTradePlan sellPlan;

    public DoubleDirectionTradePlan(BaseTradePlan buyPlan, BaseTradePlan sellPlan) {
        Preconditions.checkArgument(buyPlan.getExchangeType() == ExchangeType.BUY, "Buy plan should buy");
        Preconditions.checkArgument(sellPlan.getExchangeType() == ExchangeType.SELL, "Sell plan should sell");
        this.buyPlan = buyPlan;
        this.sellPlan = sellPlan;
    }

    public BaseTradePlan getBuyPlan() {
        return buyPlan;
    }

    public BaseTradePlan getSellPlan() {
        return sellPlan;
    }

    public EntrustStrategy getEntrustStrategy() {
        return getBuyPlan().getEntrustStrategy();
    }

    @Override
    public TradeNumber getTradeNumber() {
        return buyPlan.getTradeNumber();
    }

    @Override
    public OrderType getOrderType() {
        return buyPlan.getOrderType();
    }

    @Override
    public EntrustCommand createEntrustCommand(TradeSignal tradeSignal, SecurityInfo securityInfo, TradingMarketSupplier tradingMarketSupplier) {
        if (tradeSignal instanceof Buy) {
            return buyPlan.createEntrustCommand(tradeSignal, securityInfo, tradingMarketSupplier);
        } else if (tradeSignal instanceof Sell) {
            return sellPlan.createEntrustCommand(tradeSignal, securityInfo, tradingMarketSupplier);
        }
        throw new IllegalArgumentException(String.valueOf(tradeSignal));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DoubleDirectionTradePlan)) return false;

        DoubleDirectionTradePlan that = (DoubleDirectionTradePlan) o;

        if (buyPlan != null ? !buyPlan.equals(that.buyPlan) : that.buyPlan != null) return false;
        return !(sellPlan != null ? !sellPlan.equals(that.sellPlan) : that.sellPlan != null);

    }

    @Override
    public int hashCode() {
        int result = buyPlan != null ? buyPlan.hashCode() : 0;
        result = 31 * result + (sellPlan != null ? sellPlan.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("buyPlan", buyPlan)
                .add("sellPlan", sellPlan)
                .toString();
    }
}
