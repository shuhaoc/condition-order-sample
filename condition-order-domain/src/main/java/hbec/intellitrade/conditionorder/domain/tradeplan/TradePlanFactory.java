package hbec.intellitrade.conditionorder.domain.tradeplan;

import hbec.intellitrade.trade.domain.ExchangeType;
import hbec.intellitrade.trade.domain.OrderType;
import me.caosh.autoasm.AutoAssemblers;

import java.math.BigDecimal;

/**
 * {@link TradePlan}工厂
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2017/8/24
 */
public class TradePlanFactory {
    private static final TradePlanFactory INSTANCE = new TradePlanFactory();

    private static final int OPPOSITE_ENTRUST_STRATEGY_SUM = 13;

    public static TradePlanFactory getInstance() {
        return INSTANCE;
    }

    public TradePlan create(int exchangeType,
                            Integer entrustStrategy,
                            BigDecimal entrustPrice,
                            Integer entrustMethod,
                            Integer number,
                            BigDecimal entrustAmount,
                            OrderType orderType) {
        if (exchangeType == DoubleDirectionTradePlan.DOUBLE_EXCHANGE_TYPE) {
            return createDouble(entrustStrategy, entrustMethod, number, entrustAmount, orderType);
        } else {
            return createBasic(exchangeType,
                               entrustStrategy,
                               entrustPrice,
                               entrustMethod,
                               number,
                               entrustAmount,
                               orderType);
        }
    }

    public BaseTradePlan createBasic(int exchangeType,
                                     Integer entrustStrategy,
                                     BigDecimal entrustPrice,
                                     Integer entrustMethod,
                                     Integer number,
                                     BigDecimal entrustAmount,
                                     OrderType orderType) {
        ExchangeType theExchangeType = AutoAssemblers.getDefault()
                                                     .disassemble(exchangeType, ExchangeType.class);
        TradeNumber tradeNumber = TradeNumberFactory.getInstance().create(entrustMethod, number, entrustAmount);
        EntrustStrategy theEntrustStrategy = AutoAssemblers.getDefault()
                                                           .disassemble(entrustStrategy, EntrustStrategy.class);

        return create(theExchangeType, theEntrustStrategy, entrustPrice, tradeNumber, orderType);
    }

    public BaseTradePlan create(ExchangeType exchangeType,
                                EntrustStrategy entrustStrategy,
                                BigDecimal entrustPrice,
                                TradeNumber tradeNumber, OrderType orderType) {
        if (entrustStrategy == EntrustStrategy.CUSTOMIZED_PRICE) {
            return new CustomizedPriceTradePlan(exchangeType, entrustPrice, tradeNumber);
        } else if (entrustStrategy == EntrustStrategy.MARKET_PRICE) {
            return new MarketPriceTradePlan(exchangeType, tradeNumber, orderType);
        }
        return new OfferedPriceTradePlan(exchangeType, entrustStrategy, tradeNumber);
    }

    public DoubleDirectionTradePlan createDouble(Integer entrustStrategy,
                                                 Integer entrustMethod,
                                                 Integer number,
                                                 BigDecimal entrustAmount,
                                                 OrderType orderType) {
        TradeNumber tradeNumber = TradeNumberFactory.getInstance().create(entrustMethod, number, entrustAmount);
        EntrustStrategy buyEntrustStrategy = AutoAssemblers.getDefault()
                                                           .disassemble(entrustStrategy, EntrustStrategy.class);
        EntrustStrategy sellEntrustStrategy;
        if (buyEntrustStrategy == EntrustStrategy.CURRENT_PRICE) {
            sellEntrustStrategy = EntrustStrategy.CURRENT_PRICE;
        } else {
            sellEntrustStrategy = AutoAssemblers.getDefault()
                                                .disassemble(OPPOSITE_ENTRUST_STRATEGY_SUM - entrustStrategy,
                                                             EntrustStrategy.class);
        }
        BaseTradePlan buyPlan = new OfferedPriceTradePlan(ExchangeType.BUY, buyEntrustStrategy, tradeNumber);
        BaseTradePlan sellPlan = new OfferedPriceTradePlan(ExchangeType.SELL, sellEntrustStrategy, tradeNumber);
        return new DoubleDirectionTradePlan(buyPlan, sellPlan);
    }

    private TradePlanFactory() {
    }
}
