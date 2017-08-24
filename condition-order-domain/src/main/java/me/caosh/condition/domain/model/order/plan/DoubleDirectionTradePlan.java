package me.caosh.condition.domain.model.order.plan;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import me.caosh.condition.domain.model.order.constant.ExchangeType;

/**
 * Created by caosh on 2017/8/24.
 */
public class DoubleDirectionTradePlan implements TradePlan {
    private final SingleDirectionTradePlan buyPlan;
    private final SingleDirectionTradePlan sellPlan;

    DoubleDirectionTradePlan(SingleDirectionTradePlan buyPlan, SingleDirectionTradePlan sellPlan) {
        Preconditions.checkArgument(buyPlan.getExchangeType() == ExchangeType.BUY);
        Preconditions.checkArgument(sellPlan.getExchangeType() == ExchangeType.SELL);
        this.buyPlan = buyPlan;
        this.sellPlan = sellPlan;
    }

    public SingleDirectionTradePlan getBuyPlan() {
        return buyPlan;
    }

    public SingleDirectionTradePlan getSellPlan() {
        return sellPlan;
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
