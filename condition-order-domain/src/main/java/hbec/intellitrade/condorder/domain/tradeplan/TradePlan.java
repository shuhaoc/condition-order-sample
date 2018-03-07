package hbec.intellitrade.condorder.domain.tradeplan;

/**
 * Created by caosh on 2017/8/2.
 */
public interface TradePlan {
    /**
     * 由于目前双向交易的条件单买卖数量或金额都是一致的，因此此方法可以存在
     */
    TradeNumber getTradeNumber();

    void accept(TradePlanVisitor visitor);


}
