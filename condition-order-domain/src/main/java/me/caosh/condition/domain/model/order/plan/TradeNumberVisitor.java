package me.caosh.condition.domain.model.order.plan;

/**
 * Created by caosh on 2017/8/17.
 */
public interface TradeNumberVisitor {
    void visitTradeNumberDirect(TradeNumberDirect tradeNumberDirect);

    void visitTradeNumberByAmount(TradeNumberByAmount tradeNumberByAmount);
}
