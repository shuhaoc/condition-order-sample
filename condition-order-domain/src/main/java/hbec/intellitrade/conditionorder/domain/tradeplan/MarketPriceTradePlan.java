package hbec.intellitrade.conditionorder.domain.tradeplan;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import hbec.intellitrade.common.market.RealTimeMarket;
import hbec.intellitrade.common.security.SecurityInfo;
import hbec.intellitrade.conditionorder.domain.trigger.TradingMarketSupplier;
import hbec.intellitrade.strategy.domain.signal.TradeSignal;
import hbec.intellitrade.trade.domain.EntrustCommand;
import hbec.intellitrade.trade.domain.ExchangeType;
import hbec.intellitrade.trade.domain.OrderType;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * 市价委托交易计划
 * <p>
 * 其委托策略为{@link EntrustStrategy#MARKET_PRICE}
 *
 * @author caoshuhao@touker.com
 * @date 2018/3/24
 */
public class MarketPriceTradePlan extends BaseTradePlan {
    /**
     * 订单类别
     */
    private final OrderType orderType;

    public MarketPriceTradePlan(ExchangeType exchangeType,
                                TradeNumber tradeNumber,
                                OrderType orderType) {
        super(exchangeType, tradeNumber);
        Preconditions.checkArgument(orderType != OrderType.LIMITED, "Order type cannot be LIMITED");
        this.orderType = orderType;
    }

    @Override
    public EntrustStrategy getEntrustStrategy() {
        return EntrustStrategy.MARKET_PRICE;
    }

    @Override
    public OrderType getOrderType() {
        return orderType;
    }

    @Override
    public EntrustCommand createEntrustCommand(TradeSignal tradeSignal,
                                               SecurityInfo securityInfo,
                                               TradingMarketSupplier tradingMarketSupplier) {
        TradeNumber tradeNumber = getTradeNumber();
        if (tradeNumber.getEntrustMethod() == EntrustMethod.NUMBER) {
            return new EntrustCommand(securityInfo,
                                      getExchangeType(),
                                      null,
                                      ((TradeNumberDirect) tradeNumber).getNumber(),
                                      orderType);
        } else if (tradeNumber.getEntrustMethod() == EntrustMethod.AMOUNT) {
            // 市价委托、金额下单时，使用当前价估算委托数量
            RealTimeMarket realTimeMarket = tradingMarketSupplier.getTradingMarket();
            BigDecimal currentPrice = realTimeMarket.getCurrentPrice();
            return new EntrustCommand(securityInfo,
                                      getExchangeType(),
                                      null,
                                      tradeNumber.getNumber(securityInfo, currentPrice),
                                      orderType);
        } else {
            throw new IllegalArgumentException("tradeNumber=" + tradeNumber);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        MarketPriceTradePlan that = (MarketPriceTradePlan) o;
        return orderType == that.orderType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), orderType);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(MarketPriceTradePlan.class).omitNullValues()
                          .add("exchangeType", getExchangeType())
                          .add("tradeNumber", getTradeNumber())
                          .add("orderType", orderType)
                          .toString();
    }
}
