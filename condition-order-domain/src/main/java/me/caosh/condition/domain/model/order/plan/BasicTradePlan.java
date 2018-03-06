package me.caosh.condition.domain.model.order.plan;

import com.google.common.base.MoreObjects;
import hbec.intellitrade.common.market.RealTimeMarket;
import hbec.intellitrade.common.security.SecurityInfo;
import me.caosh.condition.domain.model.order.TradingMarketSupplier;
import me.caosh.condition.domain.model.order.constant.EntrustStrategy;
import me.caosh.condition.domain.model.order.constant.ExchangeType;
import hbec.intellitrade.strategy.domain.signal.TradeSignal;
import hbec.intellitrade.trade.domain.EntrustCommand;
import me.caosh.condition.domain.model.trade.EntrustPriceSelector;
import hbec.intellitrade.trade.domain.OrderType;

import java.math.BigDecimal;

/**
 * 基本交易计划
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2017/8/23
 */
public class BasicTradePlan implements SingleEntrustTradePlan {
    private final ExchangeType exchangeType;
    private final EntrustStrategy entrustStrategy;
    private final TradeNumber tradeNumber;

    public BasicTradePlan(ExchangeType exchangeType, EntrustStrategy entrustStrategy, TradeNumber tradeNumber) {
        this.exchangeType = exchangeType;
        this.entrustStrategy = entrustStrategy;
        this.tradeNumber = tradeNumber;
    }

    public ExchangeType getExchangeType() {
        return exchangeType;
    }

    public EntrustStrategy getEntrustStrategy() {
        return entrustStrategy;
    }

    @Override
    public TradeNumber getTradeNumber() {
        return tradeNumber;
    }

    @Override
    public void accept(TradePlanVisitor visitor) {
        visitor.visitSingleDirectionTradePlan(this);
    }

    @Override
    public EntrustCommand createEntrustCommand(TradeSignal tradeSignal, SecurityInfo securityInfo,
                                               TradingMarketSupplier tradingMarketSupplier) {
        RealTimeMarket realTimeMarket = tradingMarketSupplier.getTradingMarket();
        BigDecimal entrustPrice = EntrustPriceSelector.selectPrice(realTimeMarket, entrustStrategy);
        return new EntrustCommand(securityInfo, exchangeType, entrustPrice,
                tradeNumber.getNumber(entrustPrice), OrderType.LIMITED);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BasicTradePlan)) {
            return false;
        }

        BasicTradePlan that = (BasicTradePlan) o;

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
        return MoreObjects.toStringHelper(BasicTradePlan.class).omitNullValues()
                .add("exchangeType", exchangeType)
                .add("entrustStrategy", entrustStrategy)
                .add("tradeNumber", tradeNumber)
                .toString();
    }
}
