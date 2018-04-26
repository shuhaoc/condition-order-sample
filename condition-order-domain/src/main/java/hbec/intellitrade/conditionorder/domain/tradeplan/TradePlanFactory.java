package hbec.intellitrade.conditionorder.domain.tradeplan;

import hbec.intellitrade.trade.domain.ExchangeType;
import hbec.intellitrade.trade.domain.OrderType;

import java.math.BigDecimal;

/**
 * {@link TradePlan}工厂
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2017/8/24
 */
public class TradePlanFactory {
    private static final TradePlanFactory INSTANCE = new TradePlanFactory();

    public static TradePlanFactory getInstance() {
        return INSTANCE;
    }

    public BaseTradePlan create(ExchangeType exchangeType,
            EntrustStrategy entrustStrategy,
            BigDecimal entrustPrice,
            TradeNumber tradeNumber,
            OrderType orderType) {
        if (entrustStrategy == EntrustStrategy.CUSTOMIZED_PRICE) {
            return new CustomizedPriceTradePlan(exchangeType, entrustPrice, tradeNumber);
        } else if (entrustStrategy == EntrustStrategy.MARKET_PRICE) {
            return new MarketPriceTradePlan(exchangeType, tradeNumber, orderType);
        }
        return new OfferedPriceTradePlan(exchangeType, entrustStrategy, tradeNumber);
    }

    public BaseBidirectionalTradePlan createBidirectional(TradeNumber tradeNumber,
            EntrustStrategy buyStrategy,
            EntrustStrategy sellStrategy,
            OrderType orderType) {
        if (orderType == OrderType.LIMITED) {
            return new OfferedPriceBidirectionalTradePlan(tradeNumber, buyStrategy, sellStrategy);
        } else {
            return new MarketPriceBidirectionalTradePlan(tradeNumber, orderType);
        }
    }

    private TradePlanFactory() {
    }
}
