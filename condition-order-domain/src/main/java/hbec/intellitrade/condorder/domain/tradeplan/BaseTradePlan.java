package hbec.intellitrade.condorder.domain.tradeplan;

import com.google.common.base.MoreObjects;
import hbec.intellitrade.common.market.RealTimeMarket;
import hbec.intellitrade.common.security.SecurityInfo;
import hbec.intellitrade.condorder.domain.trigger.TradingMarketSupplier;
import hbec.intellitrade.strategy.domain.signal.TradeSignal;
import hbec.intellitrade.trade.domain.EntrustCommand;
import hbec.intellitrade.trade.domain.ExchangeType;
import hbec.intellitrade.trade.domain.OrderType;

import java.math.BigDecimal;

/**
 * 单向交易计划基类
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2017/8/23
 */
public abstract class BaseTradePlan implements SingleEntrustTradePlan {
    private final ExchangeType exchangeType;
    private final EntrustStrategy entrustStrategy;
    private final TradeNumber tradeNumber;
    private final OrderType orderType;

    public BaseTradePlan(ExchangeType exchangeType,
                         EntrustStrategy entrustStrategy,
                         TradeNumber tradeNumber,
                         OrderType orderType) {
        this.exchangeType = exchangeType;
        this.entrustStrategy = entrustStrategy;
        this.tradeNumber = tradeNumber;
        this.orderType = orderType;
    }

    public ExchangeType getExchangeType() {
        return exchangeType;
    }

    public EntrustStrategy getEntrustStrategy() {
        return entrustStrategy;
    }

    public TradeNumber getTradeNumber() {
        return tradeNumber;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    @Override
    public EntrustCommand createEntrustCommand(TradeSignal tradeSignal, SecurityInfo securityInfo,
                                               TradingMarketSupplier tradingMarketSupplier) {
        RealTimeMarket realTimeMarket = tradingMarketSupplier.getTradingMarket();
        BigDecimal entrustPrice = entrustStrategy.selectEntrustPrice(realTimeMarket);
        return new EntrustCommand(securityInfo, exchangeType, entrustPrice,
                tradeNumber.getNumber(entrustPrice), OrderType.LIMITED);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BaseTradePlan)) {
            return false;
        }

        BaseTradePlan that = (BaseTradePlan) o;

        if (exchangeType != that.exchangeType) {
            return false;
        }
        if (entrustStrategy != that.entrustStrategy) {
            return false;
        }
        return !(tradeNumber != null ? !tradeNumber.equals(that.tradeNumber) : that.tradeNumber != null);

    }

    @Override
    public int hashCode() {
        int result = exchangeType != null ? exchangeType.hashCode() : 0;
        result = 31 * result + (entrustStrategy != null ? entrustStrategy.hashCode() : 0);
        result = 31 * result + (tradeNumber != null ? tradeNumber.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(BaseTradePlan.class).omitNullValues()
                          .add("exchangeType", exchangeType)
                          .add("entrustStrategy", entrustStrategy)
                          .add("tradeNumber", tradeNumber)
                          .toString();
    }
}
