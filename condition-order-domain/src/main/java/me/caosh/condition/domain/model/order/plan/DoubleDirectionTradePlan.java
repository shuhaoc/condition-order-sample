package me.caosh.condition.domain.model.order.plan;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import hbec.intellitrade.common.security.SecurityInfo;
import me.caosh.condition.domain.model.order.TradingMarketSupplier;
import me.caosh.condition.domain.model.order.constant.EntrustStrategy;
import me.caosh.condition.domain.model.order.constant.ExchangeType;
import hbec.intellitrade.strategy.domain.signal.Buy;
import hbec.intellitrade.strategy.domain.signal.Sell;
import hbec.intellitrade.strategy.domain.signal.TradeSignal;
import hbec.intellitrade.trade.domain.EntrustCommand;

/**
 * Created by caosh on 2017/8/24.
 */
public class DoubleDirectionTradePlan implements SingleEntrustTradePlan {
    private final BasicTradePlan buyPlan;
    private final BasicTradePlan sellPlan;

    public DoubleDirectionTradePlan(BasicTradePlan buyPlan, BasicTradePlan sellPlan) {
        Preconditions.checkArgument(buyPlan.getExchangeType() == ExchangeType.BUY);
        Preconditions.checkArgument(sellPlan.getExchangeType() == ExchangeType.SELL);
        this.buyPlan = buyPlan;
        this.sellPlan = sellPlan;
    }

    public BasicTradePlan getBuyPlan() {
        return buyPlan;
    }

    public BasicTradePlan getSellPlan() {
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
    public void accept(TradePlanVisitor visitor) {
        visitor.visitDoubleDirectionTradePlan(this);
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
