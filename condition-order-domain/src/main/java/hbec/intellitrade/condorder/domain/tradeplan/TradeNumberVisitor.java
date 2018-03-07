package hbec.intellitrade.condorder.domain.tradeplan;

/**
 * Created by caosh on 2017/8/17.
 */
public interface TradeNumberVisitor {
    void visitTradeNumberDirect(TradeNumberDirect tradeNumberDirect);

    void visitTradeNumberByAmount(TradeNumberByAmount tradeNumberByAmount);
}
