package hbec.intellitrade.condorder.domain.tradeplan;

import hbec.intellitrade.trade.domain.ExchangeType;

import java.util.Objects;

/**
 * 单向交易计划基类
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2017/8/23
 */
public abstract class BaseTradePlan implements SingleEntrustTradePlan {
    private final ExchangeType exchangeType;
    private final TradeNumber tradeNumber;

    public BaseTradePlan(ExchangeType exchangeType, TradeNumber tradeNumber) {
        this.exchangeType = exchangeType;
        this.tradeNumber = tradeNumber;
    }

    /**
     * 获取交易类别
     *
     * @return 交易类别
     */
    public ExchangeType getExchangeType() {
        return exchangeType;
    }

    /**
     * 获取委托策略
     *
     * @return 委托策略
     */
    public abstract EntrustStrategy getEntrustStrategy();

    @Override
    public TradeNumber getTradeNumber() {
        return tradeNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BaseTradePlan that = (BaseTradePlan) o;
        return exchangeType == that.exchangeType &&
               Objects.equals(tradeNumber, that.tradeNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(exchangeType, tradeNumber);
    }
}
