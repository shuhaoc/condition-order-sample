package hbec.intellitrade.condorder.domain.tradeplan;

import hbec.intellitrade.trade.domain.ExchangeType;
import me.caosh.autoasm.AutoAssemblers;

import java.math.BigDecimal;

/**
 * Created by caosh on 2017/8/24.
 */
public class TradePlanFactory {
    private static final TradePlanFactory INSTANCE = new TradePlanFactory();

    public static final int DOUBLE_EXCHANGE_TYPE = 0;
    public static final int OPPOSITE_ENTRUST_STRATEGY_SUM = 13;

    public static TradePlanFactory getInstance() {
        return INSTANCE;
    }

    public TradePlan create(int exchangeType, Integer entrustStrategy,
                            Integer entrustMethod, Integer number, BigDecimal entrustAmount) {
        if (exchangeType == DOUBLE_EXCHANGE_TYPE) {
            return createDouble(entrustStrategy, entrustMethod, number, entrustAmount);
        } else {
            return createBasic(exchangeType, entrustStrategy, entrustMethod, number, entrustAmount);
        }
    }

    public BasicTradePlan createBasic(int exchangeType, Integer entrustStrategy, Integer entrustMethod,
                                      Integer number, BigDecimal entrustAmount) {
        TradeNumber tradeNumber = TradeNumberFactory.getInstance().create(entrustMethod, number, entrustAmount);
        EntrustStrategy theEntrustStrategy = AutoAssemblers.getDefault()
                                                           .disassemble(entrustStrategy, EntrustStrategy.class);
        ExchangeType theExchangeType = AutoAssemblers.getDefault()
                                                     .disassemble(exchangeType, ExchangeType.class);
        return new BasicTradePlan(theExchangeType, theEntrustStrategy, tradeNumber);
    }

    public DoubleDirectionTradePlan createDouble(Integer entrustStrategy, Integer entrustMethod, Integer number, BigDecimal entrustAmount) {
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
        BasicTradePlan buyPlan = new BasicTradePlan(ExchangeType.BUY, buyEntrustStrategy, tradeNumber);
        BasicTradePlan sellPlan = new BasicTradePlan(ExchangeType.SELL, sellEntrustStrategy, tradeNumber);
        return new DoubleDirectionTradePlan(buyPlan, sellPlan);
    }

    private TradePlanFactory() {
    }
}
