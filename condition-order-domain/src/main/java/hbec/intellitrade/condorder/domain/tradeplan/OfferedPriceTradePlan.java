package hbec.intellitrade.condorder.domain.tradeplan;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import hbec.intellitrade.common.market.RealTimeMarket;
import hbec.intellitrade.common.security.SecurityInfo;
import hbec.intellitrade.condorder.domain.trigger.TradingMarketSupplier;
import hbec.intellitrade.strategy.domain.signal.TradeSignal;
import hbec.intellitrade.trade.domain.EntrustCommand;
import hbec.intellitrade.trade.domain.ExchangeType;
import hbec.intellitrade.trade.domain.OrderType;

import java.math.BigDecimal;

/**
 * 五档择价的交易计划
 * <p>
 * 其订单类别是限价单
 *
 * @author caoshuhao@touker.com
 * @date 2018/3/24
 */
public class OfferedPriceTradePlan extends BaseTradePlan {
    /**
     * 委托策略
     */
    private final EntrustStrategy entrustStrategy;

    /**
     * 构造交易计划，其中订单类别是限价单
     *
     * @param exchangeType    交易类别
     * @param entrustStrategy 委托策略，必须是五档择价11个选项之一
     * @param tradeNumber     交易数量
     */
    public OfferedPriceTradePlan(ExchangeType exchangeType,
                                 EntrustStrategy entrustStrategy,
                                 TradeNumber tradeNumber) {
        super(exchangeType, tradeNumber);
        Preconditions.checkArgument(exchangeType == ExchangeType.BUY || exchangeType == ExchangeType.SELL,
                                    "Only BUY or SELL allowed");
        Preconditions.checkArgument(entrustStrategy.getValue() >= EntrustStrategy.CURRENT_PRICE.getValue()
                                    && entrustStrategy.getValue() <= EntrustStrategy.BUY5.getValue(),
                                    "Only offered prices entrust strategies allowed");
        this.entrustStrategy = entrustStrategy;
    }

    @Override
    public EntrustStrategy getEntrustStrategy() {
        return entrustStrategy;
    }

    @Override
    public OrderType getOrderType() {
        return OrderType.LIMITED;
    }

    @Override
    public EntrustCommand createEntrustCommand(TradeSignal tradeSignal,
                                               SecurityInfo securityInfo,
                                               TradingMarketSupplier tradingMarketSupplier) {
        RealTimeMarket realTimeMarket = tradingMarketSupplier.getTradingMarket();
        BigDecimal entrustPrice = getEntrustStrategy().selectEntrustPrice(realTimeMarket);
        return new EntrustCommand(securityInfo,
                                  getExchangeType(),
                                  entrustPrice,
                                  getTradeNumber().getNumber(entrustPrice),
                                  OrderType.LIMITED);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(OfferedPriceTradePlan.class).omitNullValues()
                          .add("exchangeType", getExchangeType())
                          .add("entrustStrategy", getEntrustStrategy())
                          .add("tradeNumber", getTradeNumber())
                          .toString();
    }
}
