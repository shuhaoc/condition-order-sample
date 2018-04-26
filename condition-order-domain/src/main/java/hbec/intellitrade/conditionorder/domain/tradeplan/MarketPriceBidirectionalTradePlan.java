package hbec.intellitrade.conditionorder.domain.tradeplan;

import com.google.common.base.MoreObjects;
import hbec.intellitrade.trade.domain.ExchangeType;
import hbec.intellitrade.trade.domain.OrderType;

import java.util.Objects;

/**
 * 使用市价委托的双向交易计划
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2018/5/2
 */
public class MarketPriceBidirectionalTradePlan extends BaseBidirectionalTradePlan {
    private final OrderType orderType;

    public MarketPriceBidirectionalTradePlan(TradeNumber tradeNumber, OrderType orderType) {
        super(tradeNumber);
        this.orderType = orderType;
    }

    @Override
    public SingleEntrustTradePlan getBuyPlan() {
        return new MarketPriceTradePlan(ExchangeType.BUY, getTradeNumber(), orderType);
    }

    @Override
    public SingleEntrustTradePlan getSellPlan() {
        return new MarketPriceTradePlan(ExchangeType.SELL, getTradeNumber(), orderType);
    }

    @Override
    public OrderType getOrderType() {
        return orderType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }
        if (!super.equals(o)) { return false; }
        MarketPriceBidirectionalTradePlan that = (MarketPriceBidirectionalTradePlan) o;
        return orderType == that.orderType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), orderType);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(MarketPriceBidirectionalTradePlan.class).omitNullValues()
                .add("orderType", orderType)
                .add("tradeNumber", getTradeNumber())
                .toString();
    }
}
