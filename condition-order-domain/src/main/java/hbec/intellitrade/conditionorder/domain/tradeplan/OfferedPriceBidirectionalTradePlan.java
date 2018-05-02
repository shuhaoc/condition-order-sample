package hbec.intellitrade.conditionorder.domain.tradeplan;

import com.google.common.base.MoreObjects;
import hbec.intellitrade.trade.domain.ExchangeType;
import hbec.intellitrade.trade.domain.OrderType;

import java.util.Objects;

/**
 * 使用五档择价的双向交易计划
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2017/8/24
 */
public class OfferedPriceBidirectionalTradePlan extends BaseBidirectionalTradePlan {
    /**
     * 买入委托策略
     */
    private final EntrustStrategy buyStrategy;
    /**
     * 卖出委托策略
     */
    private final EntrustStrategy sellStrategy;

    public OfferedPriceBidirectionalTradePlan(TradeNumber tradeNumber,
            EntrustStrategy buyStrategy,
            EntrustStrategy sellStrategy) {
        super(tradeNumber);
        this.buyStrategy = buyStrategy;
        this.sellStrategy = sellStrategy;
    }

    public EntrustStrategy getBuyStrategy() {
        return buyStrategy;
    }

    public EntrustStrategy getSellStrategy() {
        return sellStrategy;
    }

    @Override
    public OrderType getOrderType() {
        return OrderType.LIMITED;
    }

    @Override
    public OfferedPriceTradePlan getSellPlan() {
        return new OfferedPriceTradePlan(ExchangeType.SELL, sellStrategy, getTradeNumber());
    }

    @Override
    public OfferedPriceTradePlan getBuyPlan() {
        return new OfferedPriceTradePlan(ExchangeType.BUY, buyStrategy, getTradeNumber());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }
        if (!super.equals(o)) { return false; }
        OfferedPriceBidirectionalTradePlan that = (OfferedPriceBidirectionalTradePlan) o;
        return buyStrategy == that.buyStrategy &&
                sellStrategy == that.sellStrategy;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), buyStrategy, sellStrategy);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(OfferedPriceBidirectionalTradePlan.class).omitNullValues()
                .add("buyStrategy", buyStrategy)
                .add("sellStrategy", sellStrategy)
                .add("tradeNumber", getTradeNumber())
                .toString();
    }
}
