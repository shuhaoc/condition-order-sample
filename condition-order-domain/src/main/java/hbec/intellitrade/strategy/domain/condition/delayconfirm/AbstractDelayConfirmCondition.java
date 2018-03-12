package hbec.intellitrade.strategy.domain.condition.delayconfirm;

import hbec.intellitrade.strategy.domain.condition.market.MarketCondition;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/3/12
 */
public abstract class AbstractDelayConfirmCondition {
    final DelayConfirmCounter counter;
    final MarketCondition marketCondition;

    AbstractDelayConfirmCondition(int confirmTimes, MarketCondition marketCondition) {
        this.counter = new DelayConfirmCounter(confirmTimes);
        this.marketCondition = marketCondition;
    }

    public DelayConfirmCounter getCounter() {
        return counter;
    }

    public int getConfirmedCount() {
        return counter.getConfirmedCount();
    }

    public MarketCondition getMarketCondition() {
        return marketCondition;
    }
}
